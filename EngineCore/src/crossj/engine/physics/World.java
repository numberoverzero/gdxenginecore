package crossj.engine.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.utils.Disposable;

import crossj.engine.physics.raycasting.RayCastCallback;
import crossj.engine.util.TimeDiscretizer;

public class World implements Disposable {
    private static final float DEFAULT_BOX_STEP = 1 / 60f;
    private static final int DEFAULT_BOX_VELOCITY_ITERATIONS = 8;
    private static final int DEFAULT_BOX_POSITION_ITERATIONS = 3;
    private static final float DEFAULT_WORLD_TO_BOX = 0.01f;
    private static final float DEFAULT_BOX_TO_WORLD = 100f;
    private static final float ZERO = 1f / (1 << 16);

    private final float worldToBox, boxToWorld;
    private final int velocityIterations, positionIterations;
    private final Vector2 tmp1, tmp2;

    private Box2DDebugRenderer debugRenderer;

    com.badlogic.gdx.physics.box2d.World world;
    private final TimeDiscretizer discretizer;

    // Automatically handle activating and deactivating bodies while stepping
    private boolean locked = false;

    public World() {
        this(new Vector2());
    }

    public World(Vector2 gravity) {
        world = new com.badlogic.gdx.physics.box2d.World(gravity, true);
        discretizer = new TimeDiscretizer(DEFAULT_BOX_STEP);
        worldToBox = DEFAULT_WORLD_TO_BOX;
        boxToWorld = DEFAULT_BOX_TO_WORLD;
        velocityIterations = DEFAULT_BOX_VELOCITY_ITERATIONS;
        positionIterations = DEFAULT_BOX_POSITION_ITERATIONS;
        tmp1 = new Vector2();
        tmp2 = new Vector2();
    }

    public void setContactListener(ContactListener listener) {
        world.setContactListener(listener);
    }

    public void setGravity(Vector2 gravity) {
        world.setGravity(gravity);
    }

    public Vector2 getGravity() {
        return world.getGravity();
    }

    public void step(float delta) {
        // Lock the world, update in discrete time steps
        locked = true;
        discretizer.update(delta);
        while (discretizer.step()) {
            world.step(discretizer.getStepSize(), velocityIterations, positionIterations);
        }
        locked = false;
    }

    public boolean locked() {
        return locked;
    }

    @Override
    public void dispose() {
        world.dispose();
        getDebugRenderer().dispose();
    }

    /**
     * Create a body
     */
    public WorldBody createBody(BodyDef def) {
        Body body = world.createBody(def);
        WorldBody wBody = new WorldBody(body, this);
        return wBody;
    }

    public void destroyBody(WorldBody body) {
        world.destroyBody(body.getBox2DBody());
    }

    public float toBoxScale() {
        return worldToBox;
    }

    public float toBox(float world) {
        return world * worldToBox;
    }

    public Vector2 toBox(Vector2 worldVec) {
        return worldVec.scl(worldToBox);
    }

    public float toWorldScale() {
        return boxToWorld;
    }

    public float toWorld(float box) {
        return box * boxToWorld;
    }

    public Vector2 toWorld(Vector2 boxVec) {
        return boxVec.scl(boxToWorld);
    }

    public com.badlogic.gdx.physics.box2d.World getBox2DWorld() {
        return world;
    }

    public void debugRender(Camera camera) {
        getDebugRenderer().render(getBox2DWorld(), camera.combined.cpy().scl(toWorldScale()));
    }

    public void rayCast(RayCastCallback callback, Vector2 point1, Vector2 point2) {
        if (point1.dst2(point2) <= ZERO) {
            Gdx.app.log("World.rayCast", "Called with zero distance");
            return;
        }
        callback.reset();
        callback.result.start.set(toBox(tmp1.set(point1)));
        callback.result.end.set(toBox(tmp2.set(point2)));
        world.rayCast(callback, tmp1, tmp2);
        callback.result.scl(toWorldScale());
    }

    /**
     * Don't create a debug renderer unless we need one
     *
     * @return
     */
    public Box2DDebugRenderer getDebugRenderer() {
        if (debugRenderer == null) {
            debugRenderer = new Box2DDebugRenderer();
        }
        return debugRenderer;
    }

}
