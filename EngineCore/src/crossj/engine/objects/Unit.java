package crossj.engine.objects;

import com.badlogic.gdx.math.Vector2;

import crossj.engine.actor.Actor;
import crossj.engine.event.EventDispatcher;
import crossj.engine.physics.WorldBody;

public class Unit extends GameObject {
    private final Actor actor;
    private final WorldBody body;
    private final Mover mover;

    public Unit(EventDispatcher dispatcher, Actor actor, WorldBody body, Mover mover) {
        super(dispatcher);
        this.actor = actor;
        this.body = body;
        this.mover = mover;
    }

    public Actor getActor() {
        return actor;
    }

    private WorldBody getBody() {
        return body;
    }

    public Mover getMover() {
        return mover;
    }

    @Override
    public void dispose() {
        super.dispose();
        actor.dispose();
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void setPosition(Vector2 position) {
        body.setPosition(position);
    }

}
