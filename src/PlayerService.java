import java.util.ArrayList;
import java.util.List;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class PlayerService {

    public static List<Command> getFreeMoves() {
        List<Command> moves = new ArrayList<>();

        if (Information.PLAYER_I - 1 != -1) { // UP
            Cell cell = Information.BOARD[Information.PLAYER_I - 1][Information.PLAYER_J];
            if (cell.isFree()) {
                moves.add(Command.UP);
            }
        }

        if (Information.PLAYER_I + 1 != Information.BOARD_N) { // DOWN
            Cell cell = Information.BOARD[Information.PLAYER_I + 1][Information.PLAYER_J];
            if (cell.isFree()) {
                moves.add(Command.DOWN);
            }
        }

        if (Information.PLAYER_J - 1 != -1) { // LEFT
            Cell cell = Information.BOARD[Information.PLAYER_I][Information.PLAYER_J - 1];
            if (cell.isFree()) {
                moves.add(Command.LEFT);
            }
        }

        if (Information.PLAYER_J + 1 != Information.BOARD_M) { // RIGHT
            Cell cell = Information.BOARD[Information.PLAYER_I][Information.PLAYER_J + 1];
            if (cell.isFree()) {
                moves.add(Command.RIGHT);
            }
        }

        return moves;
    }

}
