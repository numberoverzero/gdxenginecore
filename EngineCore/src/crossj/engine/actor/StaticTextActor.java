package crossj.engine.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import crossj.engine.objects.Tracker;
import crossj.engine.util.Math;

public class StaticTextActor implements Actor {
    private final BitmapFont font;
    private final String text;
    private final Vector2 origin;
    private final Tracker tracker;
    private boolean enabled;

    public StaticTextActor(BitmapFont font, String text) {
        this(font, text, 0, 0);
    }

    public StaticTextActor(BitmapFont font, String text, float x, float y) {
        this.font = font;
        this.text = text;
        tracker = new Tracker();
        tracker.track(new Vector2(x, y));
        origin = new Vector2(0, 0);
        enabled = true;
    }

    @Override
    public void setOrigin(Vector2 origin) {
        this.origin.set(Math.constrain(origin.cpy(), 0, 1));
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
        Vector2 position = tracker.getPosition();
        font.draw(spriteBatch, text, position.x - origin.x * bounds.width, position.y + origin.y * bounds.height);

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

    @Override
    public Tracker getTracker() {
        return tracker;
    }
}
