package crossj.engine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.badlogic.gdx.utils.Disposable;

public class EventPool implements Disposable {
    public enum Behavior {
        /**
         * Expand the pool to accommodate the call
         */
        EXPAND,

        /**
         * Return an event without expanding the pool, likely by
         * destroying/resetting another event that is active
         */
        DESTROY,

        /**
         * Return null if there is no inactive event to return
         */
        NULL
    }

    private static final int GLOBAL_POOL_SIZE = 500;
    public static final EventPool GLOBAL = new EventPool(GLOBAL_POOL_SIZE, Behavior.NULL);

    private final Map<Class<? extends Event<?>>, Pool<? extends Event<?>>> pools;
    private final int size;
    private final Behavior behavior;

    public EventPool(int size, Behavior behavior) {
        pools = new HashMap<>();
        if (size < 1) {
            throw new IllegalArgumentException("Pool size must be > 0");
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
        protected final List<T> events;
        protected T firstAvailable;

        public Pool(int size, Callable<T> factory) {
            this.factory = factory;
            events = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                T event = create();
                events.add(event);
                if (i > 0) {
                    events.get(i - 1).setPoolNext(event);
                }
            }
            events.get(size - 1).setPoolNext(null);
            firstAvailable = events.get(0);
        }

        protected abstract T acquire();

        protected void release(T event) {
            event.setActive(false);
            event.setPoolNext(firstAvailable);
            firstAvailable = event;
        }

        protected T create() {
            try {
                return factory.call();
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
            event.setActive(true);
            firstAvailable = (T) event.getPoolNext();
            return event;
        }

        @Override
        public void dispose() {
            for(T event : events) {
                event.dispose();
            }
            events.clear();
        }
    }

    private class StaticPool<T extends Event<?>> extends Pool<T> {
        private final Behavior behavior;

        public StaticPool(int size, Behavior behavior, Callable<T> factory) {
            super(size, factory);
            switch (behavior) {
            case NULL:
                break;
            case DESTROY:
                events.get(size - 1).setPoolNext(events.get(0));
                break;
            default:
                throw new IllegalArgumentException("Static pools only support DESTROY or NULL behavior");
            }
            this.behavior = behavior;
        }

        @Override
        protected T acquire() {
            switch (behavior) {
            case NULL:
                if (firstAvailable == null || firstAvailable.isActive()) {
                    return null;
                }
                break;
            case DESTROY:
                if (firstAvailable == null) {
                    throw new RuntimeException("StaticPool(DESTROY) found null Event");
                }
                break;
            default:
                throw new RuntimeException("Static pools only support DESTROY or NULL behavior");
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
            if (firstAvailable == null || firstAvailable.isActive()) {
                // Create a new event and release it, setting it to
                // firstAvailable
                release(create());
            }
            return advance();
        }
    }

    @Override
    public void dispose() {
        for(Pool<? extends Event<?>> pool : pools.values()) {
            pool.dispose();
        }
        pools.clear();

    }
}
