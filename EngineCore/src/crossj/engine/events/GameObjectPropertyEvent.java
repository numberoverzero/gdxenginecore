package crossj.engine.events;

import crossj.engine.event.BasicPoolEvent;
import crossj.engine.objects.GameObject;

public class GameObjectPropertyEvent<T> extends BasicPoolEvent<GameObjectListener> {

    // No static setup here to add events to the global pool, since I can't
    // figure out how to do that for a class with a type param. That's probably
    // ok, since the EventPool may be an early optimization anyway. Or, this is
    // too vague an event type anyway.

    private GameObject gameObject;
    private String property;
    private T oldValue;
    private T newValue;
    public boolean active = false;

    public GameObjectPropertyEvent(GameObject gameObject, String property, T oldValue, T newValue) {
        this.gameObject = gameObject;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public boolean notify(GameObjectListener listener) {
        return listener.onPropertyChanged(gameObject, property, oldValue, newValue);
    }

    @Override
    public void reset() {
        gameObject = null;
        property = null;
        oldValue = null;
        newValue = null;
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
