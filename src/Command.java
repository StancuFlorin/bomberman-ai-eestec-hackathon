/**
 * Created by StancuFlorin on 11/1/2015.
 */

public enum  Command {
    NONE,
    RIGHT,
    UP,
    LEFT,
    DOWN;

    /*
    * generates command based on current position on the boarde and the position found by the algorithm
    */
    public static Command getCommand(Cell currentCell, Cell futureCell) {

        if (futureCell.getX() > currentCell.getX()) { // UP
            return UP;
        }

        if (futureCell.getX() < currentCell.getX()) { // DOWN
            return DOWN;
        }

        if (futureCell.getY() > currentCell.getY()) { // RIGHT
            return RIGHT;
        }

        if (futureCell.getY() < currentCell.getY()) { // LEFT
            return LEFT;
        }

        return NONE;
    }
}
