package main.java.draughts;

import java.awt.*;

public class Pawn {

    private Coordinates position;
    private Color color;
    private boolean isCrowned;

    public int[][] getFieldsPickedWhenCrowned() {
        return fieldsPickedWhenCrowned;
    }

    public void setFieldsPickedWhenCrowned(int[][] fieldsPickedWhenCrowned) {
        this.fieldsPickedWhenCrowned = fieldsPickedWhenCrowned;
    }

    private int [][] fieldsPickedWhenCrowned;

    public Pawn(Coordinates position, Color color) {
        this.color = color;
        this.position = position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }


    public void setColor(Color color) {
        this.color = color;
    }

    public void setCrowned(Board board) {
        isCrowned = true;
        this.fieldsPickedWhenCrowned = new int [board.getBoardSize()][board.getBoardSize()];
    }

    //The Pawn class contains a method that validates the move (whether it is within the game rules) before it is performed.
    // missing other rules. Chapter 'Moves and captures' from https://en.wikipedia.org/wiki/International_draughts
    //sprawdza czy moge sie przesunac na podane pole
    //sprawdzanie bicia po przekatnej jest jako extra


    public Color getColor() {
        return color;
    }

    public Coordinates getPosition() {
        return position;
    }

    public boolean isCrowned() {
        return this.isCrowned;
    }

}