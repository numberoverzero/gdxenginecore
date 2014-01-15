package crossj.engine.physics.raycasting;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class NearestRayCastCallback implements RayCastCallback {
    public RayCastResult result;

    public NearestRayCastCallback(RayCastResult result) {
        this.result = result;
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        // Since we're returning a fraction, the next result must be less than the last
        result.update(fixture, point, normal, fraction);
        return fraction;
    }
}
