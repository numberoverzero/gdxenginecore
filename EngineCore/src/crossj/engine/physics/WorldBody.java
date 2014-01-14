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
        body.applyForceToCenter(world.toBox(force), wake);
    }

    public void applyImpulseToCenter(Vector2 impulse, boolean wake) {
        body.applyLinearImpulse(world.toBox(impulse), body.getWorldCenter(), wake);
    }

    /**
     * Calls {@link Fixture#setRestitution} on all body {@link Fixture}s.
     * @param restitution
     */
    public void setRestitution(float restitution) {
        for(Fixture fixture : body.getFixtureList()) {
            fixture.setRestitution(restitution);
        }
    }

    public boolean containsFixture(Fixture fixture) {
        return body.getFixtureList().contains(fixture, true);
    }

    /**
     * Calls {@link Fixture#setFriction} on all body {@link Fixture}s.
     * @param friction
     */
    public void setFriction(float friction) {
        for(Fixture fixture : body.getFixtureList()) {
            fixture.setFriction(1);
        }
    }

    public void setPosition(Vector2 position) {
        body.setTransform(world.toBox(position.x), world.toBox(position.y), body.getAngle());
    }

    public Vector2 getPosition() {
        return world.toWorld(body.getWorldCenter());
    }

    public void setLinearVelocity(Vector2 velocity) {
        body.setLinearVelocity(world.toBox(velocity.x), world.toBox(velocity.y));
    }

    public Vector2 getLinearVelocity() {
        return world.toWorld(body.getLinearVelocity());
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
