package crossj.engine.objects.modifier;

import crossj.engine.event.EventDispatcher;
import crossj.engine.objects.GameObject;

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

    /**
     * Return true if the modifier's value for the given field matches expected
     * value - false otherwise. Returns false if the field is not present or the
     * provided value is not comparable.
     */
    public <T> boolean match(String field, T value) {
        return false;
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