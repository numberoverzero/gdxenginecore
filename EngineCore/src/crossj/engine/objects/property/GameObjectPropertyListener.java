package crossj.engine.objects.property;

import crossj.engine.objects.GameObject;


public interface GameObjectPropertyListener {

    boolean onPropertyChanged(GameObject object, String property, Object oldValue, Object newValue);
}
