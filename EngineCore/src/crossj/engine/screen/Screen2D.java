package crossj.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import crossj.engine.event.EventDispatcher;
import crossj.engine.input.InputProcessorAdapter;

public abstract class Screen2D implements Screen {
    protected final EventDispatcher eventDispatcher;
    protected final OrthographicCamera camera;
    protected final SpriteBatch spriteBatch;
    protected InputProcessor inputProcessor;
    protected final String debugName;

    public Screen2D(String debugName) {
        this.debugName = debugName;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        spriteBatch = new SpriteBatch();
        eventDispatcher = new EventDispatcher();
        inputProcessor = new InputProcessorAdapter() {
        };
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }

    @Override
    public String getDebugName() {
        return debugName;
    }
}
