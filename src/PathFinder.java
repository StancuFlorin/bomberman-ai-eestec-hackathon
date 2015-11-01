import java.util.*;

/**
 * Created by adinu on 11/1/15.
 */
public class PathFinder {

    //TODO: change this
    public static boolean placeBomb;

    private static PathFinder instance = null;

    private PriorityQueue<Cell> openedCells;

    private Set<Cell> closedCells;

    private Cell last;

    private int minTotalCost = Integer.MAX_VALUE;

    private static final int LOOKUPS_LIMIT = 5;

    private PathFinder() {};

    public static PathFinder getInstance() {
        if (instance == null) {
            instance = new PathFinder();
        }

        return instance;
    }

    private void prepareSearch() {
        closedCells = new HashSet<>();
        openedCells = new PriorityQueue<>();

        openedCells.offer(Information.BOARD[Information.PLAYER_I][Information.PLAYER_J]);
        last = Information.BOARD[Information.PLAYER_I][Information.PLAYER_J];
        minTotalCost = Integer.MAX_VALUE;
        placeBomb = false;
    }

    public void updateParams(Cell front, int currX, int currY) {
        // parent numbers
        int gCurr = front.getArrivalCost();
        int hCurr = front.getHeuristicCost();

        // new calculated numbers
        int gNew = gCurr + 1 +
                   Information.BOARD[currX][currY].getDangerLevel()  +
                   front.getPrevSteps() + 1;
        int hNew = --hCurr;
        int fNew = gNew + hNew;

        if (fNew < Information.BOARD[currX][currY].getTotalCost()) {
            int prevG = Information.BOARD[currX][currY].getArrivalCost();
            Information.BOARD[currX][currY].setTotalCost(fNew);
            Information.BOARD[currX][currY].setHeuristicCost(hNew);
            Information.BOARD[currX][currY].setArrivalCost(gNew);
            Information.BOARD[currX][currY].setParent(front);
            Information.BOARD[currX][currY].setPrevSteps(front.getPrevSteps() + 1);

            //System.out.println("updated cell nowww: " + Information.BOARD[currX][currY].getX() +
            //        " " + Information.BOARD[currX][currY].getY());

            if (fNew < minTotalCost && Information.BOARD[currX][currY].getPrevSteps() > 2) {
                minTotalCost = fNew;
                last = Information.BOARD[currX][currY];
            }

            if (prevG == 0) {
                //System.out.println("offer = " + currX + " - " + currY);
                openedCells.offer(Information.BOARD[currX][currY]);
            }
        }
    }

    public Cell findPath() {
        prepareSearch();
        int nbOfMoves = LOOKUPS_LIMIT;

        // used for rolling back a
        while ((nbOfMoves >= 0) && (!openedCells.isEmpty())) {
            // get the front of the p queue
            Cell front = openedCells.remove();
            closedCells.add(front);

            // check adjacent cells
            int currX = front.getX();
            int currY = front.getY();
            int nextX = currX + 1;
            int nextY = currY + 1;
            int prevX = currX - 1;
            int prevY = currY - 1;
            // first let's take a look into the closed cells list
            if (!closedCells.contains(Information.BOARD[currX][prevY])) {
                // check the cell again for obstacles
                if (Information.BOARD[currX][prevY].isFree()) {
                    //System.out.println("checked for availability " + currX + ", " + prevY);
                    updateParams(front, currX, prevY);
                }
            }

            if (!closedCells.contains(Information.BOARD[currX][nextY])) {
                if (Information.BOARD[currX][nextY].isFree()) {
                    //System.out.println("checked for availability " + currX + ", " + nextY);
                    updateParams(front, currX, nextY);
                }
            }

            if (!closedCells.contains(Information.BOARD[prevX][currY])) {
                if (Information.BOARD[prevX][currY].isFree()) {
                    //System.out.println("checked for availability " + prevX + ", " + currY);
                    updateParams(front, prevX, currY);
                }
            }

            if (!closedCells.contains(Information.BOARD[nextX][currY])) {
                if (Information.BOARD[nextX][currY].isFree()) {
                    //System.out.println("checked for availability " + nextX + ", " + currY);
                    updateParams(front, nextX, currY);
                }
            }

            //System.out.println("nbOfMoves = " + nbOfMoves);
            nbOfMoves--;
        }

        //System.out.println("last at the end: " + last.getX() + " " + last.getY());

        return showNextCell();
    }

    private Cell showNextCell() {
        if (last.getParent() == null) {
            return null;
        }

        Cell curr = last.getParent();

        // too bad move, returning null..
        if (curr.getParent().getParent() == null) {
            return null;
        }

        while (curr.getParent() != null) {
            //System.out.println(curr.getX() + " " + curr.getY());
            if (curr.getParent().getParent().getParent() == null) {
                return curr.getParent();
            }
            curr = curr.getParent();
        }

        return null;
    }

    public Cell nextMove() {
        final Cell currentCell = CellService.getPlayerCell();
        int cX = currentCell.getX();
        int cY = currentCell.getY();

        // 1st case: place a bomb in the current cell
        Cell[][] temp = new Cell[Information.BOARD_N][Information.BOARD_M];
        for (int i = 0; i < Information.BOARD_N; i++) {
            for (int j = 0; j < Information.BOARD_M; j++) {
                temp[i][j] = Information.BOARD[i][j];
            }
        }

        currentCell.setBombTimeLeft(10);
        CellService.populateNeighbourCellsWithSafeTimeLeft(Arrays.asList(currentCell));
        Cell nextCell = findPath();
        if (nextCell != null) {
            placeBomb = true;
            return nextCell;
        } else {
            for (int i = 0; i < Information.BOARD_N; i++) {
                for (int j = 0; j < Information.BOARD_M; j++) {
                    Information.BOARD[i][j] = temp[i][j];
                }
            }

            nextCell = findPath();
            if (nextCell != null) {
                return nextCell;
            } else {
                return CellService.getPlayerCell();
            }
        }
    }

}
