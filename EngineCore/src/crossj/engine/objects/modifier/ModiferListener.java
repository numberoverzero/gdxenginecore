package crossj.engine.objects.modifier;

import crossj.engine.objects.GameObject;

public interface ModiferListener {

    boolean onModiferApplied(GameObject gameObject, Modifier modifier);

    boolean onModifierRemoved(GameObject gameObject, Modifier modifier);

}
