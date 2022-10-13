package main.java.draughts;

import java.awt.*;

public class Pawn {

    private final Color color;
    private Coordinates position;
    private boolean isCrowned;

    private int[][] fieldsPickedWhenCrowned; //array of counters for each field counting how many times this pawn was
    // moved on that fields when it was crowned

    public Pawn(Coordinates position, Color color) {
        this.color = color;
        this.position = position;
    }

    public int[][] getFieldsPickedWhenCrowned() {
        return fieldsPickedWhenCrowned;
    }

    public void setFieldsPickedWhenCrowned(int[][] fieldsPickedWhenCrowned) {
        this.fieldsPickedWhenCrowned = fieldsPickedWhenCrowned;
    }

    public Color getColor() {
        return color;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public boolean isCrowned() {
        return this.isCrowned;
    }

    public void setCrowned(Board board) {
        isCrowned = true;
        this.fieldsPickedWhenCrowned = new int[board.getBoardSize()][board.getBoardSize()];
    }

}