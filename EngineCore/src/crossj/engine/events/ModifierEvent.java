package crossj.engine.events;

import java.util.concurrent.Callable;

import crossj.engine.event.BasicEvent;
import crossj.engine.event.EventPool;
import crossj.engine.objects.GameObject;
import crossj.engine.objects.Modifier;

public class ModifierEvent extends BasicEvent<ModiferListener> {

    /**
     * Add a pool of events to the global event pool.
     */
    static {
        Callable<ModifierEvent> factory = new Callable<ModifierEvent>() {

            @Override
            public ModifierEvent call() throws Exception {
                return new ModifierEvent(null, null, null);
            }
        };
        EventPool.GLOBAL.addType(ModifierEvent.class, factory);
    }

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
