package crossj.engine.objects;

import com.badlogic.gdx.math.Vector2;

import crossj.engine.actor.Actor;
import crossj.engine.event.EventDispatcher;
import crossj.engine.objects.movement.Mover;
import crossj.engine.physics.WorldBody;

public class Unit extends GameObject {
    private final Actor actor;
    private WorldBody body;
    private final Mover mover;

    public Unit(EventDispatcher dispatcher, Actor actor, WorldBody body, Mover mover) {
        super(dispatcher);
        this.actor = actor;
        this.body = body;
        this.mover = mover;

        if (actor != null && body != null) {
            actor.getTracker().track(this);
        }
    }

    public void setBody(WorldBody body) {
        this.body = body;
    }

    public Actor getActor() {
        return actor;
    }

    public Mover getMover() {
        return mover;
    }

    @Override
    public void dispose() {
        super.dispose();
        actor.dispose();
        body.dispose();
    }

    @Override
    public Vector2 getPosition() {
        return mover.getPosition();
    }

    public void setPosition(Vector2 position) {
        mover.setPosition(position);
    }

}
