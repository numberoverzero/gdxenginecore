package crossj.engine.objects.movement;

import com.badlogic.gdx.math.Vector2;

import crossj.engine.physics.WorldBody;

public class CartesianMover implements Mover {
    private final float maxSpeed;
    private final WorldBody body;
    private final Vector2 velocity = new Vector2();
    private final Vector2 netVelocity = new Vector2();
    private final Vector2 tmp = new Vector2();

    public enum Direction {
        Up(0, 1), Down(0, -1), Left(-1, 0), Right(1, 0);

        public Vector2 unit;

        private Direction(float x, float y) {
            unit = new Vector2(x, y);
        }
    }

    public CartesianMover(WorldBody body, float maxSpeed) {
        this.body = body;
        this.maxSpeed = maxSpeed;
    }

    public void setInput(Direction direction, boolean pressed) {
        velocity.set(direction.unit).scl(pressed ? maxSpeed : -maxSpeed);
        netVelocity.add(velocity);
    }

    public void applyInput() {
        body.setLinearVelocity(tmp.set(netVelocity).limit(maxSpeed));
    }

    @Override
    public void setPosition(Vector2 position) {
        body.setPosition(position);
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }
}
