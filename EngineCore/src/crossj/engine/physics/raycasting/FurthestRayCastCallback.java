package crossj.engine.physics.raycasting;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class FurthestRayCastCallback implements RayCastCallback {
    public RayCastResult result;

    public FurthestRayCastCallback(RayCastResult result) {
        this.result = result;
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        // Don't need null check since RayCastResult initializes fraction to 0
        if (result.fraction < fraction) {
            result.update(fixture, point, normal, fraction);
        }
        return 1;
    }
}
