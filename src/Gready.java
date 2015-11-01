import java.util.List;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class Gready {

    private Cell previousMe;

    public Cell startNegamax() {
        Cell playerCell = CellService.getPlayerCell();
        this.previousMe = playerCell;
        List<Cell> neighbours = CellService.getFreeNeighbours(playerCell);
        int bestScore = Integer.MIN_VALUE;
        Cell cell = null;
        for (Cell neighbour : neighbours) {
            int score = greedy(neighbour, 10);
            System.out.println(neighbour + " - " + score);
            if (score > bestScore) {
                bestScore = score;
                cell = neighbour;
            }
        }
        return cell;
    }

    private int greedy(Cell cell, int depthLeft) {
        if (depthLeft == 0) {
            return cell.getBombTimeLeft();
        }

        for (Cell neighbour : CellService.getFreeNeighbours(cell)) {
            return neighbour.getBombTimeLeft() + greedy(neighbour, depthLeft - 1);
        }

        return 0;
    }

}
