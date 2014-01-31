package crossj.engine.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import crossj.engine.event.EventDispatcher;
import crossj.engine.objects.modifier.Modifier;
import crossj.engine.objects.property.GameObjectPropertyEvent;

/**
 * Anything managed in a game screen, including buffs, debuffs, buildings,
 * units, quests, points of interest, meta objects (xp modifiers, achievements)
 */
public abstract class GameObject implements Disposable {
    private final EventDispatcher eventDispatcher;
    private final List<Modifier> modifiers;

    public GameObject(EventDispatcher dispatcher) {
        eventDispatcher = dispatcher;
        modifiers = new ArrayList<>();
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public abstract Vector2 getPosition();

    @Override
    public void dispose() {
        while (!modifiers.isEmpty()) {
            modifiers.get(0).remove(this, false);
        }
    }

    /**
     * Notify listeners that the object's property has changed.
     *
     * For example, to notify that health has changed: <blockquote>
     *
     * <pre>
     * public void setHealth(int newHealth) {
     *     int oldHealth = health;
     *     health = value;
     *     onPropertyChange(&quot;Health&quot;, oldHealth, newHealth);
     * }
     * </pre>
     *
     * </blockquote>
     *
     * @param property
     *            Name of the property that is changing
     * @param oldValue
     *            What the value was before the update
     * @param newValue
     *            What the value has been changed to (before this method is
     *            invoked)
     */
    protected <T> void onPropertyChange(String property, T oldValue, T newValue) {
        GameObjectPropertyEvent event = getEventDispatcher().acquire(GameObjectPropertyEvent.class);
        event.set(this, property, oldValue, newValue);
        event.active = true;
        getEventDispatcher().notify(event);
        event.active = false;
        getEventDispatcher().release(event);
    }
}
