/**
 * Inspired by http://stackoverflow.com/a/942990
 */
package crossj.engine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher {
    public static final EventDispatcher GLOBAL = new EventDispatcher();

    @SuppressWarnings("rawtypes")
    private final Map<Class<? extends Event>, List> listeners;
    private final EventPool pool;

    public EventDispatcher() {
        this(EventPool.GLOBAL);
    }

    public EventDispatcher(EventPool pool) {
        listeners = new HashMap<>();
        this.pool = pool;
    }

    /**
     * Add a listener to the list of listeners that receives events of the given
     * type
     *
     * @param eventClass
     *            The type of event to start listening to
     * @param listener
     *            The listener to add
     */
    public <L> void addListener(Class<? extends Event<L>> eventClass, L listener) {
        synchronized (listeners) {
            List<L> eventListeners = getListeners(eventClass);
            if (!eventListeners.contains(listener)) {
                eventListeners.add(listener);
            }
        }
    }

    /**
     * Remove a listener from the list of listeners that receives events of the
     * given type
     *
     * @param eventClass
     *            The type of event to stop listening to
     * @param listener
     *            The listener to remove
     */
    public <L> void removeListener(Class<? extends Event<L>> eventClass, L listener) {
        synchronized (listeners) {
            List<L> eventListeners = getListeners(eventClass);
            if (eventListeners.contains(listener)) {
                eventListeners.remove(listener);
            }
        }
    }

    /**
     * Notify all listeners of an event. The event will stop being broadcast
     * once a listener consumes it.
     *
     * @param event
     *            The event to broadcast to listeners
     */
    public <L> void notify(Event<L> event) {
        notify(event, true);
    }

    /**
     * Notify all listeners of an event. If consumable is true, the event will
     * not be broadcast to any more listeners once a listener consumes it. If
     * consumable is false, the event will be broadcast to all listeners, even
     * if a listener consumes the event.
     *
     * @param event
     *            The event to broadcast to listeners
     * @param consumable
     *            true if the event should not continue to be broadcast once a
     *            listener consumes the event.
     */
    public <L> void notify(Event<L> event, boolean consumable) {
        boolean consumed;
        synchronized (listeners) {
            List<L> eventListeners = getListeners(event);
            for (L listener : eventListeners) {
                consumed = event.notify(listener);
                // If the event returns true and is consumed by the
                // listener, don't continue notifying listeners.
                if (consumable && consumed) {
                    return;
                }
            }
        }
    }

    /**
     * Convenience method for getting listeners of the type of a given event
     */
    @SuppressWarnings("unchecked")
    private <L> List<L> getListeners(Event<L> event) {
        return getListeners((Class<? extends Event<L>>) event.getClass());
    }

    private <L> List<L> getListeners(Class<? extends Event<L>> eventClass) {
        synchronized (listeners) {
            @SuppressWarnings("unchecked")
            List<L> eventListeners = listeners.get(eventClass);
            if (eventListeners == null) {
                eventListeners = new ArrayList<L>();
                listeners.put(eventClass, eventListeners);
            }
            return eventListeners;
        }
    }

    /**
     * Get an event from the dispatcher's pool of events
     */
    public <T extends Event<?>> T acquire(Class<T> type) {
        return pool.acquire(type);
    }

    /**
     * Release an event back to the dispatcher's pool
     */
    public <T extends Event<?>> void release(T event) {
        pool.release(event);
    }
}
