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

    private static final int INT_SIZE = 4;
    private ByteBuffer intByteBuffer;

    private SocketChannel socketChannel;

    public ProtocolService(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        this.intByteBuffer = ByteBuffer.allocate(INT_SIZE);
        this.intByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public void readID() throws IOException {
        System.out.println("ID");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        int bytesRead = socketChannel.read(byteBuffer);
        System.out.println("bytesRead = " + bytesRead);
        if (bytesRead == -1) {
            throw new ReadError();
        }
        byte byteID = byteBuffer.get(0);
        Information.ID = (int) byteID;
    }

    public int getNextInt() throws IOException {
        try {
            intByteBuffer.reset();
        } catch (InvalidMarkException exception) {}
        int bytesRead = socketChannel.read(intByteBuffer);
        System.out.println("bytesRead = " + bytesRead);
        if (bytesRead == -1) {
            return -1;
        }
        return intByteBuffer.getInt();
    }

}
