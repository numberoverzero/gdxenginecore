package crossj.engine.fonts;

/**
 * Describes how fonts are scaled against a reference size and viewport.
 */
public enum ScaleMethod {
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
     * a font along the component not chosen
     */
    MAXIMUM,

    /**
     * Use the smaller of vertical or horizontal scale. This will always
     * compress a font along the component not chosen
     */
    MINIMUM,

    /**
     * Scale by the average of the horizontal and vertical scale. This will give
     * equal distortion (stretching, compressing) along both components.
     *
     * Example: horizontal scale is 1.5, vertical scale is 3.0. This will scale
     * by 2.25, which will stretch horizontally and compress vertically
     */
    EQUAL,

    /**
     * The reference size is always used, regardless of the current screen
     * resolution.
     */
    NONE

}
