package able.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProcessUtils {
    private static final Log LOG = new Log();

    public static void pipe(Process out, Process in) {
        pipe(out.getInputStream(), in.getOutputStream());
    }

    public static void pipe(Process out, OutputStream in) {
        pipe(out.getInputStream(), in);
    }

    public static void pipe(InputStream out, Process in) {
        pipe(out, in.getOutputStream());
    }


    public static void pipe(final InputStream out, final OutputStream in) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    IOUtils.copy(out, in);
                } catch (IOException e) {
                    LOG.fine("Problem copying piping stream", e);
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
