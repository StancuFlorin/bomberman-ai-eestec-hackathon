import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Main {

    private static final String IP = "192.168.56.101";
    private static final Integer PORT = 10000;

    public static void main(String[] args) throws IOException {

        System.out.println("Hello World!");

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(IP, PORT));
        System.out.println("Connected!");

        ProtocolService protocolService = new ProtocolService(socketChannel);

        protocolService.readID();
        System.out.println("ID = " + Information.ID);

        protocolService.readHeader();
        System.out.println("CURRENT_MOVE = " + Information.CURRENT_MOVE);
        System.out.println("AGGRESSIVE_MODE = " + Information.AGGRESSIVE_MODE);
        System.out.println("MAX_MOVE = " + Information.MAX_MOVE);
        System.out.println("N = " + Information.N);
        System.out.println("M = " + Information.M);

        /*
        int CURRENT_MOVE = protocolService.getNextInt();
        int AGGRESSIVE_MODE = protocolService.getNextInt();
        int MAX_MOVE = protocolService.getNextInt();
        int N = protocolService.getNextInt();
        int M = protocolService.getNextInt();


        System.out.println("CURRENT_MOVE = " + CURRENT_MOVE);
        System.out.println("AGGRESSIVE_MODE = " + AGGRESSIVE_MODE);
        System.out.println("MAX_MOVE = " + MAX_MOVE);
        System.out.println("N = " + N);
        System.out.println("M = " + M);
        */
    }
}
