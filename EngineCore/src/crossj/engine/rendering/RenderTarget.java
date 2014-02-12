package crossj.engine.rendering;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public interface RenderTarget extends Disposable {

    public void begin();

    public void end();

    public TextureRegion getRegion();

    public int getWidth();

    public int getHeight();

}