package crossj.engine.event;

public abstract class BasicEvent<L> implements Event<L> {
    private boolean active = false;

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }
}
