package crossj.engine.events;

import crossj.engine.objects.GameObject;

public interface GameObjectListener {

    <T> boolean onPropertyChanged(GameObject gameObject, String property, T oldValue, T newValue);
}
