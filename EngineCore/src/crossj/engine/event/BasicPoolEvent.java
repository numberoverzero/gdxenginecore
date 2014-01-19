package crossj.engine.event;


public abstract class BasicPoolEvent<L> implements Event<L> {
    private Event<L> next;

    @Override
    public Event<L> getNext() {
        return next;
    }

    @Override
    public void setNext(Event<L> next) {
        this.next = next;

    }
}
