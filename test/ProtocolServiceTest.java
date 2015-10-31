import org.junit.Test;

/**
 * Created by StancuFlorin on 10/31/2015.
 */

public class ProtocolServiceTest {

    private int getCommand(int command, boolean setBomb) {
        int x = command;
        if (setBomb) {
            x = x | 0b100000000;
        }
        return x;
    }

    @Test
    public void testSendMessage() {
        int command = 1;
        boolean setBomb = true;

        int x = getCommand(command, setBomb);
        System.out.println(x);
    }

}
