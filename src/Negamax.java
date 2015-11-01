import java.util.List;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class Negamax {

    private Cell previousMe;

    public Cell startNegamax() {
        Cell playerCell = CellService.getPlayerCell();
        this.previousMe = playerCell;
        List<Cell> neighbours = CellService.getFreeNeighbours(playerCell);
        int bestScore = Integer.MIN_VALUE;
        Cell cell = null;
        for (Cell neighbour : neighbours) {
            int score = alphaBeta(neighbour, Integer.MIN_VALUE, Integer.MAX_VALUE / 2, 10);
            System.out.println(neighbour + " - " + score);
            if (score > bestScore) {
                bestScore = score;
                cell = neighbour;
            }
        }
        return cell;
    }

    public int evaluate(Cell cell) {
        // sa nu mor
        if (cell.getFlameTimeLeft() != 0) {
            return -100;
        }

        if (cell.getSafeTimeLeft() != 0) {
            return -10 * cell.getSafeTimeLeft();
        } else if (cell.isFree()) {
            return 100;
        }

        return -100;
    }

    public int alphaBeta(Cell cell, int alpha, int beta, int depthLeft) {
        if (depthLeft == 0) {
            return evaluate(cell);
        }

        List<Cell> neighbours = CellService.getFreeNeighbours(cell);
        for (Cell neighbour : neighbours) {
            if (neighbour.equals(this.previousMe)) {
                continue;
            }

            //BoardService.nextBoardState();
            neighbour.nextState();
            this.previousMe = cell;
            int score = -alphaBeta(neighbour, -beta, -alpha, depthLeft - 1);
            neighbour.previousState();
            //BoardService.previousBoardState();
            if (score >= alpha) {
                alpha = score;
            }
            /*
            if (alpha >= beta) {
                break;
            }
            */
        }
        return alpha;
    }

    public static boolean addBomb() {
        Cell playerCell = CellService.getPlayerCell();
        System.out.println("current cell = " + playerCell.getSafeTimeLeft());
        if (playerCell.getSafeTimeLeft() != 0 && playerCell.getSafeTimeLeft() <= 7) {
            return false;
        }
        return true;
    }

}
