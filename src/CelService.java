import java.util.ArrayList;
import java.util.List;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class CelService {

    public static void populateNeighbourCellsWithSafeTimeLeft(List<Cell> cellsWithBombs) {
        for (Cell cell : cellsWithBombs) {
            for (Cell neighbour : getDangerousCells(cell)) {
                neighbour.setSafeTimeLeft(cell.getBombTimeLeft());
            }
        }
    }

    public static List<Cell> getDangerousCells(Cell bombCell) {
        List<Cell> neighbours = new ArrayList<>();

        int x = bombCell.getX();
        int y = bombCell.getY();

        for(int i = 1; i < 6; i++) {
            if(isOnBoard(x+1, y) && !Information.BOARD[x+1][y].isWall()) {
                neighbours.add(Information.BOARD[x+1][y]);
            }

            if(isOnBoard(x-1, y) && !Information.BOARD[x-1][y].isWall()) {
                neighbours.add(Information.BOARD[x-1][y]);
            }

            if(isOnBoard(x, y+1) && !Information.BOARD[x][y+1].isWall()) {
                neighbours.add(Information.BOARD[x][y+1]);
            }

            if(isOnBoard(x, y-1) && !Information.BOARD[x][y-1].isWall()) {
                neighbours.add(Information.BOARD[x][y-1]);
            }
        }

        return neighbours;
    }

    public static boolean isOnBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < Information.BOARD_N && y < Information.BOARD_M;
    }
}
