package crossj.engine.pool;

public interface Poolable<E> {
    E getNext();

    void setNext(E next);

    boolean isActive();

    /**
     * It is ok for a poolable to be activated when reset.
     */
    E reset();
}
