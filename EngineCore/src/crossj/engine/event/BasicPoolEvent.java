package crossj.engine.event;

public abstract class BasicPoolEvent<L> implements Event<L> {

    @SuppressWarnings("rawtypes")
    private Event next = null;

    @Override
    @SuppressWarnings("rawtypes")
    public Event getNext() {
        return next;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setNext(Event event) {
        next = event;
    }

    @Override
    public void dispose() {
        next = null;
    }
}
