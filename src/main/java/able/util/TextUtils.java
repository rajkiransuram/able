package able.util;

import java.io.IOException;
import java.io.InputStream;

public class TextUtils {
    public static int countInstances(String str, String match) {
        int index = -1;
        int count = -1;

        do {
            index = str.indexOf(match, index + 1);
            count++;
        } while (index != -1);

        return count;
    }

    public static String readToString(InputStream is) throws IOException {
        byte[] buffer = new byte[2048];
        int length;
        StringBuilder sb = new StringBuilder();
        while ((length = is.read(buffer)) != -1) {
            sb.append(new String(buffer, 0, length));
        }
        is.close();

        return sb.toString();
    }

    public static boolean stringSet(String str) {
        return str != null && !str.trim().equals("");
    }
}
