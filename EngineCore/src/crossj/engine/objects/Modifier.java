package crossj.engine.objects;

import crossj.engine.event.EventDispatcher;
import crossj.engine.objects.modifier.ModifierEvent;

/**
 * A single modifier object can be used for multiple units.
 */
public abstract class Modifier extends GameObject {
    /**
     * Events this class can trigger
     */
    public static enum EventType {
        APPLIED, REMOVED
    }

    private final String name;

    public Modifier(EventDispatcher dispatcher, String name) {
        super(dispatcher);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void apply(GameObject object, boolean notify) {
        object.getModifiers().add(this);
        if (notify) {
            notify(object, EventType.APPLIED);
        }
    }

    public void remove(GameObject object, boolean notify) {
        object.getModifiers().remove(this);
        if (notify) {
            notify(object, EventType.REMOVED);
        }
    }

    private void notify(GameObject object, EventType type) {
        EventDispatcher dispatcher = object.getEventDispatcher();
        ModifierEvent event = dispatcher.acquire(ModifierEvent.class);
        event.set(object, this, type);
        event.active = true;
        dispatcher.notify(event);
        event.active = false;
        dispatcher.release(event);
    }
}