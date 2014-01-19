package crossj.engine.pool;

import java.util.concurrent.Callable;

import com.badlogic.gdx.utils.Disposable;

/**
 * O(1) acquire and release based on
 * http://gameprogrammingpatterns.com/object-pool.html
 */
public class Pool<E extends Poolable> implements Disposable {

    private final PoolBuffer<E> buffer;
    private final PoolBehavior behavior;
    private final Callable<E> factory;

    public Pool(int size, PoolBehavior behavior, Callable<E> factory) {
        if (size < 1) {
            throw new IllegalArgumentException("Pool must have positive non-zero size, was " + size);
        }
        this.behavior = behavior;
        this.factory = factory;
        buffer = new PoolBuffer<>();

        // Keep a reference to the first element inserted (end of the list) so
        // that it can refer to the head when we're done generating elements
        E end = buffer.insert(create());
        for (int i = 0; i < size - 1; i++) {
            buffer.insert(create());
        }
        end.setNext(buffer.peek());
    }

    protected E create() {
        try {
            E event = factory.call();
            return event;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public E acquire() {
        E head = buffer.peek();
        if (head.isActive()) {
            switch (behavior) {
            case DESTROY:
                // Ignore that it's active and reset it anyway
                break;
            case EXPAND:
                release(create());
                break;
            case NULL:
                return null;
            }
        }
        return buffer.advance().reset();
    }

    public void release(E e) {
        if (e == null) {
            return;
        }
        assert !e.isActive();
        buffer.insert(e);
    }

    @Override
    public void dispose() {
        buffer.dispose();
    }
}
