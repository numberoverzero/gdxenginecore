package crossj.engine.physics.raycasting;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Find a specific fixture along a ray, or null if the ray does not intersect
 * the fixture.
 */
public class FixtureRayCastCallback extends RayCastCallback {
    public Fixture fixture;

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if (fixture.equals(this.fixture)) {
            result.update(fixture, point, normal, fraction);
            result.hasValue = true;
            return 1;
        }
        return 1;
    }
}
