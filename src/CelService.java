import java.util.ArrayList;
import java.util.List;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class CelService {

    public static void populateNeighbourCellsWithSafeTimeLeft(List<Cell> cellsWithBombs) {
        for (Cell cell : cellsWithBombs) {
            for (Cell neighbour : getNeighbours(cell)) {
                neighbour.setSafeTimeLeft(cell.getBombTimeLeft());
            }
        }
    }

    public static List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        return neighbours;
    }

}
