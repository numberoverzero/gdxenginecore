package crossj.engine.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import crossj.engine.util.Math;

public class StaticTextActor implements Actor {
    private final BitmapFont font;
    private final String text;
    private float x, y, originX, originY;
    private boolean enabled;

    public StaticTextActor(BitmapFont font, String text) {
        this(font, text, 0, 0);
    }

    public StaticTextActor(BitmapFont font, String text, float x, float y) {
        this.font = font;
        this.text = text;
        this.x = x;
        this.y = y;
        enabled = true;
    }

    @Override
    public void setOrigin(float x, float y) {
        originX = Math.constrain(x, 0, 1);
        originY = Math.constrain(y, 0, 1);
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    @Override
    public void act(SpriteBatch spriteBatch, float delta) {
        if (!isEnabled()) {
            return;
        }
        TextBounds bounds = font.getBounds(text);
        font.draw(spriteBatch, text, x - originX * bounds.width, y + originY * bounds.height);

    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void reset() {
        // no-op for static text
    }
}
