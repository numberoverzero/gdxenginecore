package crossj.engine.physics.raycasting;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

public class RayCastResult {
    /**
     * The fixture that intersected the ray, filtered by some criteria
     */
    public Fixture fixture;

    /**
     * The point at which the fixture and ray intersected
     */
    public Vector2 point = new Vector2();

    /**
     * The normal from the center of the fixture intersected towards the point
     * of intersection
     */
    public Vector2 normal = new Vector2();

    /**
     * The starting point of the ray
     */
    public Vector2 start = new Vector2();

    /**
     * The ending point of the ray
     */
    public Vector2 end = new Vector2();

    /**
     * The fraction of the distance along the ray of the intersection
     */
    public float fraction;

    public RayCastResult() {

    }

    public RayCastResult(Vector2 start, Vector2 end) {
        this.start.set(start);
        this.end.set(end);
    }

    public RayCastResult(RayCastResult other) {
        update(other);
    }

    public RayCastResult(Vector2 start, Vector2 end, Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        update(start, end, fixture, point, normal, fraction);
    }

    public void update(RayCastResult other) {
        update(other.start, other.end, other.fixture, other.point, other.normal, other.fraction);
    }

    /**
     * Useful for dumping a raycast callback in an interative fashion, where
     * start/end do not change
     */
    public void update(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        this.fixture = fixture;
        this.point.set(point);
        this.normal.set(normal);
        this.fraction = fraction;
    }

    public void update(Vector2 start, Vector2 end, Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        this.start.set(start);
        this.end.set(end);
        this.fixture = fixture;
        this.point.set(point);
        this.normal.set(normal);
        this.fraction = fraction;
    }

    public RayCastResult cpy(RayCastResult other) {
        return new RayCastResult(other);
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
