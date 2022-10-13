package main.java.draughts;

public class Coordinates {
    private final int coordinatesRow; // x -> coordinatesRow
    private final int coordinatesCol; // y -> coordinatesCol

    public Coordinates(int coordinatesRow, int coordinatesCol){
        this.coordinatesRow = coordinatesRow;
        this.coordinatesCol = coordinatesCol;
    }

    public int getRow() {
        return coordinatesRow;
    }

    public int getCol() {
        return coordinatesCol;
    }
}
