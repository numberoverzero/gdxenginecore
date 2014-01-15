package crossj.engine.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Graphics {
    private static Vector3 tmp = new Vector3();

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
     * Avoids an allocation for the result by setting the outVec
     */
    public static void unproject(Camera camera, float x, float y, Vector2 outVec) {
        camera.unproject(tmp.set(x, y, 0));
        outVec.set(tmp.x, tmp.y);
    }

    public static Vector2 unproject(Camera camera, float x, float y) {
        Vector2 outVec = new Vector2();
        unproject(camera, x, y, outVec);
        return outVec;
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
     * Rotation is provided in radians.
     */
    public static void draw(SpriteBatch spriteBatch, Texture texture, Vector2 position, Vector2 origin,
            Vector2 dimensions, float rotation) {
        spriteBatch.draw(texture, position.x, position.y, origin.x, origin.y, dimensions.x, dimensions.y, 1, 1,
                rotation * MathUtils.radiansToDegrees, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}
