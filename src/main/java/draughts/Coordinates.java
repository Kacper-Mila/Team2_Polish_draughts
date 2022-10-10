package main.java.draughts;

public class Coordinates {
    //Use it as format of passing coordinates data between methods/classes
    private int row; // x -> row
    private int col; // y -> col

    public Coordinates(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    } // getX -> getRow

    public int getCol() {
        return col;
    } // getY -> getCol
}
