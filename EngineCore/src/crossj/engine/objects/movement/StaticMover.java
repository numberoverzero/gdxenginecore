package crossj.engine.objects.movement;

import com.badlogic.gdx.math.Vector2;

/**
 * Not attached to any body - basically a wrapper around a Vector2.
 */
public class StaticMover implements Mover {
    Vector2 position = new Vector2();

    public StaticMover() {
    }

    public StaticMover(Vector2 position) {
        setPosition(position);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position.set(position);

    }

}
