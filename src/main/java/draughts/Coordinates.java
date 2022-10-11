package main.java.draughts;

public class Coordinates {
    //Use it as format of passing coordinates data between methods/classes
    private int coordinatesRow; // x -> coordinatesRow
    private int coordinatesCol; // y -> coordinatesCol

    public Coordinates(int coordinatesRow, int coordinatesCol){
        this.coordinatesRow = coordinatesRow;
        this.coordinatesCol = coordinatesCol;
    }

    public int getRow() {
        return coordinatesRow;
    } // getX -> getRow

    public int getCol() {
        return coordinatesCol;
    } // getY -> getCol
}
