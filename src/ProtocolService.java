import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

/**
 * Created by StancuFlorin on 10/31/2015.
 */

public class ProtocolService {

    private SocketChannel socketChannel;
    private static final int MAX_SIZE = (5 + 32 * 32) * 4;

    public ProtocolService(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public void readID() throws IOException {
        System.out.println("ID");
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        while (byteBuffer.hasRemaining() && (socketChannel.read(byteBuffer) > 0)) {}

        byteBuffer.flip();
        Information.ID = byteBuffer.getInt();
    }

    public void readMessage() throws IOException {
        System.out.println("HEADER");
        ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_SIZE);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        while (byteBuffer.hasRemaining() && (socketChannel.read(byteBuffer) > 0)) {}
        byteBuffer.flip();

        Information.CURRENT_MOVE = byteBuffer.getInt();
        Information.AGGRESSIVE_MODE = byteBuffer.getInt();
        Information.MAX_MOVES = byteBuffer.getInt();
        Information.N = byteBuffer.getInt();
        Information.M = byteBuffer.getInt();

        createBoard(byteBuffer);
    }

    private void createBoard(ByteBuffer byteBuffer) {
        Information.BOARD = new Cell[Information.N][Information.M];


    }

    private void sendMessage(int move, int command) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(move);
        byteBuffer.putInt(command);
        socketChannel.write(byteBuffer);
    }

    private void sendMessage(int move, int command, boolean setBomb) throws IOException {
        sendMessage(move, getCommand(command, setBomb));
    }

    public void sendMessage(int move, Command command, boolean setBomb) {
        sendMessage(move, command, setBomb);
    }

    public int getCommand(int command, boolean setBomb) {
        int x = command;
        if (setBomb) {
            x = x | 0b100000000;
        }
        return x;
    }

}
