package crossj.engine.pool;

import com.badlogic.gdx.utils.Disposable;

/**
 * Stack with limited interface
 */
public class PoolBuffer<E extends Poolable<E>> implements Disposable {
    private E top;

    public PoolBuffer() {
        top = null;
    }

    public E push(E e) {
        e.setNext(top);
        top = e;
        return top;
    }

    public E peek() {
        return top;
    }

    public E pop() {
        E tmp = top;
        top = top.getNext();
        return tmp;
    }

    @Override
    public void dispose() {
        while (peek() != null) {
            pop().setNext(null);
        }
    }
}
