/**
 * Inspired by http://stackoverflow.com/a/942990
 */
package crossj.engine.event;

import crossj.engine.pool.Poolable;


/**
 * An event notifies a listener that it is available for consumption.
 */
public abstract class Event<L> implements Poolable<Event<L>> {
    private Event<L> next;

    @Override
    public final Event<L> getNext() {
        return next;
    }

    @Override
    public final void setNext(Event<L> next) {
        this.next = next;

    }

    /**
     * Returns true if the event was consumed by the listener false otherwise
     *
     * @param listener
     *            The listener to notify
     * @return whether the event should continue to be available for consumption
     */
    public abstract boolean notify(L listener);
}
