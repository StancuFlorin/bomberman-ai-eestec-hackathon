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

        Information.PLAYER_ID = byteBuffer.getInt();
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
        Information.BOARD_N = byteBuffer.getInt();
        Information.BOARD_M = byteBuffer.getInt();

        System.out.println("CURRENT_MOVE = " + Information.CURRENT_MOVE);
        System.out.println("AGGRESSIVE_MODE = " + Information.AGGRESSIVE_MODE);
        System.out.println("MAX_MOVES = " + Information.MAX_MOVES);
        System.out.println("BOARD_N = " + Information.BOARD_N);
        System.out.println("BOARD_M = " + Information.BOARD_M);

        createBoard();
    }

    private void createBoard() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Information.BOARD_N * Information.BOARD_M * 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        while (byteBuffer.hasRemaining() && (socketChannel.read(byteBuffer)) > 0) {}
        byteBuffer.flip();

        // read board information
        Information.BOARD = new Cell[Information.BOARD_N][Information.BOARD_M];
        List<Cell> cellsWithBombs = new ArrayList<>();

        //  create board
        for(int i = 0; i < Information.BOARD_N; i++) {
            for(int j = 0; j < Information.BOARD_M; j++) {
                byte[] bytes = {byteBuffer.get(), byteBuffer.get(), byteBuffer.get(), byteBuffer.get()};
                Cell cell = new Cell(bytes, i, j);
                Information.BOARD[i][j] = cell;

                if (cell.getBombTimeLeft() != 0) {
                    cellsWithBombs.add(cell);
                }

                if(Information.BOARD[i][j].isMyLocation()) {
                    Information.PLAYER_I = i;
                    Information.PLAYER_J = j;
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
