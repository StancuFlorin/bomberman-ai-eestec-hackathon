/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class DebugService {

    private static final boolean DEBUG = true;

    public static void printInformation() {
        if (!DEBUG) {
            return;
        }

        System.out.println("CURRENT_MOVE = " + Information.CURRENT_MOVE);
        System.out.println("AGGRESSIVE_MODE = " + Information.AGGRESSIVE_MODE);
        System.out.println("MAX_MOVES = " + Information.MAX_MOVES);
        System.out.println("BOARD_N = " + Information.BOARD_N);
        System.out.println("BOARD_M = " + Information.BOARD_M);
    }

    public static void printBoard() {
        if (!DEBUG) {
            return;
        }

        for (int i = 0; i < Information.BOARD_N; i++) {
            String line = "";
            for (int j = 0; j < Information.BOARD_M; j++) {
                Cell cell = Information.BOARD[i][j];

                if (cell.isWall()) {
                    line += " w";
                } else if (cell.getBombTimeLeft() != 0) {
                    line += " b";
                } else if (cell.isMyLocation()) {
                    line += " x";
                } else if (cell.getFlameTimeLeft() != 0) {
                    line += " +";
                } else {
                    line += "  ";
                }

            }
            System.out.println(line);
        }

    }

    public static void printPlayerPosition() {
        if (!DEBUG) {
            return;
        }

        System.out.println(Information.PLAYER_I + " - " + Information.PLAYER_J);
    }

}
