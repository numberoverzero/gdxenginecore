package crossj.engine.physics.raycasting;

import com.badlogic.gdx.math.Vector2;

public abstract class RayCastCallback implements com.badlogic.gdx.physics.box2d.RayCastCallback {
    public final RayCastResult result;

    public RayCastCallback() {
        result = new RayCastResult();
    }

    public RayCastCallback(RayCastResult result) {
        this.result = result;
    }

    /**
     * Reset the result, including zeroing any necessary fields
     */
    public void reset() {
        result.hasValue = false;
        result.fixture = null;
        result.point.set(Vector2.Zero);
        result.normal.set(Vector2.Zero);
        result.start.set(Vector2.Zero);
        result.end.set(Vector2.Zero);
        result.fraction = 0;
    }
}
