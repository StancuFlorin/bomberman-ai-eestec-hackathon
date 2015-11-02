import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.channels.SocketChannel;

public class Main {

/*    private static final String IP = "192.168.56.101";
    private static final Integer PORT = 10000;*/

    private static int rounds = 30;

    public static void main(String[] args) throws IOException {

        String IP = args[0];
        Integer PORT = Integer.parseInt(args[1]);
        Integer STRATEGY = Integer.parseInt(args[2]);

        System.out.println("STRATEGY = " + STRATEGY);

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(IP, PORT));
        System.out.println("Connected!");

        ProtocolService protocolService = new ProtocolService(socketChannel);

        protocolService.readID();
        System.out.println("PLAYER_ID = " + Information.PLAYER_ID);

        while (true) {
            try {
                protocolService.readHeader();
                rounds--;
                //protocolService.sendMessage(Information.CURRENT_MOVE, PlayerService.getPlayerCommand(), true);

                if (STRATEGY == 0) {
                    Command c = PlayerService.getPlayerCommand();
                    //System.out.println(Information.BOMB);
                    if (rounds < 0) {
                        protocolService.sendMessage(Information.CURRENT_MOVE, c, Information.BOMB);
                        Information.BOMB = false;
                    } else {
                        protocolService.sendMessage(Information.CURRENT_MOVE, c, false);
                        Information.BOMB = false;
                    }
                } else if (STRATEGY == 1) {
                    protocolService.sendMessage(Information.CURRENT_MOVE, PlayerService.getGreedyCommand(), true);
                } else {
                    Command c = PlayerService.getPlayerCommand();
                    protocolService.sendMessage(Information.CURRENT_MOVE, c, false);
                }
            } catch (BufferUnderflowException e) {
                //e.printStackTrace();
                break;
            }
        }

        System.out.println("DONE!");
        socketChannel.close();
    }

}
