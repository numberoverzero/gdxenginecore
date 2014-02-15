package crossj.engine.rendering.post;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import crossj.engine.rendering.RenderTarget;

/**
 * Fullscreen post-processor - apply a single effect as part of an effects
 * pipeline
 *
 */
public interface PostProcessor extends RenderTarget {

    /**
     * A shared spritebatch which will be used throughout the pipeline. It is
     * important that the same spritebatch is used, so that any shaders,
     * coloring, or transforms set on the spritebatch in {@link #begin()} are
     * preserved while rendering to this target.
     *
     * @param spriteBatch
     */
    public void setSpriteBatch(SpriteBatch spriteBatch);

}
