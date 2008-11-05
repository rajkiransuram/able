package able.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class NetworkUtils {
    public static String getPrivateLocalAddress() {
        try {
            ArrayList<String> ips = new ArrayList<String>();
            Enumeration<NetworkInterface> m = NetworkInterface.getNetworkInterfaces();
            while (m.hasMoreElements()) {
                NetworkInterface ni = m.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    String ip = address.getHostAddress();

                    // filter out Inet6 Addr Entries
                    if (ip.indexOf(":") == -1) {
                        ips.add(ip);
                    }
                }
            }

            // don't eveb bother keeping this around
            ips.remove("127.0.0.1");

            if (!ips.isEmpty()) {
                for (String ip : ips) {
                    if (ip.startsWith("192.168.0.")) {
                        return ip;
                    }
                }

                // still didn't find an ideal one? just pick the first one
                return ips.get(0);
            }
        } catch (SocketException e) {
            // ignore
        }

        return null;
    }
}
