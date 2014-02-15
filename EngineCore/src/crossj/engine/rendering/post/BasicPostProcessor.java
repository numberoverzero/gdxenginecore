package crossj.engine.rendering.post;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import crossj.engine.rendering.FBORenderTarget;

/**
 * Single shader PostProcessing step
 *
 */
public class BasicPostProcessor extends FBORenderTarget implements PostProcessor {

    private ShaderProgram shader;
    private SpriteBatch spriteBatch;

    public BasicPostProcessor(int width, int height) {
        this(width, height, null);
    }

    public BasicPostProcessor(int width, int height, ShaderProgram shader) {
        super(width, height);
        setShader(shader);
    }

    public void setShader(ShaderProgram shader) {
        this.shader = shader;
    }

    @Override
    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void begin() {
        if (spriteBatch == null) {
            throw new RuntimeException("Must set PostProcessor's SpriteBatch before rendering");
        }
        super.begin();
        spriteBatch.setShader(shader);
    }

    @Override
    public void end() {
        super.end();
        spriteBatch.setShader(null);
    }

}
