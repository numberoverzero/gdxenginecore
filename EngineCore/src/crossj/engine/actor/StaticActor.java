package crossj.engine.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import crossj.engine.util.Math;

/**
 * Actor with a single, static texture.
 * 
 */
public class StaticActor implements Actor {
    private final Texture texture;
    private float x, y, originX, originY;
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
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        enabled = true;
    }

    @Override
    public void setOrigin(float x, float y) {
        originX = Math.constrain(x, 0, 1);
        originY = Math.constrain(y, 0, 1);
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
        spriteBatch.draw(texture, x - originX * texture.getWidth(), y - originY * texture.getHeight(), width, height);
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
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
}
