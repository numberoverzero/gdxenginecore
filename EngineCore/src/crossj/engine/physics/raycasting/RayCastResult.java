package crossj.engine.physics.raycasting;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

public class RayCastResult {

    /**
     * Whether the result is non-null. Useful for re-using result objects - this
     * should be checked first, since the fixture may be non-null from a
     * previous search, but the latest search never updated it.
     */
    public boolean hasValue = false;

    /**
     * The fixture that intersected the ray, filtered by some criteria
     */
    public Fixture fixture;

    /**
     * The point at which the fixture and ray intersected
     */
    public final Vector2 point = new Vector2();

    /**
     * The normal from the center of the fixture intersected towards the point
     * of intersection
     */
    public final Vector2 normal = new Vector2();

    /**
     * The starting point of the ray
     */
    public final Vector2 start = new Vector2();

    /**
     * The ending point of the ray
     */
    public final Vector2 end = new Vector2();

    /**
     * The fraction of the distance along the ray of the intersection
     */
    public float fraction = 0;

    public RayCastResult() {
    }

    public RayCastResult(Vector2 start, Vector2 end) {
        this.start.set(start);
        this.end.set(end);
    }

    /**
     * Useful for dumping a raycast callback in an interative fashion, where
     * start/end do not change
     */
    public RayCastResult update(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        this.fixture = fixture;
        this.point.set(point);
        this.normal.set(normal);
        this.fraction = fraction;
        return this;
    }

    /**
     * Multiplies this result by a scalar.
     *
     * Useful for transforming the result to/from box/world coordinates
     *
     * @param scalar
     *            The scalar
     * @return This result for chaining
     */
    public RayCastResult scl(float scalar) {
        point.scl(scalar);
        start.scl(scalar);
        end.scl(scalar);
        return this;
    }
}
