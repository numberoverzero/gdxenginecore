package crossj.engine.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Disposable;

public class World implements Disposable {
    private static final float DEFAULT_BOX_STEP = 1 / 60f;
    private static final int DEFAULT_BOX_VELOCITY_ITERATIONS = 6;
    private static final int DEFAULT_BOX_POSITION_ITERATIONS = 2;
    private static final float DEFAULT_WORLD_TO_BOX = 0.01f;
    private static final float DEFAULT_BOX_TO_WORLD = 100f;

    private final float step, worldToBox, boxToWorld;
    private final int velocityIterations, positionIterations;

    com.badlogic.gdx.physics.box2d.World world;
    private float accumulator; // http://gafferongames.com/game-physics/fix-your-timestep/

    public World() {
        this(Vector2.Zero);
    }

    public World(Vector2 gravity) {
        accumulator = 0;
        world = new com.badlogic.gdx.physics.box2d.World(gravity, true);
        step = DEFAULT_BOX_STEP;
        worldToBox = DEFAULT_WORLD_TO_BOX;
        boxToWorld = DEFAULT_BOX_TO_WORLD;
        velocityIterations = DEFAULT_BOX_VELOCITY_ITERATIONS;
        positionIterations = DEFAULT_BOX_POSITION_ITERATIONS;
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
        world.dispose();
    }

    public Body createBody(BodyDef def) {
        return world.createBody(def);
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

    public com.badlogic.gdx.physics.box2d.World getWorld() {
        return world;
    }

}
