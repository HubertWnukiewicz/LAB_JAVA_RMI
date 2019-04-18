import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class PortFactor {

    static int startingPort = 1997;

    public static int getPort() {
        int portToReturn = startingPort;
        while (true) {
            if (available(portToReturn)) {
                return portToReturn;
            } else {
                portToReturn++;
            }
        }
    }

    public static boolean available(int port) {
        if (port < 1997 || port > 3000) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                }
            }
        }

        return false;
    }

}