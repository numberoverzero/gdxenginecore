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
        this.behavior = behavior;
        this.factory = factory;
        buffer = new PoolBuffer<>();
        E head = create();
        buffer.add(head);
        for (int i = 0; i < size - 1; i++) {
            buffer.add(create());
        }
        buffer.add(head);
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
                head.reset();
                break;
            case EXPAND:
                System.out.println("Expanding");
                release(create());
                break;
            case NULL:
                return null;
            }
        }
        head = buffer.advance();
        //head.reset();
        return head;
    }

    public void release(E e) {
        if (e == null) {
            return;
        }
        assert !e.isActive();
        buffer.add(e);
    }

    @Override
    public void dispose() {
        buffer.dispose();
    }
}
