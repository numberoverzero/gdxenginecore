package crossj.engine.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JavaUtil {

    /**
     * http://stackoverflow.com/a/10117051
     */
    public static <T> List<T> copy(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext()) {
            copy.add(iter.next());
        }
        return copy;
    }

    public static <T> List<T> copy(Iterable<T> iter) {
        List<T> copy = new ArrayList<T>();
        for (T t : iter) {
            copy.add(t);
        }
        return copy;
    }
}
