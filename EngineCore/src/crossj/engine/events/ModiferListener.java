package crossj.engine.events;

import crossj.engine.objects.GameObject;
import crossj.engine.objects.Modifier;

public interface ModiferListener {

    boolean onModiferApplied(GameObject gameObject, Modifier modifier);

    boolean onModifierRemoved(GameObject gameObject, Modifier modifier);

}
