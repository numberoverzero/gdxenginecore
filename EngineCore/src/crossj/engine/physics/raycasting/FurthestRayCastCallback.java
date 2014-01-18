package crossj.engine.physics.raycasting;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

public class FurthestRayCastCallback extends RayCastCallback {
    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if (!result.hasValue || fraction > result.fraction) {
            result.update(fixture, point, normal, fraction);
            result.hasValue = true;
        }
        return 1;
    }
}
