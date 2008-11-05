package able.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpGetWaitCondition implements ThreadUtils.WaitCondition {
    private URL url;

    public HttpGetWaitCondition(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public boolean checkCondition(long elapsedTimeInMs) {
        try {
            URLConnection conn = url.openConnection();
            conn.connect();

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
