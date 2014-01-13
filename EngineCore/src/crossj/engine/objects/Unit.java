package crossj.engine.objects;

import crossj.engine.actor.Actor;
import crossj.engine.event.EventDispatcher;

public class Unit extends GameObject {
    private final Actor actor;

    public Unit(EventDispatcher dispatcher, Actor actor) {
        super(dispatcher);
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    @Override
    public void dispose() {
        super.dispose();
        actor.dispose();
    }

}
