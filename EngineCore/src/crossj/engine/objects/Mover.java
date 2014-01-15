package crossj.engine.objects;

import com.badlogic.gdx.math.Vector2;

import crossj.engine.physics.WorldBody;

public class Mover {
    private final float velocity;
    private final WorldBody body;
    private final Vector2 force;
    private final Vector2 netForce;

    public enum Direction {
        Up(0, 1), Down(0, -1), Left(-1, 0), Right(1, 0);

        public Vector2 unit;

        private Direction(float x, float y) {
            unit = new Vector2(x, y);
        }
    }

    public Mover(WorldBody body, float velocity) {
        this.body = body;
        this.velocity = velocity;
        force = new Vector2();
        netForce = new Vector2();
    }

    public void setInput(Direction direction, boolean pressed) {
        force.set(direction.unit).scl(pressed ? velocity : -velocity);
        netForce.add(force);
    }

    public void applyInput() {
        body.setLinearVelocity(netForce);
    }

    public void setPosition(Vector2 position) {
        body.setPosition(position);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
