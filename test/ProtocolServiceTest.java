import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by StancuFlorin on 10/31/2015.
 */

public class ProtocolServiceTest {

    private ProtocolService protocolService;

    @Before
    public void setUp() {
        this.protocolService = new ProtocolService(null);
    }

    @Test
    public void testGetCommand() {
        for (int i = 0; i <= 4; i++) {
            int x = protocolService.getCommand(i, true);
            Assert.assertEquals(x, (1 << 31) + i);
        }

        for (int i = 0; i <= 4; i++) {
            int x = protocolService.getCommand(i, false);
            Assert.assertEquals(x, i);
        }
    }

}
