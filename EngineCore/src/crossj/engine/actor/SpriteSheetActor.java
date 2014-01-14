package crossj.engine.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import crossj.engine.util.Graphics;
import crossj.engine.util.Math;

public class SpriteSheetActor implements Actor {
    private final Texture texture;
    private final Animation animation;
    private final Vector2 position, origin;
    private final float width, height;
    private boolean enabled;
    private float stateTime;

    public SpriteSheetActor(Texture texture, int frameColumns, int frameRows, float fps, int playMode) {
        this(texture, frameColumns, frameRows, fps, playMode, texture.getWidth() / frameColumns, texture.getHeight()
                / frameRows);
    }

    public SpriteSheetActor(Texture texture, int frameColumns, int frameRows, float fps, int playMode, int width,
            int height) {
        animation = Graphics.fromSpriteSheet(texture, 1 / fps, frameColumns, frameRows);
        animation.setPlayMode(playMode);
        position = new Vector2(0, 0);
        origin = new Vector2(0, 0);

        this.width = width;
        this.height = height;
        this.texture = texture;
        stateTime = 0;
        enabled = true;
    }

    @Override
    public void dispose() {
        texture.dispose();
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
    public void act(SpriteBatch spriteBatch, float delta) {
        if (!isEnabled()) {
            return;
        }
        stateTime += delta;
        TextureRegion frame = animation.getKeyFrame(stateTime);
        spriteBatch.draw(frame, position.x - origin.x * frame.getRegionWidth(),
                position.y - origin.y * frame.getRegionHeight(), width, height);
    }

    @Override
    public void setOrigin(Vector2 origin) {
        this.origin.set(Math.constrain(origin.cpy(), 0, 1));
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void reset() {
        stateTime = 0f;
    }
}
