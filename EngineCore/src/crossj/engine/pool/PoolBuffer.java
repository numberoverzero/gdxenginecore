package crossj.engine.pool;

import com.badlogic.gdx.utils.Disposable;

/**
 * Buffer specifically designed for pools. It's a linked list that supports
 * insert (before head) and peek/pop (at head only). The limited operations
 * force an O(1) access pattern.
 */
public class PoolBuffer<E extends Poolable> implements Disposable {
    private E head;

    public PoolBuffer() {
        head = null;
    }

    /**
     * Adds an element as the new head, pointing to the old head as the next
     * element. This element will be returned by the next peek/advance call
     */
    public E insert(E e) {
        e.setNext(head);
        head = e;
        return head;
    }

    public E peek() {
        return head;
    }

    /**
     * Return the current head, and advance head to the next value
     * (pre-increment)
     */
    @SuppressWarnings("unchecked")
    public E pop() {
        E tmp = head;
        head = (E) head.getNext();
        return tmp;
    }

    @Override
    public void dispose() {
        while (peek() != null) {
            pop().setNext(null);
        }
    }

}
