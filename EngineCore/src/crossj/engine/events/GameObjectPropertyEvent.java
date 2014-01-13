package crossj.engine.events;

import crossj.engine.event.Event;
import crossj.engine.objects.GameObject;

public class GameObjectPropertyEvent<T> implements Event<GameObjectListener> {

    private final GameObject gameObject;
    private final String property;
    private final T oldValue;
    private final T newValue;

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

}
