package crossj.engine.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Graphics {
    public static final Vector2 CENTER = new Vector2(0.5f, 0.5f);

    public static Animation fromSpriteSheet(Texture texture, float frameDuration, int frameColumns, int frameRows) {
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / frameColumns, texture.getHeight()
                / frameRows);
        TextureRegion[] frames = new TextureRegion[frameColumns * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameColumns; j++) {
                frames[index] = tmp[i][j];
                index++;
            }
        }
        return new Animation(frameDuration, frames);
    }

    /**
     * Return white pixel
     */
    public static Texture pixel() {
        return pixel(1, 1, 1, 1);
    }

    public static Texture pixel(float r, float g, float b) {
        return pixel(r, g, b, 1);
    }

    public static Texture pixel(float r, float g, float b, float a) {
        return pixel(new Color(r, g, b, a));
    }

    public static Texture pixel(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        return new Texture(pixmap, pixmap.getFormat(), false);
    }

    /**
     * Convenience for rendering an entire texture with a given position,
     * origin, dimensions, and rotation.
     *
     * Rotation should be in radians.
     */
    public static void draw(SpriteBatch spriteBatch, Texture texture, Vector2 position, Vector2 origin,
            Vector2 dimensions, float rotation) {
        spriteBatch.draw(texture, position.x, position.y, origin.x, origin.y, dimensions.x, dimensions.y, 1, 1,
                rotation * MathUtils.radiansToDegrees, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    static final Color oldTint = new Color();
    static boolean isTinting = false;

    /**
     * Set the tint for a sprite batch, preserving the tint. Revert the tint
     * color with {@link #stopTint(SpriteBatch)}.
     *
     * Not thread safe, do not nest start calls, even for different sprite
     * batches.
     *
     * @param spriteBatch
     * @param tint
     */
    public static void startTint(SpriteBatch spriteBatch, Color tint) {
        if (isTinting) {
            throw new RuntimeException("Tried to start tinting again without calling stopTint first!");
        }
        isTinting = true;
        oldTint.set(spriteBatch.getColor());
        spriteBatch.setColor(tint);
    }

    /**
     * Revert the tint for a sprite batch.
     *
     * Not thread safe, do not nest stop calls, even for different sprite
     * batches.
     *
     * @param spriteBatch
     */
    public static void stopTint(SpriteBatch spriteBatch) {
        if (!isTinting) {
            throw new RuntimeException("Tried to stop tinting without calling startTine first!");
        }
        isTinting = false;
        spriteBatch.setColor(oldTint);
    }
}
