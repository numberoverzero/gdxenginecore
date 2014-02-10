package crossj.engine.theme;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.XmlReader.Element;

import crossj.engine.util.XmlUtil;

public class ColorScheme {
    private static final String XML_SECTION = "color-scheme";
    private static final String XML_ENTRY = "color";
    private static final String XML_ENTRY_KEY = "key";
    private static final String XML_ENTRY_VALUE = "value";

    public final Map<String, Color> colors = new HashMap<>();

    public ColorScheme() {

    }

    public ColorScheme(String... keys) {
        for (String key : keys) {
            colors.put(key, new Color());
        }
    }

    public static ColorScheme load(FileHandle file) {
        return load(file, null);
    }

    public static ColorScheme load(FileHandle file, String subsection) {
        ColorScheme scheme = new ColorScheme();
        Element root = XmlUtil.parse(file);
        root = XmlUtil.getChild(root, subsection, XML_SECTION);
        for (Element entry : root.getChildrenByName(XML_ENTRY)) {
            String key = entry.getAttribute(XML_ENTRY_KEY);
            String value = entry.getAttribute(XML_ENTRY_VALUE);
            scheme.colors.put(key, Color.valueOf(value));
        }
        return scheme;
    }

}
