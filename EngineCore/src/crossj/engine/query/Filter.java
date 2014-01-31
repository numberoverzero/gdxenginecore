package crossj.engine.query;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Filter<E> implements Iterator<E> {
    Iterator<E> iterator;
    Predicate<E> predicate;

    public Filter(Iterator<E> iterator, Predicate<E> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() {
        while (hasNext()) {
            E e = iterator.next();
            if (predicate.match(e)) {
                return e;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove items from a filter");
    }
}
