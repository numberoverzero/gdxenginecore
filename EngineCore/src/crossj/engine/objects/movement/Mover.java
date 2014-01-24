package crossj.engine.objects.movement;

import com.badlogic.gdx.math.Vector2;

public interface Mover {
    Vector2 getPosition();

    void setPosition(Vector2 position);
}
