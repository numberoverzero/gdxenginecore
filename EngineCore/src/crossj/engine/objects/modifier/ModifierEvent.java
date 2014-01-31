package crossj.engine.objects.modifier;

import crossj.engine.event.Event;
import crossj.engine.objects.GameObject;
import crossj.engine.objects.Modifier;

public class ModifierEvent extends Event<ModiferListener> {

    public GameObject object = null;
    public Modifier modifier = null;
    public Modifier.EventType type = null;
    public boolean active = false;

    public ModifierEvent() {
    }

    public ModifierEvent(GameObject object, Modifier modifier, Modifier.EventType type) {
        this.object = object;
        this.modifier = modifier;
        this.type = type;
    }

    /**
     * Set all fields of the event
     */
    public ModifierEvent set(GameObject object, Modifier modifier, Modifier.EventType type) {
        this.object = object;
        this.modifier = modifier;
        this.type = type;
        return this;
    }

    @Override
    public boolean notify(ModiferListener listener) {
        switch (type) {
        case APPLIED:
            return listener.onModiferApplied(object, modifier);
        case REMOVED:
            return listener.onModifierRemoved(object, modifier);
        default:
            return false;
        }
    }

    @Override
    public ModifierEvent reset() {
        object = null;
        modifier = null;
        type = null;
        return this;
    }

    @Override
    public boolean isActive() {
        return active;
    }

}
