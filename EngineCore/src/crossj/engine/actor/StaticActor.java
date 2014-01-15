package crossj.engine.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import crossj.engine.objects.Tracker;

/**
 * Actor with a single, static texture.
 *
 */
public class StaticActor implements Actor {
    private final Texture texture;
    private final TextureRegion region;
    private final Vector2 origin, renderPosition;
    private final Tracker tracker;
    private final float width, height;
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
        this.width = width;
        this.height = height;
        this.rotation = rotation;

        region = new TextureRegion(texture);
        tracker = new Tracker();
        tracker.track(new Vector2(x, y));
        origin = Vector2.Zero.cpy();
        renderPosition = Vector2.Zero.cpy();
        enabled = true;
    }

    @Override
    public void setOrigin(Vector2 origin) {
        this.origin.set(origin).scl(width, height);
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
        renderPosition.set(tracker.getPosition()).sub(origin);
        spriteBatch.draw(region, renderPosition.x, renderPosition.y, origin.x, origin.y, width, height, 1, 1, rotation
                * MathUtils.radiansToDegrees);
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
