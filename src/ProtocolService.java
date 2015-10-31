import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

/**
 * Created by StancuFlorin on 10/31/2015.
 */

public class ProtocolService {

    SocketChannel socketChannel;

    public ProtocolService(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public int getID() throws IOException {
        System.out.println("ID");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        int bytesRead = socketChannel.read(byteBuffer);
        System.out.println("bytesRead = " + bytesRead);
        if (bytesRead == -1) {
            return -1;
        }
        byte byteID = byteBuffer.get(0);
        Integer ID = (int) byteID;
        System.out.println("ID = " + ID);
        return ID;
    }

}
