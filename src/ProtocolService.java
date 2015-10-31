import exception.ReadError;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
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
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        int bytesRead = socketChannel.read(byteBuffer);
        System.out.println("bytesRead = " + bytesRead);
        if (bytesRead == -1) {
            throw new ReadError("bytesRead == -1");
        }
        byte byteID = byteBuffer.get(0);
        Information.ID = (int) byteID;
    }

    public void readHeader() throws IOException {
        System.out.println("HEADER");
        ByteBuffer byteBuffer = ByteBuffer.allocate(MAX_SIZE);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        while (socketChannel.read(byteBuffer) > 0) {}
        byteBuffer.rewind();

        Information.CURRENT_MOVE = byteBuffer.getInt();
        Information.AGGRESSIVE_MODE = byteBuffer.getInt();
        Information.MAX_MOVE = byteBuffer.getInt();
        Information.N = byteBuffer.getInt();
        Information.M = byteBuffer.getInt();
    }

}
