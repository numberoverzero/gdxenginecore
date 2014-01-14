package crossj.engine.objects;

import com.badlogic.gdx.math.Vector2;

import crossj.engine.event.EventDispatcher;
import crossj.engine.events.ModifierEvent;

/**
 * A single modifier object can be used for multiple units.
 */
public class Modifier extends GameObject {
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

    public void apply(GameObject gameObject) {
        gameObject.getModifiers().add(this);
        gameObject.getEventDispatcher().notify(new ModifierEvent(gameObject, this, EventType.APPLIED));
    }

    public void remove(GameObject gameObject) {
        gameObject.getModifiers().remove(this);
        gameObject.getEventDispatcher().notify(new ModifierEvent(gameObject, this, EventType.REMOVED));
    }

    public void setPercent(float newValue) {
        float oldValue = 0f;
        onPropertyChange("percent", oldValue, newValue);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(0, 0);
    }
}