package crossj.engine.objects.modifier.query;

import java.util.Iterator;
import java.util.List;

import crossj.engine.objects.Modifier;
import crossj.engine.util.JavaUtil;

public class Query implements Iterable<Modifier> {
    private final QueryIterator iter;

    public Query() {
        iter = new QueryIterator();
    }

    public Query reset() {
        iter.reset();
        return this;
    }

    public Query data(List<Modifier> modifiers) {
        iter.data(modifiers);
        return this;
    }

    public Query field(String field) {
        iter.field(field);
        return this;
    }

    public Query value(Object value) {
        iter.value(value);
        return this;
    }

    public Iterator<Modifier> filter() {
        return iter;
    }

    public List<Modifier> asList() {
        return JavaUtil.copy(this);
    }

    @Override
    public Iterator<Modifier> iterator() {
        return iter;
    }
}
