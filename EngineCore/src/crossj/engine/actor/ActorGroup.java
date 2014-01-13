package crossj.engine.actor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ActorGroup implements Actor {
    private final List<Actor> actors;
    private final Vector2 position;
    private boolean enabled;

    public ActorGroup() {
        actors = new ArrayList<>();
        position = new Vector2(0, 0);
    }

    /**
     * Add an actor to the group. offset is the delta from the group's current
     * position. When the group's position is updated, the actor's position will
     * be adjusted to maintain the desired offset.
     * 
     * @param actor
     * @param offset
     */
    public void add(Actor actor, Vector2 offset) {
        add(actor, offset.x, offset.y);
    }

    /**
     * See {@link #add(Actor, Vector2)}
     * 
     * @param actor
     * @param offsetX
     * @param offsetY
     */
    public void add(Actor actor, float offsetX, float offsetY) {
        actors.add(actor);
        actor.setPosition(actor.getPosition().set(position).add(offsetX, offsetY));;
    }

    public boolean remove(Actor actor) {
        return actors.remove(actor);
    }

    public Actor remove(int index) {
        return actors.remove(index);
    }

    @Override
    public void dispose() {
        for (Actor actor : actors) {
            actor.dispose();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void act(SpriteBatch spriteBatch, float delta) {
        if (!isEnabled()) {
            return;
        }
        for (Actor actor : actors) {
            actor.act(spriteBatch, delta);
        }
    }

    @Override
    public void setOrigin(float x, float y) {
        // no-op since each actor maintains its own offset from the render
        // position
    }

    @Override
    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    @Override
    public void setPosition(float x, float y) {
        Vector2 delta = new Vector2(x, y).sub(position);
        for (Actor actor : actors) {
            actor.setPosition(actor.getPosition().add(delta));
        }
        position.set(x, y);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void reset() {
        for (Actor actor : actors) {
            actor.reset();
        }
    }
}
