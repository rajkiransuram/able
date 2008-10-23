package able.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketTestWaitCondition implements ThreadUtils.WaitCondition {
    public static void main(String[] args) {
        System.out.println(ThreadUtils.waitFor(new SocketTestWaitCondition("192.168.0.75", 22, 500),
                TimeUnit.MINUTES, 10,
                TimeUnit.SECONDS, 10));
    }

    private String host;
    private int port;
    private int timeout;

    public SocketTestWaitCondition(String host, int port, int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }

    public boolean checkCondition(long elapsedTimeInMs) {
        Socket socket = null;
        try {
            socket = new Socket();
            socket.bind(null);
            socket.connect(new InetSocketAddress(host, port), timeout);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
