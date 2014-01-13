package crossj.engine.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Fonts {
    /**
     * Generate a font of the given size, disposing of the generator used. Bad
     * for creating multiple fonts, as the generator creation/disposal is
     * expensive.
     */
    public static BitmapFont ttfFont(String path, int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        BitmapFont font = generator.generateFont(size);
        generator.dispose();
        return font;
    }
}
