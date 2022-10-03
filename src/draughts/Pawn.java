package draughts;

import java.awt.*;

import static java.awt.Color.black;
import static java.awt.Color.white;

public class Pawn {

    private Coordinates position;
    private Color color;
    private boolean isCrowned;

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

    public void setCrowned(boolean crowned) {
        isCrowned = crowned;
    }

    //The Pawn class contains a method that validates the move (whether it is within the game rules) before it is performed.
    // missing other rules. Chapter 'Moves and captures' from https://en.wikipedia.org/wiki/International_draughts
    //sprawdza czy moge sie przesunac na podane pole
    //sprawdzanie bicia po przekatnej jest jako extra

    /**
     * @param board    game's board instance
     * @param position coordinates of pawn move
     * @return true if move is valid, otherwise false
     */
    public boolean validateMove(Board board, Coordinates position) {
        //czy wybrane pole jest puste i jest w zasiegu
        // jednego pola po przekatnej do przodu
        Pawn[][] fields = board.getFields();

        int startX = this.position.getX();
        int startY = this.position.getY();
        int goalX = position.getX();
        int goalY = position.getY();
        Color startColor = this.color;

        // if the goal field is empty check if move is diagonally by one square
        if ((fields[goalX][goalY]) == null) {
            if (startColor.equals(black)) {
                return ((goalX == startX + 1) && (goalY == startY - 1)) || ((goalX == startX + 1) && (goalY == startY + 1));
            } else if (startColor.equals(white)) {
                return ((goalX == startX - 1) && (goalY == startY - 1)) || ((goalX == startX - 1) && (goalY == startY + 1));
            }
        } else {
            return false;
        }
        return false;
    }

    public Pawn validateMoveWithCapture(Board board, Coordinates position) {
        //TODO: przeniesc metody validacji ruchu do klasy Board
        Pawn[][] fields = board.getFields();

        int startX = this.position.getX();

        int startY = this.position.getY();
        int goalX = position.getX();
        int goalY = position.getY();
        Pawn goalPawn = fields[goalX][goalY];

        // if goal field is empty
        if (goalPawn == null) {
            // if my color is black
            if (this.color.equals(black)) {
                // if my goal move is 2 fields diagonally away
                if ((goalX == startX - 2) && (goalY == startY - 2)) {
                    if (fields[startX - 1][startY - 1] != null) {
                        if (fields[startX - 1][startY - 1].color.equals(white)) {
                            return fields[startX - 1][startY - 1];
                        }
                    }
                } else if ((goalX == startX + 2) && (goalY == startY - 2)) {
                    if (fields[startX + 1][startY - 1] !=  null) {
                        if (fields[startX + 1][startY - 1].color.equals(white)) {
                            return fields[startX + 1][startY - 1];
                        }
                    }
                } else if ((goalX == startX - 2) && (goalY == startY + 2)) {
                    if (fields[startX - 1][startY + 1] != null) {
                        if (fields[startX - 1][startY + 1].color.equals(white)) {
                            return fields[startX - 1][startY + 1];
                        }
                    }
                } else if ((goalX == startX + 2) && (goalY == startY + 2)) {
                    if (fields[startX + 1][startY + 1] != null) {
                        if (fields[startX + 1][startY + 1].color.equals(white)) {
                            return fields[startX + 1][startY + 1];
                        }
                    }
                }
            } else if (this.color.equals(white)) {
                if ((goalX == startX - 2) && (goalY == startY - 2)) {
                    if (fields[startX - 1][startY - 1] != null) {
                        if (fields[startX - 1][startY - 1].color.equals(black)) {
                            return fields[startX - 1][startY - 1];
                        }
                    }
                } else if ((goalX == startX + 2) && (goalY == startY - 2)) {
                    if (fields[startX + 1][startY - 1] != null) {
                        if (fields[startX + 1][startY - 1].color.equals(black)) {
                            return fields[startX + 1][startY - 1];
                        }
                    }
                } else if ((goalX == startX - 2) && (goalY == startY + 2)) {
                    if (fields[startX - 1][startY + 1] != null){
                        if (fields[startX - 1][startY + 1].color.equals(black)) {
                            return fields[startX - 1][startY + 1];
                        }
                    }
                } else if ((goalX == startX + 2) && (goalY == startY + 2)) {
                    if (fields[startX + 1][startY + 1] != null) {
                        if (fields[startX + 1][startY + 1].color.equals(black)) {
                            return fields[startX + 1][startY + 1];
                        }
                    }
                }
            }
        } else {
             return null;
        }
        return null;
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