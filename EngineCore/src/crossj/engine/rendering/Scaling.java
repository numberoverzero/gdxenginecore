package crossj.engine.rendering;

import com.badlogic.gdx.math.Vector2;

/**
 * Describes how to scale a reference size against a target viewport.
 */
public enum Scaling {

    /**
     * Scale is preserved along the horizontal component, stretching or
     * shrinking the vertical component
     */
    HORIZONTAL,

    /**
     * Scale is preserved along the vertical component, stretching or shrinking
     * the horizontal component
     */
    VERTICAL,

    /**
     * Use the larger of vertical or horizontal scale. This will always stretch
     * along the component not chosen
     */
    MAXIMUM,

    /**
     * Use the smaller of vertical or horizontal scale. This will always
     * compress along the component not chosen
     */
    MINIMUM,

    /**
     * Scale by the average of the horizontal and vertical scale. This will give
     * equal distortion (stretching, compressing) along both components.
     *
     * Example: horizontal scale is 1.5, vertical scale is 3.0. This will scale
     * by 2.25, which will stretch horizontally and compress vertically
     */
    AVERAGE,

    /**
     * Keep area proportional - horizontal and vertical components will be
     * scaled by an equal amount, such that the proportion of the reference and
     * target areas are equivalent.
     */
    AREA,

    /**
     * The reference size is always used, regardless of the current screen
     * resolution.
     */
    NONE;

    /**
     * Return the scale that should be applied to dimensions in reference
     * coordinates when rendering to the target coordinates.
     *
     * For example, if the reference coordinates are [1, 1] and the target is
     * [2, 3] and HORIZONTAL is used, the return scale of 2 means an object
     * which used to take up the whole area (1x1 object) will take up 2/3 of the
     * target area (2x2, with a 1x1 arena below the object open).
     */
    private static final Vector2 tmp = new Vector2();

    public float scale(Vector2 reference, Vector2 target) {
        tmp.set(target).div(reference);
        float scale = 1;

        switch (this) {
        case HORIZONTAL:
            scale = tmp.x;
            break;
        case VERTICAL:
            scale = tmp.y;
            break;
        case MAXIMUM:
            scale = tmp.x > tmp.y ? tmp.x : tmp.y;
            break;
        case MINIMUM:
            scale = tmp.x <= tmp.y ? tmp.x : tmp.y;
            break;
        case AVERAGE:
            scale = (tmp.x + tmp.y) / 2;
            break;
        case AREA:
            scale = (float) Math.sqrt((target.x * target.y) / (reference.x * reference.y));
            break;
        case NONE:
        default:
            break;
        }
        return scale;
    }
}
