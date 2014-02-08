package crossj.engine.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Disposable;

import crossj.engine.pool.Poolable;

/**
 * Wrapper around {@link Body} which interacts in world coordinates, internally
 * translating to Box2D coordinates where appropriate
 */
public class WorldBody implements Poolable<WorldBody>, Disposable {
    private final World world;
    private final Body body;
    private WorldBody poolNext = null;

    /**
     * Used to track activates and deactivates while world is locked. If this
     * number is greater than 0 the next time update is called, it will be
     * activated. If this number is less than 0 the next time update is called,
     * it will be activated. Also used when calling {@link #isActive()}
     */
    private int deferredActiveChange = 0;

    public WorldBody(Body body, World world) {
        this.world = world;
        this.body = body;
        body.setUserData(this);
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
     *
     * @param restitution
     */
    public void setRestitution(float restitution) {
        for (Fixture fixture : body.getFixtureList()) {
            fixture.setRestitution(restitution);
        }
    }

    /**
     * Calls {@link Fixture#setFriction} on all body {@link Fixture}s.
     *
     * @param friction
     */
    public void setFriction(float friction) {
        for (Fixture fixture : body.getFixtureList()) {
            fixture.setFriction(friction);
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

    public World getWorld() {
        return world;
    }

    /**
     * True if the fixture's body's user-data is this
     *
     * @param fixture
     * @return
     */
    public boolean contains(Fixture fixture) {
        Body fBody = fixture.getBody();
        return fBody == null ? false : equals(fBody.getUserData());
    }

    @Override
    public boolean isActive() {
        return deferredActiveChange != 0 ? deferredActiveChange > 0 : body.isActive();
    }

    public void setActive(boolean active) {
        // Don't take any action if there's no change
        if(active == isActive()) {
            return;
        }

        if (world.locked()) {
            deferredActiveChange += active ? 1 : -1;
        } else {
            body.setActive(active);
        }
        onSetActive(active);
    }

    public void onSetActive(boolean active) {

    }

    public void update(float delta) {
        if(deferredActiveChange != 0) {
            if(!world.locked()) {
                body.setActive(deferredActiveChange > 0);
                deferredActiveChange = 0;
            }
        }
    }

    @Override
    public void setNext(WorldBody next) {
        poolNext = next;
    }

    @Override
    public WorldBody getNext() {
        return poolNext;
    }

    @Override
    public WorldBody reset() {
        body.setActive(true);
        body.setAngularVelocity(0);
        body.setLinearVelocity(0, 0);
        body.resetMassData();
        return this;
    }

}
