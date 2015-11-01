import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by adinu on 11/1/15.
 */
public class PathFinder {

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

    public void prepareSearch() {
        if (openedCells != null) {
            while (!openedCells.isEmpty()) {
                openedCells.remove();
            }
        }
        openedCells = new PriorityQueue<>();
        openedCells.offer(Information.BOARD[Information.PLAYER_I][Information.PLAYER_J]);
        last = Information.BOARD[Information.PLAYER_I][Information.PLAYER_J];
        if (closedCells != null) {
            closedCells.clear();
        }
        closedCells = new HashSet<>();
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
                openedCells.offer(Information.BOARD[currX][currY]);
            }
        }
    }

    public Cell findPath() {
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
                    updateParams(front, currX, currY);
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

            nbOfMoves--;
        }

        //System.out.println("last at the end: " + last.getX() + " " + last.getY());

        return nextCell();
    }

    public Cell nextCell() {
        Cell curr = last.getParent();

        if (curr.getParent() == null) {
            return CellService.getPlayerCell();
        }

        if (curr.getParent().getParent() == null) {
            return curr;
        }

        while (curr.getParent() != null) {
            //System.out.println(curr.getX() + " " + curr.getY());
            if (curr.getParent().getParent().getParent() == null) {
                return curr.getParent();
            }
            curr = curr.getParent();
        }

        return CellService.getPlayerCell();
    }

}
