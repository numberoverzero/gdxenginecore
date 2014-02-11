package crossj.engine.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class RenderTarget {
    private final TextureRegion fboRegion;
    private final FrameBuffer fbo;

    public RenderTarget(Vector2 dimensions) {
        this((int) dimensions.x, (int) dimensions.y);
    }

    public RenderTarget(int width, int height) {
        // from https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Textures
        boolean npotSupported = Gdx.graphics.supportsExtension("GL_OES_texture_npot")
                || Gdx.graphics.supportsExtension("GL_ARB_texture_non_power_of_two");
        if (npotSupported) {
            fbo = new FrameBuffer(Format.RGBA8888, width, height, true);
            fboRegion = new TextureRegion(fbo.getColorBufferTexture());
        } else {
            int texWidth = MathUtils.nextPowerOfTwo(width);
            int texHeight = MathUtils.nextPowerOfTwo(height);
            fbo = new FrameBuffer(Format.RGBA8888, texWidth, texHeight, true);
            fboRegion = new TextureRegion(fbo.getColorBufferTexture(), 0, texHeight - height, width, height);
        }
        fboRegion.flip(false, true);
    }

    public void begin() {
        fbo.begin();
    }

    public void end() {
        fbo.end();
    }

    public TextureRegion getRegion() {
        return fboRegion;
    }

    public int getWidth() {
        return fboRegion.getRegionWidth();
    }

    public int getHeight() {
        return fboRegion.getRegionHeight();
    }

    /**
     * Render one target onto another.  Useful for processing pipelines.
     *
     * @param src
     * @param dst
     * @param spriteBatch
     * @param shader
     */
    public static void render(RenderTarget src, RenderTarget dst, SpriteBatch spriteBatch) {
        dst.begin();
        spriteBatch.begin();
        spriteBatch.draw(src.getRegion(), 0, 0);
        spriteBatch.end();
        dst.end();
    }

    /**
     * Post-processing pipeline, render each target onto the next
     */
    public static void render(SpriteBatch batch, RenderTarget... targets) {
        for (int i = 0; i < targets.length - 1; i++) {
            render(targets[i], targets[i + 1], batch);
        }
    }
}
