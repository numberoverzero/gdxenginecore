/**
 * Inspired by http://stackoverflow.com/a/942990
 */
package crossj.engine.event;

/**
 * An event notifies a listener that it is available for consumption.
 */
public interface Event<L> {
    /**
     * Returns true if the event was consumed by the listener false otherwise
     *
     * @param listener
     *            The listener to notify
     * @return whether the event should continue to be available for consumption
     */
    boolean notify(L listener);

    /**
     * Whether the event is currently in use. Used for pooling.
     */
    boolean isActive();

    /**
     * Whether the event is currently in use.
     */
    void setActive(boolean active);

    /**
     * Reset the event. Used for pooling.
     */
    void reset();

    /**
     * Should not be called directly.  Used for an O(1) pool function getNextInactive()
     */
    void setPoolNext(@SuppressWarnings("rawtypes") Event event);

    /**
     * Should not be called directly.  Used for an O(1) pool function getNextInactive()
     */
    @SuppressWarnings("rawtypes")
    Event getPoolNext();

}
