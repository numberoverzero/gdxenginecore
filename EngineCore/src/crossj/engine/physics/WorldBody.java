package crossj.engine.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Disposable;

/**
 * Wrapper around {@link Body} which interacts in world coordinates, internally
 * translating to Box2D coordinates where appropriate
 */
public class WorldBody implements Disposable {
    private final World world;
    private final Body body;

    public WorldBody(Body body, World world) {
        this.world = world;
        this.body = body;
    }

    @Override
    public void dispose() {
        world.destroyBody(this);
    }

    public void applyForceToCenter(Vector2 force, boolean wake) {
        body.applyForceToCenter(force, wake);
    }

    /**
     * Set the restitution on all body {@link Fixture}s.
     * @param restitution
     */
    public void setRestitution(float restitution) {
        for(Fixture fixture : body.getFixtureList()) {
            fixture.setRestitution(restitution);
        }
    }

    public void setPosition(Vector2 position) {
        body.setTransform(position, body.getAngle());
    }

    public Vector2 getPosition() {
        return body.getWorldCenter();
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public float getAngle() {
        return body.getAngle();
    }

    public void setAngle(float angle) {
        body.setTransform(body.getWorldCenter(), angle);
    }

    public Body getBox2DBody() {
        return body;
    }

}
