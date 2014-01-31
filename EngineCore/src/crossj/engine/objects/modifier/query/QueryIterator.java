package crossj.engine.objects.modifier.query;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import crossj.engine.objects.Modifier;

public class QueryIterator implements Iterator<Modifier> {
    private List<Modifier> modifiers;
    private String field;
    private Object value;
    private int index = 0;

    public QueryIterator reset() {
        index = 0;
        return this;
    }

    public QueryIterator data(List<Modifier> modifiers) {
        this.modifiers = modifiers;
        index = 0;
        return this;
    }

    public QueryIterator field(String field) {
        this.field = field;
        index = 0;
        return this;
    }

    public QueryIterator value(Object value) {
        this.value = value;
        index = 0;
        return this;
    }

    @Override
    public boolean hasNext() {
        return index < modifiers.size();
    }

    @Override
    public Modifier next() {
        while (index < modifiers.size()) {
            Modifier m = modifiers.get(index);
            index++;
            if (m.match(field, value)) {
                return m;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove items from a query");
    }
}