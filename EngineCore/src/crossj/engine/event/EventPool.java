package crossj.engine.event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.badlogic.gdx.utils.Disposable;

import crossj.engine.pool.PoolBehavior;

public class EventPool implements Disposable {
    private static final int GLOBAL_POOL_SIZE = 500;
    public static final EventPool GLOBAL = new EventPool(GLOBAL_POOL_SIZE, PoolBehavior.NULL);

    private final Map<Class<? extends Event<?>>, Pool<? extends Event<?>>> pools;
    private final int size;
    private final PoolBehavior behavior;

    public EventPool(int size, PoolBehavior behavior) {
        pools = new HashMap<>();
        if (size < 1) {
            throw new IllegalArgumentException("Pool must have positive non-zero size, was " + size);
        }
        this.size = size;
        this.behavior = behavior;
    }

    public <T extends Event<?>> void addType(Class<? extends T> type, Callable<T> factory) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        } else if (pools.containsKey(type)) {
            throw new IllegalArgumentException("Pool already contains type <" + type.toString() + ">");
        }
        switch (behavior) {
        case DESTROY:
            pools.put(type, new StaticPool<T>(size, behavior, factory));
            break;
        case NULL:
            pools.put(type, new StaticPool<T>(size, behavior, factory));
            break;
        case EXPAND:
            pools.put(type, new DynamicPool<T>(size, factory));
            break;
        default:
            throw new RuntimeException("Unknown pool constructor for behavior " + behavior);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Event<?>> T acquire(Class<T> type) {
        if (type == null || !pools.containsKey(type)) {
            return null;
        }
        return ((Pool<T>) pools.get(type)).acquire();
    }

    @SuppressWarnings("unchecked")
    public <T extends Event<?>> void release(T event) {
        if (event == null || !pools.containsKey(event.getClass())) {
            return;
        }
        ((Pool<T>) pools.get(event.getClass())).release(event);
    }

    /**
     * O(1) firstAvailable based on
     * http://gameprogrammingpatterns.com/object-pool.html
     */
    private abstract class Pool<T extends Event<?>> implements Disposable {
        private final Callable<T> factory;
        protected T firstAvailable;
        protected int size;

        @SuppressWarnings("unchecked")
        public Pool(int size, Callable<T> factory) {
            if (size < 1) {
                throw new IllegalArgumentException("Pool must have positive non-zero size, was " + size);
            }
            this.factory = factory;

            @SuppressWarnings("rawtypes")
            Event head, current;
            head = current = create();
            for (int i = 0; i < size - 1; i++) {
                current.setNext(create());
                current = current.getNext();
            }
            current.setNext(head);
            firstAvailable = (T) head;
        }

        protected abstract T acquire();

        protected void release(T event) {
            event.setNext(firstAvailable);
            firstAvailable = event;
        }

        protected T create() {
            try {
                T event = factory.call();
                size++;
                return event;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Without null or bounds checks, advances firstAvailable to next
         * pointer, resets and activates the previous firstAvailable and returns
         * it for use
         */
        @SuppressWarnings("unchecked")
        protected T advance() {
            T event = firstAvailable;
            event.reset();
            firstAvailable = (T) event.getNext();
            return event;
        }

        @Override
        @SuppressWarnings("rawtypes")
        public void dispose() {
            Event current = firstAvailable, next;
            for (int i = 0; i < size; i++) {
                if (current == null) {
                    return;
                }
                next = current.getNext();
                current.dispose();
                current = next;
            }
        }
    }

    private class StaticPool<T extends Event<?>> extends Pool<T> {
        private final PoolBehavior behavior;

        public StaticPool(int size, PoolBehavior behavior, Callable<T> factory) {
            super(size, factory);
            this.behavior = behavior;
        }

        @Override
        protected T acquire() {
            if (PoolBehavior.NULL.equals(behavior) && firstAvailable.isActive()) {
                System.out.println("Still active");
                return null;
            }
            return advance();
        }
    }

    private class DynamicPool<T extends Event<?>> extends Pool<T> {
        public DynamicPool(int size, Callable<T> factory) {
            super(size, factory);
        }

        @Override
        protected T acquire() {
            if (firstAvailable.isActive()) {
                // Create a new event and release it, setting it to
                // firstAvailable
                release(create());
            }
            return advance();
        }
    }

    @Override
    public void dispose() {
        for (Pool<? extends Event<?>> pool : pools.values()) {
            pool.dispose();
        }
        pools.clear();

    }
}
