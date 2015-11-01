/**
 * Created by StancuFlorin on 11/1/2015.
 */

public class DebugService {

    public static void printBoard() {

        for (int i = 0; i < Information.BOARD_N; i++) {
            String line = "";
            for (int j = 0; j < Information.BOARD_M; j++) {
                Cell cell = Information.BOARD[i][j];

                boolean isSomething = false;
                if (cell.isWall()) {
                    line += " w";
                } else if (cell.getBombTimeLeft() != 0) {
                    line += " b";
                } else if (cell.isMyLocation()) {
                    line += " x";
                } else if (cell.getFlameTimeLeft() != 0) {
                    line += " +";
                } else if (!isSomething) {
                    line += "  ";
                }

            }
            System.out.println(line);
        }

    }

    public static void printPlayerPosition() {
        System.out.println(Information.PLAYER_I + " - " + Information.PLAYER_J);
    }

}
