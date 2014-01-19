package crossj.engine.event;

import crossj.engine.pool.Poolable;

public abstract class BasicPoolEvent<L> implements Event<L> {

    private Poolable next = null;

    @SuppressWarnings("unchecked")
    @Override
    public <E extends Poolable> E getNext() {
        return (E) next;
    }

    @Override
    public <E extends Poolable> void setNext(E next) {
        this.next = next;

    }

    @Override
    public void dispose() {
        next = null;
    }
}
