/**
 * Created by StancuFlorin on 11/1/2015.
 */
public class BoardService {

    public static void nextBoardState() {
        for (Cell cell : Information.almostSafeCells) {
            cell.nextState();
        }
    }

    public static void previousBoardState() {
        for (Cell cell : Information.almostSafeCells) {
            cell.previousState();
        }
    }

}
