package crossj.engine.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import crossj.engine.event.EventDispatcher;
import crossj.engine.fonts.TTFontCache;
import crossj.engine.rendering.Viewport;
import crossj.engine.util.Graphics;

public abstract class Screen2D implements Screen, InputProcessor {
    protected final EventDispatcher eventDispatcher;
    protected final SpriteBatch spriteBatch;
    protected final String debugName;
    protected final TTFontCache fonts;
    protected final BitmapFont debugFont;
    protected final Vector2 debugPos = new Vector2();
    protected final Viewport viewport = new Viewport(1920, 1080);
    protected final Texture white = Graphics.pixel(0.1f, 0.1f, 0.1f, 1);

    public Screen2D() {
        this(null);
    }

    public Screen2D(String debugName) {
        if (debugName == null) {
            this.debugName = this.getClass().getSimpleName();
        } else {
            this.debugName = debugName;
        }

        viewport.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch = new SpriteBatch();
        eventDispatcher = new EventDispatcher();

        fonts = new TTFontCache();
        fonts.add("open-sans", Gdx.files.internal("data/OpenSans-Regular.ttf"));
        debugFont = fonts.get("open-sans", 28);
    }

    /**
     * Screens should usually handle drawing in the draw function.
     */
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        spriteBatch.setProjectionMatrix(viewport.camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(white, 0, 0, viewport.getReferenceWidth(), viewport.getReferenceHeight());
        draw(delta);
        spriteBatch.end();
        renderDebugInfo(delta);
    }

    protected abstract void draw(float delta);

    private void renderDebugInfo(float delta) {
        spriteBatch.setProjectionMatrix(viewport.camera.combined);
        spriteBatch.begin();
        debugPos.set(0, viewport.getReferenceHeight());
        debugFont.drawMultiLine(spriteBatch, getDebugString(), debugPos.x, debugPos.y);
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

    public String getDebugString() {
        return "Resolution: " + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight() + " | " + "FPS: "
                + Gdx.graphics.getFramesPerSecond() + " | " + "Screen: " + debugName;
    }

    @Override
    public void resize(int width, int height) {
        viewport.resize(width, height);
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
