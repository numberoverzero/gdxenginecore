package crossj.engine.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Disposable;

import crossj.engine.event.EventDispatcher;
import crossj.engine.events.GameObjectPropertyEvent;

/**
 * Anything managed in a game screen, including buffs, debuffs, buildings,
 * units, quests, points of interest
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

    @Override
    public void dispose() {
        while (!modifiers.isEmpty()) {
            modifiers.get(0).remove(this);
            ;
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
        getEventDispatcher().notify(new GameObjectPropertyEvent<T>(this, property, oldValue, newValue));
    }
}
