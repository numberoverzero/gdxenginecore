package crossj.engine.pool;

import com.badlogic.gdx.utils.Disposable;

/**
 * Stack with limited interface
 */
public class PoolBuffer<E extends Poolable<E>> implements Disposable {
    private E head;

    public PoolBuffer() {
        head = null;
    }

    public E insert(E e) {
        e.setNext(head);
        head = e;
        return head;
    }

    public E peek() {
        return head;
    }

    public E pop() {
        E tmp = head;
        head = head.getNext();
        return tmp;
    }

    @Override
    public void dispose() {
        while (peek() != null) {
            pop().setNext(null);
        }
    }
}
