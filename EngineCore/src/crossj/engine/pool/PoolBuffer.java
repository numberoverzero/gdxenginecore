package crossj.engine.pool;

import com.badlogic.gdx.utils.Disposable;

/**
 * Buffer specifically designed for pools. Does not support remove, traversal,
 * contains. Appends to front of buffer, does not track size.
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
        // If this is the first addition, loop the reference
        // so that e.getNext() returns e
        if (head == null) {
            head = e;
        }
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
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public E advance() {
        assert head != null;
        E tmp = head;
        head = (E) head.getNext();
        return tmp;
    }

    @Override
    public void dispose() {
        Poolable current = head, next;
        while (current != null) {
            next = current.getNext();
            current.setNext(null);
            current = next;
        }
    }

}
