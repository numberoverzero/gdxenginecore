package crossj.engine.event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.badlogic.gdx.utils.Disposable;

import crossj.engine.pool.Pool;
import crossj.engine.pool.PoolBehavior;

/**
 * Manages multiple event class pools
 */
public class EventPool implements Disposable {
    private static final int GLOBAL_POOL_SIZE = 500;
    public static final EventPool GLOBAL = new EventPool(GLOBAL_POOL_SIZE, PoolBehavior.NULL);

    private final Map<Class<? extends Event<?>>, Pool<? extends Event<?>>> pools;
    private final int size;
    private final PoolBehavior behavior;

    public EventPool(int size, PoolBehavior behavior) {
        pools = new HashMap<>();
        if (size < 1) {
            throw new IllegalArgumentException("Invalid size " + size);
        }
        this.size = size;
        this.behavior = behavior;
    }

    public <T extends Event<?>> void addType(Class<T> type, Callable<T> factory) {
        addType(type, factory, size, behavior);
    }

    @SuppressWarnings("unchecked")
    public <T extends Event<?>> void addType(Class<T> type, Callable<T> factory, int size, PoolBehavior behavior) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        } else if (pools.containsKey(type)) {
            throw new IllegalArgumentException("Pool already contains type <" + type.toString() + ">");
        }
        pools.put(type, new Pool<Event<?>>(size, behavior, (Callable<Event<?>>)factory));
    }


    @SuppressWarnings("unchecked")
    public <T extends Event<?>> T acquire(Class<T> type) {
        if (type == null || !pools.containsKey(type)) {
            return null;
        }
        return (T) ((Pool<Event<?>>) pools.get(type)).acquire();
    }

    @SuppressWarnings("unchecked")
    public <T extends Event<?>> void release(T event) {
        if (event == null || !pools.containsKey(event.getClass())) {
            return;
        }
        ((Pool<Event<?>>) pools.get(event.getClass())).release(event);
    }

    @Override
    public void dispose() {
        for (Pool<? extends Event<?>> pool : pools.values()) {
            pool.dispose();
        }
        pools.clear();
    }
}
