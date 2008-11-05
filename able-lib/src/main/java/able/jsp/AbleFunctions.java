package able.jsp;

import java.util.Collection;

public class AbleFunctions {
    public static boolean contains(Collection c, Object o) {
        return c.contains(o);
    }

    public static String escapeQuotes(String s) {
        return s.replaceAll("\"", "\\").replaceAll("\'", "\\'");
    }
}
