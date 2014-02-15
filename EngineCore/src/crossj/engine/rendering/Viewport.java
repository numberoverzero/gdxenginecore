package crossj.engine.rendering;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * This allows the camera to keep width and height fixed to the reference screen
 * size (usually 1920x1080). Call begin() before drawing to render in reference
 * screen coordinates - use end() to render in real screen coordinates.
 *
 * Based off the work here:
 * http://www.acamara.es/blog/2012/02/keep-screen-aspect
 * -ratio-with-different-resolutions-using-libgdx/
 */
public class Viewport {
    private final List<ViewportListener> listeners = new ArrayList<>();
    public final OrthographicCamera camera;
    private final Rectangle viewport = new Rectangle();
    private final int referenceWidth, referenceHeight;
    private final float referenceRatio;

    private static Vector3 tmp = new Vector3();

    public Viewport(int referenceWidth, int referenceHeight) {
        this.referenceWidth = referenceWidth;
        this.referenceHeight = referenceHeight;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, referenceWidth, referenceHeight);
        referenceRatio = (float) referenceWidth / (float) referenceHeight;
    }

    public void addListener(ViewportListener listener) {
        listeners.add(listener);
    }

    /**
     * Set the size of the screen that the viewport will render on to.
     */
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        float scale = 1f;
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
        for (ViewportListener listener : listeners) {
            listener.onViewportResize(this, width, height);
        }
    }

    /**
     * Apply the viewport before rendering
     */
    public void begin() {
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
    }

    public void end() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

    /**
     * Project a point from world coordinates to screen coordinates
     */
    public void project(Vector2 point) {
        camera.project(tmp.set(point.x, point.y, 0), (int) viewport.x, (int) viewport.y, (int) viewport.width,
                (int) viewport.height);
        point.set(tmp.x, tmp.y);
    }

    /**
     * Unproject a point from screen coordinates to world coordinates
     */
    public void unproject(Vector2 point) {
        camera.unproject(tmp.set(point.x, point.y, 0), (int) viewport.x, (int) viewport.y, (int) viewport.width,
                (int) viewport.height);
        point.set(tmp.x, tmp.y);
    }

}
