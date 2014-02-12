package crossj.engine.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * This allows the orthographic camera to keep width and height fixed to the
 * screen size. Call apply() before drawing to scale rendering.
 *
 * Based off the work here:
 * http://www.acamara.es/blog/2012/02/keep-screen-aspect
 * -ratio-with-different-resolutions-using-libgdx/
 */
public class Viewport {
    private final Rectangle viewport = new Rectangle();
    private final int referenceWidth, referenceHeight;
    private final float referenceRatio;
    public float scale = 1;

    public Viewport(int referenceWidth, int referenceHeight) {
        this.referenceWidth = referenceWidth;
        this.referenceHeight = referenceHeight;
        referenceRatio = (float) referenceWidth / (float) referenceHeight;
    }

    /**
     * Set the size of the screen that the viewport will render on to.
     */
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        scale = 1f;
        float x = 0, y = 0;

        if (aspectRatio > referenceRatio) {
            scale = (float) height / (float) referenceHeight;
            x = (width - referenceWidth * scale) / 2;
        } else if (aspectRatio < referenceRatio) {
            scale = (float) width / (float) referenceWidth;
            y = (height - referenceHeight * scale) / 2;
        } else {
            scale = (float) width / (float) referenceWidth;
        }

        float w = referenceWidth * scale;
        float h = referenceHeight * scale;
        viewport.set(x, y, w, h);
    }

    /**
     * Apply the viewport before rendering
     */
    public void apply() {
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
    }

    /**
     * The reference width used when scaling
     */
    public int getReferenceWidth() {
        return referenceWidth;
    }

    /**
     * The reference height used when scaling
     */
    public int getReferenceHeight() {
        return referenceHeight;
    }

}
