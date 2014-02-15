package crossj.engine.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;

public class FBORenderTarget implements RenderTarget {
    private TextureRegion fboRegion;
    private FrameBuffer fbo;

    public FBORenderTarget(int width, int height) {
        resize(width, height);
    }

    @Override
    public void begin() {
        fbo.begin();
    }

    @Override
    public void end() {
        fbo.end();
    }

    @Override
    public TextureRegion getRegion() {
        return fboRegion;
    }

    @Override
    public int getWidth() {
        return fboRegion.getRegionWidth();
    }

    @Override
    public int getHeight() {
        return fboRegion.getRegionHeight();
    }

    @Override
    public void resize(int width, int height) {
        if (fbo != null) {
            fbo.dispose();
        }
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

    @Override
    public void dispose() {
        fbo.dispose();
    }
}
