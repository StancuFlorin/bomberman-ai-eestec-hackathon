import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ionut on 10/31/2015.
 */
public class Cell {

    /**
     * ID-urile jucatorilor care se afla in celula curenta.
     */
    private List<Integer> players = new ArrayList<>();

    private boolean isWall;

    /**
     * Cat timp mai e celula in flacari.
     */
    private int flameTimeLeft;

    /**
     * Cat mai are bomba pana explodeaza.
     */
    private int bombTimeLeft;

    /**
     * Pozitia in board a celulei.
     */
    private int x;
    private int y;

    /**
     * Cat mai e pana cand un vecin explodeaza si ma afecteza si pe mine.
     */

    private int safeTimeLeft;

    /**
     * Daca in celula curenta nu se afla o bomba / flacari / zid.
     * Daca nu exista niciun player in celula curenta.
     * @return
     */

    public boolean isFree() {
        return !isWall && (flameTimeLeft == 0) && (bombTimeLeft == 0) && (players.isEmpty());
    }

    public Cell(byte[] data, int x, int y) {
        setPlayers(data[0]);
        setIsWall(data[1]);

        this.flameTimeLeft = (int)data[2];
        this.bombTimeLeft = (int)data[3];

        this.x = x;
        this.y = y;
    }

    public boolean isMyLocation() {
        for(Integer player : players) {
            if(player == Information.ID) {
                return true;
            }
        }

        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<Integer> getPlayers() {
        return players;
    }

    public void setPlayers(List<Integer> players) {
        this.players = players;
    }

    public void setPlayers(byte myByte) {
        final int MASK_P0 = 1;
        final int MASK_P1 = 2;
        final int MASK_P2 = 4;
        final int MASK_P3 = 8;
        final int MASK_P4 = 16;
        final int MASK_P5 = 32;
        final int MASK_P6 = 64;
        final int MASK_P7 = 128;

        int data = (int)myByte;

        if((data & MASK_P0) == MASK_P0) {
            players.add(0);
        }

        if((data & MASK_P1) == MASK_P1) {
            players.add(1);
        }

        if((data & MASK_P2) == MASK_P2) {
            players.add(2);
        }

        if((data & MASK_P3) == MASK_P3) {
            players.add(3);
        }

        if((data & MASK_P4) == MASK_P4) {
            players.add(4);
        }

        if((data & MASK_P5) == MASK_P5) {
            players.add(5);
        }

        if((data & MASK_P6) == MASK_P6) {
            players.add(6);
        }

        if((data & MASK_P7) == MASK_P7) {
            players.add(7);
        }
    }

    public boolean isWall() {
        return isWall;
    }

    public void setIsWall(byte myByte) {
        int data = (int) myByte;

        if((data & 128) == 128) {
            isWall = true;
        }
    }

    public void setIsWall(boolean isWall) {
        this.isWall = isWall;
    }

    public int getFlameTimeLeft() {
        return flameTimeLeft;
    }

    public void setFlameTimeLeft(int flameTimeLeft) {
        this.flameTimeLeft = flameTimeLeft;
    }

    public int getBombTimeLeft() {
        return bombTimeLeft;
    }

    public void setBombTimeLeft(int bombTimeLeft) {
        this.bombTimeLeft = bombTimeLeft;
    }

    public int getSafeTimeLeft() {
        return safeTimeLeft;
    }

    public void setSafeTimeLeft(int safeTimeLeft) {
        this.safeTimeLeft = Math.min(this.safeTimeLeft, safeTimeLeft);
    }
}
