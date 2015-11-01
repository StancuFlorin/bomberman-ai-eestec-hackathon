import java.util.ArrayList;
import java.util.List;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class PlayerService {

    public static List<Cell> getFreeMoves() {
        List<Cell> moves = new ArrayList<>();
        if (Information.I - 1 != -1) { // UP
            Cell cell = Information.BOARD[Information.I - 1][Information.J];
            if (cell.isFree()) {
                moves.add(cell);
            }
        }

        if (Information.I + 1 != Information.N) { // DOWN
            Cell cell = Information.BOARD[Information.I + 1][Information.J];
            if (cell.isFree()) {
                moves.add(cell);
            }
        }

        if (Information.J - 1 != -1) { // LEFT
            Cell cell = Information.BOARD[Information.I][Information.J - 1];
            if (cell.isFree()) {
                moves.add(cell);
            }
        }

        if (Information.J + 1 != Information.M) { // RIGHT
            Cell cell = Information.BOARD[Information.I][Information.J + 1];
            if (cell.isFree()) {
                moves.add(cell);
            }
        }

        return moves;
    }

}
