package draughts;

import java.awt.*;

import static java.awt.Color.black;
import static java.awt.Color.white;

public class Pawn {

    private Coordinates position;
    private Color color;
    private boolean isCrowned;

    public Pawn(Coordinates position, Color color ){
        this.color = color;
        this.position = position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCrowned(boolean crowned) {
        isCrowned = crowned;
    }

    //The Pawn class contains a method that validates the move (whether it is within the game rules) before it is performed.
    // missing other rules. Chapter 'Moves and captures' from https://en.wikipedia.org/wiki/International_draughts
    //sprawdza czy moge sie przesunac na podane pole
    //sprawdzanie bicia po przekatnej jest jako extra
    public boolean validateMove(Board board, Coordinates position){
        Pawn[][] fields = board.getFields();

        int startX = this.position.getX();
        int startY = this.position.getY();
        int goalX = position.getX();
        int goalY = position.getY();
        Color startColor = fields[startX][startY].color;
        Color goalColor = fields[goalX][goalY].color;


        // if the goal field is empty check if move is diagonally by one square
        if ((fields[goalX][goalY]) == null) {
            if (startColor.equals(white)) {
                if      (((goalX == startX + 1) && (goalY == startY - 1)) ||
                        ((goalX == startX + 1) && (goalY == startY + 1))) {
                    return true;
                } else {
                    return false;
                }
            } else if (startColor.equals(black)) {
                if     (((goalX == startX - 1) && (goalY == startY - 1)) ||
                        ((goalX == startX - 1) && (goalY == startY + 1))){
                    return true;
                } else {
                    return false;
                }
            }
        } else { // if pawn color is different from color of diagonal neighbour, you can capture
            if (goalColor != startColor) {
                if     (((goalX == startX - 2) && (goalY == startY - 2)) ||
                        ((goalX == startX + 2) && (goalY == startY - 2)) ||
                        ((goalX == startX - 2) && (goalY == startY + 2)) ||
                        ((goalX == startX + 2) && (goalY == startY + 2))) {

                    return true;
                }
            }
        }
        return false;
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

    public void setCrowned() {
        this.isCrowned = true;
    }

}
