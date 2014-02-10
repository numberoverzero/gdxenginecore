package crossj.engine.fonts;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

/**
 * Stores font generators and baked fonts for quick rendering without
 * remembering all of the internals of true type fonts and such.
 */
public class TTFontCache implements Disposable {
    Map<String, FreeTypeFontGenerator> generators;
    Map<String, Map<Integer, BitmapFont>> bitmapFonts;

    public TTFontCache() {
        generators = new HashMap<>();
        bitmapFonts = new HashMap<>();
    }

    /**
     * Associate a font with a given (internal) .tff file
     */
    public void add(String name, String path) {
        add(name, Gdx.files.internal(path));
    }

    /**
     * Associate a font with a given .tff file
     */
    public void add(String name, FileHandle font) {
        if (contains(name)) {
            throw new RuntimeException("Font '" + name + "' already exists.");
        }
        generators.put(name, new FreeTypeFontGenerator(font));
    }

    /**
     * Remove a font (all sizes) from the cache
     */
    public void remove(String name) {
        FreeTypeFontGenerator generator = generators.remove(name);
        if (generator != null) {
            generator.dispose();
        }
        Map<Integer, BitmapFont> fonts = bitmapFonts.remove(name);
        if (fonts != null) {
            for (BitmapFont font : fonts.values()) {
                font.dispose();
            }
        }
    }

    /**
     * Remove a font of a specific size from the cache
     */
    public void remove(String name, int size) {
        BitmapFont font = getBitmapFonts(name).remove(size);
        if (font != null) {
            font.dispose();
        }
    }

    /**
     * True if there is a ttf associated with the given name
     */
    public boolean contains(String name) {
        return generators.containsKey(name);
    }

    public BitmapFont get(String name, int size) {
        if (!contains(name)) {
            throw new RuntimeException("Unknown font '" + name + "'.");
        }
        return getBitmapFont(name, size);
    }

    /**
     * The set of all font names known by the generator
     */
    public Set<String> getFonts() {
        return generators.keySet();
    }

    /**
     * The set of all sizes for a given font
     *
     * @param name
     * @return
     */
    public Set<Integer> getFontSizes(String name) {
        return getBitmapFonts(name).keySet();
    }

    private Map<Integer, BitmapFont> getBitmapFonts(String name) {
        Map<Integer, BitmapFont> fonts = bitmapFonts.get(name);
        if (fonts == null) {
            fonts = new HashMap<>();
            bitmapFonts.put(name, fonts);
        }
        return fonts;
    }

    private BitmapFont getBitmapFont(String name, int size) {
        BitmapFont font = getBitmapFonts(name).get(size);
        if (font == null) {
            font = generators.get(name).generateFont(size);
            getBitmapFonts(name).put(size, font);
        }
        return font;
    }

    @Override
    public void dispose() {
        for (String name : getFonts()) {
            remove(name);
        }
    }
}
