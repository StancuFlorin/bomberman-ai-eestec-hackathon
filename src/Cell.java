import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ionut on 10/31/2015.
 */
public class Cell {
    private List<Integer> players = new ArrayList<>();

    private boolean isWall;

    private int flameTimeLeft;

    private int bombTimeLeft;

    public List<Integer> getPlayers() {
        return players;
    }

    public void setPlayers(List<Integer> players) {
        this.players = players;
    }

    public boolean isWall() {
        return isWall;
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
}
