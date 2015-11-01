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

        Cell currentCell = CellService.getPlayerCell();
        Cell futureCell = pathFinder.nextMove();

        Command command = Command.getCommand(currentCell, futureCell);
        System.out.println("Command = " + command);
        return command;
    }

    public static Command getGreedyCommand() {
        Greedy greedy = new Greedy();
        Cell nextMove = greedy.start();
        Cell playerCell = CellService.getPlayerCell();
        Command command = Command.NONE;
        if (nextMove != null) {
            command = Command.getCommand(playerCell, nextMove);
        }

        System.out.println("command = " + command);
        return command;
    }

    public static Command getNegamaxCommand() {
        Negamax negamax = new Negamax();
        Cell nextMove = negamax.startNegamax();
        Cell playerCell = CellService.getPlayerCell();

        Command command = Command.getCommand(playerCell, nextMove);
        System.out.println("command = " + command);
        return command;
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
