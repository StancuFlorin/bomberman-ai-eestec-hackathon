import java.util.ArrayList;
import java.util.List;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class CellService {

    public static void populateNeighbourCellsWithSafeTimeLeft(List<Cell> cellsWithBombs) {
        for (Cell cell : cellsWithBombs) {
            for (Cell neighbour : getDangerousCells(cell)) {
                neighbour.setSafeTimeLeft(cell.getBombTimeLeft());
            }
        }
    }

    public static List<Cell> getFreeNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();

        int x = cell.getX();
        int y = cell.getY();

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(i != 0 && j != 0) {
                    if(isOnBoard(x+i, y+i) && Information.BOARD[x+i][y+j].isFree()) {
                        neighbours.add(Information.BOARD[x+i][y+i]);
                    }
                }
            }
        }

        return neighbours;
    }

    public static List<Cell> getDangerousCells(Cell bombCell) {
        List<Cell> neighbours = new ArrayList<>();

        int x = bombCell.getX();
        int y = bombCell.getY();

        for(int i = 1; i <= 6; i++) {
            if(isOnBoard(x+i, y) && !Information.BOARD[x+i][y].isWall()) {
                neighbours.add(Information.BOARD[x+i][y]);
            }

            if(isOnBoard(x-i, y) && !Information.BOARD[x-i][y].isWall()) {
                neighbours.add(Information.BOARD[x-i][y]);
            }

            if(isOnBoard(x, y+i) && !Information.BOARD[x][y+i].isWall()) {
                neighbours.add(Information.BOARD[x][y+i]);
            }

            if(isOnBoard(x, y-i) && !Information.BOARD[x][y-i].isWall()) {
                neighbours.add(Information.BOARD[x][y-i]);
            }
        }

        return neighbours;
    }

    public static boolean isOnBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < Information.BOARD_N && y < Information.BOARD_M;
    }
}
