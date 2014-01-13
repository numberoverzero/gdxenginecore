package crossj.engine.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Graphics {
    public static Animation fromSpriteSheet(Texture texture, float frameDuration, int frameColumns, int frameRows) {
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / frameColumns, texture.getHeight()
                / frameRows);
        TextureRegion[] frames = new TextureRegion[frameColumns * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameColumns; j++) {
                frames[index] = tmp[i][j];
                index++;
            }
        }
        return new Animation(frameDuration, frames);
    }
}
