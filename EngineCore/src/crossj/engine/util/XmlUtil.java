package crossj.engine.util;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class XmlUtil {

    /**
     * Return the root node of an XML file
     */
    public static Element parse(FileHandle file) {
        XmlReader reader = new XmlReader();
        try {
            return reader.parse(file);
        } catch (IOException e) {
            throw new RuntimeException("Exception while trying to load XML file '" + file.name() + "':", e);
        }
    }

    /**
     * Descend multiple names, such as getChild(root, "foo", "bar", "thing") for
     * getting the element with value "value" in:
     *
     * <pre>
     * &lt;foo&gt;
     *   &lt;bar&gt;
     *     &lt;thing&gt;
     *       value
     *     &lt;/thing&gt;
     *   &lt;/bar&gt;
     * &lt;/foo&gt;
     * </pre>
     */
    public static Element getChild(Element root, String... subsections) {
        for (String subsection : subsections) {
            if (subsection == null) {
                continue;
            }
            root = root.getChildByName(subsection);
        }
        return root;
    }
}
