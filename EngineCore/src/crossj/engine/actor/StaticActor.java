package crossj.engine.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import crossj.engine.objects.Tracker;
import crossj.engine.util.Graphics;

/**
 * Actor with a single, static texture.
 *
 */
public class StaticActor implements Actor {
    private final Texture texture;
    private final Vector2 origin, position, dimensions;
    private final Tracker tracker;
    private final float rotation;
    private boolean enabled;

    public StaticActor(Texture texture) {
        this(texture, 0, 0);
    }

    public StaticActor(Texture texture, float x, float y) {
        this(texture, x, y, texture.getWidth(), texture.getHeight(), 0);
    }

    public StaticActor(Texture texture, Vector2 position, Vector2 dimensions) {
        this(texture, position, dimensions, 0);
    }

    public StaticActor(Texture texture, Vector2 center, Vector2 dimensions, float rotation) {
        this(texture, center.x, center.y, dimensions.x, dimensions.y, rotation);
    }

    public StaticActor(Texture texture, float x, float y, float width, float height, float rotation) {
        this.texture = texture;
        this.rotation = rotation;

        tracker = new Tracker();
        tracker.track(new Vector2(x, y));

        dimensions = new Vector2(width, height);
        origin = new Vector2();
        position = new Vector2();
        enabled = true;
    }

    @Override
    public void setOrigin(Vector2 origin) {
        this.origin.set(origin).scl(dimensions);
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
        position.set(tracker.getPosition()).sub(origin);
        Graphics.draw(spriteBatch, texture, position, origin, dimensions, rotation);
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
