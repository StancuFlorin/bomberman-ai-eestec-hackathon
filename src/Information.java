import java.util.ArrayList;
import java.util.List;

/**
 * Created by StancuFlorin on 10/31/2015.
 */

public class Information {

    public static int CURRENT_MOVE;
    public static int AGGRESSIVE_MODE;
    public static int MAX_MOVES;

    public static int BOARD_N;
    public static int BOARD_M;
    public static Cell[][] BOARD;

    public static int PLAYER_ID;
    public static int PLAYER_I;
    public static int PLAYER_J;

    public static List<Cell> almostSafeCells = new ArrayList<>();

    public static boolean BOMB = false;
}
