package crossj.engine.objects;

import com.badlogic.gdx.math.Vector2;

import crossj.engine.actor.Actor;
import crossj.engine.event.EventDispatcher;
import crossj.engine.physics.WorldBody;

public class Unit extends GameObject {
    private final Actor actor;
    private final WorldBody body;

    public Unit(EventDispatcher dispatcher, Actor actor, WorldBody body) {
        super(dispatcher);
        this.actor = actor;
        this.body = body;
    }

    public Actor getActor() {
        return actor;
    }

    public WorldBody getBody() {
        return body;
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
