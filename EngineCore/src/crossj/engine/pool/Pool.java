package crossj.engine.pool;

import java.util.concurrent.Callable;

import com.badlogic.gdx.utils.Disposable;

public class Pool<E extends Poolable<E>> implements Disposable {

    private final PoolBuffer<E> buffer;
    private final PoolBehavior behavior;
    private final Callable<E> factory;

    public Pool(int size, PoolBehavior behavior, Callable<E> factory) {
        if (size < 1) {
            throw new IllegalArgumentException("Invalid size " + size);
        }
        this.behavior = behavior;
        this.factory = factory;
        buffer = new PoolBuffer<>();

        // Keep a reference to the first element inserted (end of the list) so
        // that it can refer to the head when we're done generating elements
        E end = buffer.push(create());
        for (int i = 0; i < size - 1; i++) {
            buffer.push(create());
        }
        end.setNext(buffer.peek());
    }

    protected E create() {
        try {
            return factory.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public E acquire() {
        if (buffer.peek().isActive()) {
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
        return buffer.pop().reset();
    }

    public void release(E e) {
        if (e == null) {
            return;
        }
        assert !e.isActive();
        buffer.push(e);
    }

    @Override
    public void dispose() {
        buffer.dispose();
    }
}
