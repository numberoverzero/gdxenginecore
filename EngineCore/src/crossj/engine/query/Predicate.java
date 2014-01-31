package crossj.engine.query;

public interface Predicate<E> {
    /**
     * Return true if the given value matches this predicate; false otherwise.
     */
    public boolean match(E e);
}
