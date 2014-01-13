package crossj.engine.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public interface Actor extends Disposable {

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void act(SpriteBatch spriteBatch, float delta);

    void setOrigin(float x, float y);

    void setPosition(float x, float y);

    void setPosition(Vector2 position);

    Vector2 getPosition();

    void reset();
}
