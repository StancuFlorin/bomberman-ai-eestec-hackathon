import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ionut on 10/31/2015.
 */
public class Cell {

    /**
     * PLAYER_ID-urile jucatorilor care se afla in celula curenta.
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
        return players.contains(Information.PLAYER_ID);
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

    public void setPlayers(byte data) {
        int myByte = (int) data;

        if((myByte & (1 << 0)) != 0) {
            players.add(1);
        }

        if((myByte & (1 << 1)) != 0) {
            players.add(2);
        }

        if((myByte & (1 << 2)) != 0) {
            players.add(4);
        }

        if((myByte & (1 << 3)) != 0) {
            players.add(8);
        }

        if((myByte & (1 << 4)) != 0) {
            players.add(16);
        }

        if((myByte & (1 << 5)) != 0) {
            players.add(32);
        }

        if((myByte & (1 << 6)) != 0) {
            players.add(64);
        }

        if((myByte & (1 << 7)) != 0) {
            players.add(128);
        }
    }

    public boolean isWall() {
        return isWall;
    }

    public void setIsWall(byte myByte) {
        int data = Math.abs(myByte);

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
