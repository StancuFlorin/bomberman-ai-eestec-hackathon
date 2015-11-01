import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class PlayerService {

    public static Command getRandomMove() {
        List<Command> moves = getFreeMoves();
        if (moves.isEmpty()) {
            return Command.NONE;
        }
        Random random = new Random();
        int index = random.nextInt(moves.size());
        return moves.get(index);
    }

    public static Command getPlayerCommand() {
        PathFinder pathFinder = PathFinder.getInstance();
        pathFinder.prepareSearch(Information.BOARD, Information.PLAYER_I, Information.PLAYER_J);

        Cell currentCell = CellService.getPlayerCell();
        Cell futureCell = pathFinder.find()[0];

        return Command.getCommand(currentCell, futureCell);
    }

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
