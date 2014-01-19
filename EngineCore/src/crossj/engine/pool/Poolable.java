package crossj.engine.pool;


public interface Poolable<E> {
    E getNext();

    void setNext(E next);

    boolean isActive();

    E reset();
}
