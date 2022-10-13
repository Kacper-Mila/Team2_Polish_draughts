package main.java.draughts;

import java.awt.*;

public class Pawn {

    private Coordinates position;
    private final Color color;
    private boolean isCrowned;

    private int [][] fieldsPickedWhenCrowned; //array of counters for each field counting how many times this pawn was
    // moved on that fields when it was crowned

    public int[][] getFieldsPickedWhenCrowned() {
        return fieldsPickedWhenCrowned;
    }

    public void setFieldsPickedWhenCrowned(int[][] fieldsPickedWhenCrowned) {
        this.fieldsPickedWhenCrowned = fieldsPickedWhenCrowned;
    }

    public Pawn(Coordinates position, Color color) {
        this.color = color;
        this.position = position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public void setCrowned(Board board) {
        isCrowned = true;
        this.fieldsPickedWhenCrowned = new int [board.getBoardSize()][board.getBoardSize()];
    }

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