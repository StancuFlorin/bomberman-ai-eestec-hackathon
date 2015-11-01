import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class CellService {

    public static Cell getPlayerCell() {
        return Information.BOARD[Information.PLAYER_I][Information.PLAYER_J];
    }

    public static void populateNeighbourCellsWithSafeTimeLeft(List<Cell> cellsWithBombs) {
        for (Cell cell : cellsWithBombs) {
            for (Cell neighbour : getNeighboursFromBombArea(cell)) {
                neighbour.setSafeTimeLeft(cell.getBombTimeLeft());
            }
        }
    }

    public static List<Cell> getFreeNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();

        int i = cell.getX();
        int j = cell.getY();

        if (isOnBoard(i, j - 1) && Information.BOARD[i][j - 1].isFree()) {
            neighbours.add(Information.BOARD[i][j-1]);
        }

        if (isOnBoard(i, j + 1) && Information.BOARD[i][j + 1].isFree()) {
            neighbours.add(Information.BOARD[i][j + 1]);
        }

        if (isOnBoard(i - 1, j) && Information.BOARD[i - 1][j].isFree()) {
            neighbours.add(Information.BOARD[i - 1][j]);
        }

        if (isOnBoard(i + 1, j) && Information.BOARD[i + 1][j].isFree()) {
            neighbours.add(Information.BOARD[i + 1][j]);
        }

        return neighbours;
    }

    public static List<Cell> getNeighboursFromBombArea(Cell bombCell) {
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


    public static boolean addBomb() {
        Cell cell = CellService.getPlayerCell();
        List<Cell> neighbours = getNeighboursFromBombArea(cell);
        Set<Cell> neighboursSet = new HashSet<>();
        for (Cell neighbour : neighbours) {
            neighboursSet.add(neighbour);
            List<Cell> subNeighbours = getNeighboursFromBombArea(cell);
            for (Cell cell1 : subNeighbours) {
                neighboursSet.add(cell1);
            }
        }

        for (Cell neighbour : neighboursSet) {
            if (neighbour.getBombTimeLeft() != 0 && neighbour.getBombTimeLeft() < 3) {
                return false;
            }
        }
        return true;
    }

    public static boolean isReadyToExplode(Cell cell) {
        int cX = cell.getX();
        int cY = cell.getY();

        boolean flag = false;
        if (isOnBoard(cX, cY - 1)) {
            if (Information.BOARD[cX][cY - 1].getBombTimeLeft() <= 2 &&
                    Information.BOARD[cX][cY - 1].getBombTimeLeft() != 0) {
                flag = true;
            }
        }

        if (isOnBoard(cX, cY + 1)) {
            if (Information.BOARD[cX][cY + 1].getBombTimeLeft() <= 2 &&
                    Information.BOARD[cX][cY + 1].getBombTimeLeft() != 0) {
                flag = true;
            }
        }

        if (isOnBoard(cX - 1, cY)) {
            if (Information.BOARD[cX - 1][cY].getBombTimeLeft() <= 2 &&
                    Information.BOARD[cX - 1][cY].getBombTimeLeft() != 0) {
                flag = true;
            }
        }
        if (isOnBoard(cX + 1, cY)) {
            if (Information.BOARD[cX + 1][cY].getBombTimeLeft() <= 2 &&
                    Information.BOARD[cX + 1][cY].getBombTimeLeft() != 0) {
                flag = true;
            }
        }

        return flag;
    }
}
