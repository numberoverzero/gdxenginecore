package crossj.engine.event;

public abstract class BasicPoolEvent<L> implements Event<L> {
    private boolean active = false;

    @SuppressWarnings("rawtypes")
    private Event next = null;

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Event getPoolNext() {
        return next;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setPoolNext(Event event) {
        next = event;
    }

    @Override
    public void dispose() {
        active = false;
        next = null;
    }
}
