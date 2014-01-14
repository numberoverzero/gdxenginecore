package crossj.engine.physics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;

public class World implements Disposable {
    private static final float DEFAULT_BOX_STEP = 1 / 60f;
    private static final int DEFAULT_BOX_VELOCITY_ITERATIONS = 6;
    private static final int DEFAULT_BOX_POSITION_ITERATIONS = 2;
    private static final float DEFAULT_WORLD_TO_BOX = 0.01f;
    private static final float DEFAULT_BOX_TO_WORLD = 100f;

    private final float step, worldToBox, boxToWorld;
    private final int velocityIterations, positionIterations;
    private Box2DDebugRenderer debugRenderer;

    com.badlogic.gdx.physics.box2d.World world;
    private float accumulator; // http://gafferongames.com/game-physics/fix-your-timestep/
    private final List<WorldBody> managedBodies;

    public World() {
        this(new Vector2());
    }

    public World(Vector2 gravity) {
        accumulator = 0;
        world = new com.badlogic.gdx.physics.box2d.World(gravity, true);
        step = DEFAULT_BOX_STEP;
        worldToBox = DEFAULT_WORLD_TO_BOX;
        boxToWorld = DEFAULT_BOX_TO_WORLD;
        velocityIterations = DEFAULT_BOX_VELOCITY_ITERATIONS;
        positionIterations = DEFAULT_BOX_POSITION_ITERATIONS;
        managedBodies = new ArrayList<>();
    }

    public void setGravity(Vector2 gravity) {
        world.setGravity(gravity);
    }

    public Vector2 getGravity() {
        return world.getGravity();
    }

    public void step(float delta) {
        accumulator += delta;
        while (accumulator > step) {
            world.step(step, velocityIterations, positionIterations);
            accumulator -= step;
        }
    }

    @Override
    public void dispose() {
        managedBodies.clear();
        world.dispose();
        getDebugRenderer().dispose();
    }

    /**
     * Creates a managed body.
     */
    public WorldBody createBody(BodyDef def) {
        return createBody(def, true);
    }

    /**
     * Create a body, and optionally allow the world to manage it. If the world
     * is restarted, for instance, all managed bodies will be destroyed.
     */
    public WorldBody createBody(BodyDef def, boolean manageBody) {
        Body body = world.createBody(def);
        WorldBody wBody = new WorldBody(body, this);
        if (manageBody) {
            managedBodies.add(wBody);
        }
        return wBody;
    }

    public void destroyBody(WorldBody body) {
        managedBodies.remove(body);
        world.destroyBody(body.getBox2DBody());
    }

    public void restart() {
        for (WorldBody wBody : managedBodies) {
            world.destroyBody(wBody.getBox2DBody());
        }
        managedBodies.clear();
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

    /**
     * Don't create a debug renderer unless we need one
     * @return
     */
    private Box2DDebugRenderer getDebugRenderer() {
        if(debugRenderer == null) {
            debugRenderer = new Box2DDebugRenderer();
        }
        return debugRenderer;
    }

}
