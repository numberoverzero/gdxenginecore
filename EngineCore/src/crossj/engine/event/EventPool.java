package crossj.engine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.badlogic.gdx.Gdx;

public class EventPool {
    private static final int GLOBAL_POOL_SIZE = 500;
    public static final EventPool GLOBAL = new EventPool(GLOBAL_POOL_SIZE);

    private final Map<Class<? extends Event<?>>, Pool<? extends Event<?>>> pools;
    private final int size;

    public EventPool(int size) {
        pools = new HashMap<>();
        if (size < 1) {
            throw new IllegalArgumentException("Pool size must be > 0");
        }
        this.size = size;
    }

    public <T extends Event<?>> void addType(Class<? extends T> type, Callable<T> factory) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        } else if (pools.containsKey(type)) {
            throw new IllegalArgumentException("Pool already contains type <" + type.toString() + ">");
        }
        pools.put(type, new Pool<T>(size, factory, type));
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

    private class Pool<T extends Event<?>> {
        private final Class<? extends T> type;
        private final List<T> events;
        final int size;

        public Pool(int size, Callable<T> factory, Class<? extends T> type) {
            this.size = size;
            this.type = type;
            events = new ArrayList<>(size);
            for (int i = size; i > 0; i--) {
                try {
                    events.add(factory.call());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public T acquire() {
            int index = 0;
            T event;
            while (index++ < size) {
                event = events.get(index);
                if (!event.isActive()) {
                    event.reset();
                    event.setActive(true);
                    return event;
                }
            }
            Gdx.app.error("EventPool.Pool", "Unable to acquire event of type " + type);
            return null;
        }

        public void release(T event) {
            event.setActive(false);
        }
    }
}
