package crossj.engine.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public interface Actor extends Disposable {

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void act(SpriteBatch spriteBatch, float delta);

    /**
     * Set the offset for rendering the actor against the position it is
     * tracking (or locked at)
     *
     * @param origin
     */
    void setOrigin(Vector2 origin);

    /**
     * The tracking object which the actor should render against.
     */
    Tracker getTracker();

    void reset();
}
