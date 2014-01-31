package crossj.engine.objects.property;

import crossj.engine.event.Event;
import crossj.engine.objects.GameObject;

public class GameObjectPropertyEvent extends Event<GameObjectPropertyListener> {

    public GameObject object = null;
    public String property = null;
    private Object oldValue = null;
    private Object newValue = null;
    public boolean active = false;

    public GameObjectPropertyEvent() {

    }

    public GameObjectPropertyEvent set(GameObject gameObject, String property, Object oldValue, Object newValue) {
        object = gameObject;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
        return this;
    }

    @Override
    public boolean notify(GameObjectPropertyListener listener) {
        return listener.onPropertyChanged(object, property, oldValue, newValue);
    }

    @Override
    public GameObjectPropertyEvent reset() {
        object = null;
        property = null;
        oldValue = null;
        newValue = null;
        active = false;
        return this;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}
