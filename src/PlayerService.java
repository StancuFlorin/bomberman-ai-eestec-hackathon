import java.util.ArrayList;
import java.util.List;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class PlayerService {

    public static List<Cell> getFreeMoves() {
        List<Cell> moves = new ArrayList<>();
        if (Information.PLAYER_I - 1 != -1) { // UP
            Cell cell = Information.BOARD[Information.PLAYER_I - 1][Information.PLAYER_J];
            if (cell.isFree()) {
                moves.add(cell);
            }
        }

        if (Information.PLAYER_I + 1 != Information.BOARD_N) { // DOWN
            Cell cell = Information.BOARD[Information.PLAYER_I + 1][Information.PLAYER_J];
            if (cell.isFree()) {
                moves.add(cell);
            }
        }

        if (Information.PLAYER_J - 1 != -1) { // LEFT
            Cell cell = Information.BOARD[Information.PLAYER_I][Information.PLAYER_J - 1];
            if (cell.isFree()) {
                moves.add(cell);
            }
        }

        if (Information.PLAYER_J + 1 != Information.BOARD_M) { // RIGHT
            Cell cell = Information.BOARD[Information.PLAYER_I][Information.PLAYER_J + 1];
            if (cell.isFree()) {
                moves.add(cell);
            }
        }

        return moves;
    }

}
