package crossj.engine.events;

import crossj.engine.event.BasicPoolEvent;
import crossj.engine.objects.GameObject;
import crossj.engine.objects.Modifier;

public class ModifierEvent extends BasicPoolEvent<ModiferListener> {

    private GameObject gameObject;
    private Modifier modifier;
    private Modifier.EventType type;

    public ModifierEvent(GameObject gameObject, Modifier modifier, Modifier.EventType type) {
        this.gameObject = gameObject;
        this.modifier = modifier;
        this.type = type;
    }

    @Override
    public boolean notify(ModiferListener listener) {
        switch (type) {
        case APPLIED:
            return listener.onModiferApplied(gameObject, modifier);
        case REMOVED:
            return listener.onModifierRemoved(gameObject, modifier);
        default:
            return false;
        }
    }

    @Override
    public void reset() {
        gameObject = null;
        modifier = null;
        type = null;
    }

}
