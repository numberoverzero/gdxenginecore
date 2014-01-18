package crossj.engine.objects;

import crossj.engine.event.EventDispatcher;
import crossj.engine.events.ModifierEvent;

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

    public void apply(GameObject gameObject, boolean notify) {
        gameObject.getModifiers().add(this);
        if (notify) {
            gameObject.getEventDispatcher().notify(new ModifierEvent(gameObject, this, EventType.APPLIED));
        }
    }

    public void remove(GameObject gameObject, boolean notify, boolean dispose) {
        gameObject.getModifiers().remove(this);
        if (notify) {
            gameObject.getEventDispatcher().notify(new ModifierEvent(gameObject, this, EventType.REMOVED));
        }
        if (dispose) {
            dispose();
        }
    }
}