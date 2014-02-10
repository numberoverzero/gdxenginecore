package crossj.engine.fonts;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

/**
 * Handles scaling - define sizes by name against a reference size. When view
 * dimensions change, call {@link #setActualDimensions} to rebuild fonts for the
 * new screen size with the given {@link ScaleMethod}
 */
public class FontManager {
    /**
     * Map from size name to reference size
     */
    private final Map<String, Integer> referenceSizes = new HashMap<>();

    /**
     * Map from size name to reference size
     */
    private final Map<String, Integer> actualSizes = new HashMap<>();

    private final Vector2 referenceDimensions = new Vector2(1920, 1080);
    private final Vector2 actualDimensions = new Vector2(1920, 1080);
    private ScaleMethod scaleMethod = ScaleMethod.NONE;
    private final TTFontCache cache = new TTFontCache();

    // ==================================
    // Constructors
    // ==================================

    public FontManager() {
    }

    public FontManager(ScaleMethod scaleMethod) {
        setScaleMethod(scaleMethod);
    }

    public FontManager(Vector2 reference, ScaleMethod scaleMethod) {
        setScaleMethod(scaleMethod);
        setReferenceDimensions(reference);
    }

    // ==================================
    // Public API
    // ==================================

    public void addFont(String fontName, FileHandle file) {
        // TODO
    }

    public void removeFont(String fontName) {
        // TODO
    }

    public void addSize(String name, int size) {
        // TODO
    }

    public void removeSize(String name) {
        // TODO
    }

    public Set<String> getSizes() {
        return referenceSizes.keySet();
    }

    public BitmapFont getFont(String fontName, String sizeName) {
        // TODO
        return null;
    }

    // ==================================
    // Setters and Getters
    // ==================================

    public void setScaleMethod(ScaleMethod scaleMethod) {
        this.scaleMethod = scaleMethod;
        // TODO rebuild
    }

    /**
     * Update the actual dimensions of the viewport
     */
    public void setActualDimensions(Vector2 actual) {
        setActualDimensions(actual.x, actual.y);
    }

    /**
     * Update the actual dimensions of the viewport
     */
    public void setActualDimensions(float width, float height) {
        actualDimensions.set(width, height);
        // TODO rebuild
    }

    public void setReferenceDimensions(Vector2 reference) {
        setReferenceDimensions(reference.x, reference.y);
    }

    public void setReferenceDimensions(float width, float height) {
        referenceDimensions.set(width, height);
        // TODO rebuild
    }

    // ==================================
    // Actual work
    // ==================================

    /**
     * Scale between reference and actual dimensions based on
     * {@link #scaleMethod}.
     */
    private float getScale() {
        return 0.0f;
    }
}
