package meshtrafficsimulator.models;

public class Spawn {
    private int positionRow;
    private int positionCol;
    private Directions direction;

    public Spawn(int positionRow, int positionCol, Directions direction) {
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.direction = direction;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public Directions getDirection() {
        return direction;
    }
}
