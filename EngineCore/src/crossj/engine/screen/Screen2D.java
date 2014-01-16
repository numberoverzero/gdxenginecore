package crossj.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import crossj.engine.event.EventDispatcher;
import crossj.engine.fonts.TTFontCache;
import crossj.engine.util.Graphics;

public abstract class Screen2D implements Screen, InputProcessor {
    protected final EventDispatcher eventDispatcher;
    protected final OrthographicCamera camera;
    protected final SpriteBatch spriteBatch;
    protected final String debugName;
    protected final TTFontCache fonts;
    protected final BitmapFont debugFont;

    public Screen2D() {
        this(null);
    }

    public Screen2D(String debugName) {
        if (debugName == null) {
            this.debugName = this.getClass().getSimpleName();
        } else {
            this.debugName = debugName;
        }
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        camera.setToOrtho(false);
        spriteBatch = new SpriteBatch();
        eventDispatcher = new EventDispatcher();

        fonts = new TTFontCache();
        fonts.add("open-sans", "data/OpenSans-Regular.ttf");
        debugFont = fonts.get("open-sans", 18);
    }

    /**
     * Screens should usually handle drawing in the draw function.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        draw(delta);
        spriteBatch.end();

        renderDebugInfo(delta);
    }

    protected abstract void draw(float delta);

    private void renderDebugInfo(float delta) {
        Vector2 position = Graphics.unproject(camera, 0, 0);
        String text = "Screen: " + debugName + "\n" + "FPS: " + Gdx.graphics.getFramesPerSecond();

        spriteBatch.begin();
        debugFont.drawMultiLine(spriteBatch, text, position.x, position.y);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        fonts.dispose();
    }

    @Override
    public final InputProcessor getInputProcessor() {
        return this;
    }

    @Override
    public final String getDebugName() {
        return debugName;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
