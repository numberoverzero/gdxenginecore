package crossj.engine.objects;


public interface GameObjectListener {

    <T> boolean onPropertyChanged(GameObject gameObject, String property, T oldValue, T newValue);
}
