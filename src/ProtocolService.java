import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by StancuFlorin on 10/31/2015.
 */

public class ProtocolService {

    private SocketChannel socketChannel;

    public ProtocolService(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public void readID() throws IOException {
        System.out.println("readID()");

        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        while (byteBuffer.hasRemaining() && (socketChannel.read(byteBuffer)) > 0) {}
        byteBuffer.flip();

        Information.ID = byteBuffer.getInt();
    }

    public void readMessage() throws IOException {
        System.out.println("readMessage()");

        ByteBuffer byteBuffer = ByteBuffer.allocate(5 * 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        while (byteBuffer.hasRemaining() && (socketChannel.read(byteBuffer)) > 0) {}
        byteBuffer.flip();

        Information.CURRENT_MOVE = byteBuffer.getInt();
        Information.AGGRESSIVE_MODE = byteBuffer.getInt();
        Information.MAX_MOVES = byteBuffer.getInt();
        Information.N = byteBuffer.getInt();
        Information.M = byteBuffer.getInt();

        System.out.println("CURRENT_MOVE = " + Information.CURRENT_MOVE);
        System.out.println("AGGRESSIVE_MODE = " + Information.AGGRESSIVE_MODE);
        System.out.println("MAX_MOVES = " + Information.MAX_MOVES);
        System.out.println("N = " + Information.N);
        System.out.println("M = " + Information.M);

        createBoard();
    }

    private void createBoard() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Information.N * Information.M * 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        while (byteBuffer.hasRemaining() && (socketChannel.read(byteBuffer)) > 0) {}
        byteBuffer.flip();

        Information.BOARD = new Cell[Information.N][Information.M];
        List<Cell> cellsWithBombs = new ArrayList<>();

        for(int n = 0; n < Information.N; n++) {
            for(int m = 0; m < Information.M; m++) {
                byte[] bytes = {byteBuffer.get(), byteBuffer.get(), byteBuffer.get(), byteBuffer.get()};
                Cell cell = new Cell(bytes, n, m);
                Information.BOARD[n][m] = cell;

                if (cell.getBombTimeLeft() != 0) {
                    cellsWithBombs.add(cell);
                }

                if(Information.BOARD[n][m].isMyLocation()) {
                    Information.I = n;
                    Information.J = m;
                }
            }
        }

        CelService.populateNeighbourCellsWithSafeTimeLeft(cellsWithBombs);
    }

    private void sendMessage(int move, int command) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(move);
        byteBuffer.putInt(command);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

    private void sendMessage(int move, int command, boolean setBomb) throws IOException {
        sendMessage(move, getCommand(command, setBomb));
    }

    public void sendMessage(int move, Command command, boolean setBomb) throws IOException {
        System.out.println("sendMessage()");
        sendMessage(move, command.ordinal(), setBomb);
    }

    public int getCommand(int command, boolean setBomb) {
        int x = command;
        if (setBomb) {
            int mask = 1 << 31;
            x = x | mask;
        }
        return x;
    }

}
