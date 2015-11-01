import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by adinu on 11/1/15.
 */
public class PathFinder {

    private static PathFinder instance = null;

    private Cell[][] board;

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

    public void prepareSearch(Cell[][] board, int sX, int sY) {
        this.board = board;
        if (openedCells != null) {
            while (!openedCells.isEmpty()) {
                openedCells.remove();
            }
        }
        openedCells = new PriorityQueue<>();
        openedCells.offer(board[sX][sY]);
        last = board[sX][sY];
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
                   board[currX][currY].getDangerLevel()  +
                   front.getPrevSteps() + 1;
        int hNew = --hCurr;
        int fNew = gNew + hNew;

        if (fNew < board[currX][currY].getTotalCost()) {
            int prevG = board[currX][currY].getArrivalCost();
            board[currX][currY].setTotalCost(fNew);
            board[currX][currY].setHeuristicCost(hNew);
            board[currX][currY].setArrivalCost(gNew);
            board[currX][currY].setParent(front);
            board[currX][currY].setPrevSteps(front.getPrevSteps() + 1);

            //System.out.println("updated cell nowww: " + board[currX][currY].getX() +
            //        " " + board[currX][currY].getY());

            if (fNew < minTotalCost && board[currX][currY].getPrevSteps() > 2) {
                minTotalCost = fNew;
                last = board[currX][currY];
            }

            if (prevG == 0) {
                openedCells.offer(board[currX][currY]);
            }
        }
    }

    public Cell[] find() {
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
            if (!closedCells.contains(board[currX][prevY])) {
                // check the cell again for obstacles
                if (board[currX][prevY].isFree()) {
                    System.out.println("checked for availability " + currX + ", " + prevY);
                    updateParams(front, currX, currY);
                }
            }

            if (!closedCells.contains(board[currX][nextY])) {
                if (board[currX][nextY].isFree()) {
                    System.out.println("checked for availability " + currX + ", " + nextY);
                    updateParams(front, currX, nextY);
                }
            }

            if (!closedCells.contains(board[prevX][currY])) {
                if (board[prevX][currY].isFree()) {
                    System.out.println("checked for availability " + prevX + ", " + currY);
                    updateParams(front, prevX, currY);
                }
            }

            if (!closedCells.contains(board[nextX][currY])) {
                if (board[nextX][currY].isFree()) {
                    System.out.println("checked for availability " + nextX + ", " + currY);
                    updateParams(front, nextX, currY);
                }
            }

            nbOfMoves--;
        }

        System.out.println("last at the end: " + last.getX() + " " + last.getY());

        return buildPath(last);
    }

    public Cell[] buildPath(Cell lastCell) {
        Cell curr = lastCell.getParent();

        Cell[] path = new Cell[2];
        while (curr.getParent() != null) {
            //System.out.println(curr.getX() + " " + curr.getY());
            if (curr.getParent().getParent().getParent() == null) {
                path[1] = curr; path[0] = curr.getParent();
                break;
            }
            curr = curr.getParent();
        }

        return path;
    }

}
