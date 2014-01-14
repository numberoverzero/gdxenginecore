package crossj.engine.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import crossj.engine.objects.Tracker;
import crossj.engine.util.Math;

/**
 * Actor with a single, static texture.
 *
 */
public class StaticActor implements Actor {
    private final Texture texture;
    private final Vector2 origin;
    private final Tracker tracker;
    private final float width, height;
    private boolean enabled;

    public StaticActor(Texture texture) {
        this(texture, 0, 0);
    }

    public StaticActor(Texture texture, float x, float y) {
        this(texture, x, y, texture.getWidth(), texture.getHeight());
    }

    public StaticActor(Texture texture, float x, float y, float width, float height) {
        this.texture = texture;
        tracker = new Tracker();
        tracker.track(new Vector2(x, y));
        origin = new Vector2(0, 0);
        this.width = width;
        this.height = height;
        enabled = true;
    }

    @Override
    public void setOrigin(Vector2 origin) {
        this.origin.set(Math.constrain(origin.cpy(), 0, 1));
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void act(SpriteBatch spriteBatch, float delta) {
        if (!isEnabled()) {
            return;
        }
        Vector2 position = tracker.getPosition();
        spriteBatch.draw(texture, position.x - origin.x * texture.getWidth(),
                position.y - origin.y * texture.getHeight(), width, height);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void reset() {
        // no-op for static sprite
    }

    @Override
    public Tracker getTracker() {
        return tracker;
    }
}
