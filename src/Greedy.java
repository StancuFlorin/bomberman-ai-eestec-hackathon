import java.util.List;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class Greedy {

    private Cell previousMe;
    public static boolean ADD_BOMB = true;

    public Cell start() {
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
        System.out.println("bestScore = " + bestScore);
        if (bestScore < 150) {
            ADD_BOMB = false;
        }
        return cell;
    }

    private int greedy(Cell cell, int depthLeft) {
        if (depthLeft == 0) {
            return 0;
        }

        int sum = 0;
        for (Cell neighbour : CellService.getFreeNeighbours(cell)) {
            previousMe = neighbour;
            if (neighbour.getSafeTimeLeft() == 0) {
                sum += 120 + greedy(neighbour, depthLeft - 1);
            } else {
                sum += neighbour.getSafeTimeLeft() * 10 + greedy(neighbour, depthLeft - 1);
            }
        }

        return sum;
    }

}
