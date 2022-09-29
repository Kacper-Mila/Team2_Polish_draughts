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
     *
     * @param board game's board instance
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
                if (((goalX == startX + 1) && (goalY == startY - 1)) || ((goalX == startX + 1) && (goalY == startY + 1))) {
                    return true;
                } else {
                    return false;
                }
            } else if (startColor.equals(white)) {
                if (((goalX == startX - 1) && (goalY == startY - 1)) || ((goalX == startX - 1) && (goalY == startY + 1))) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public Pawn validateMoveWithCapture(Board board, Coordinates position) {
        Pawn[][] fields = board.getFields();
        //todo: null point exception prawdopodobnie juz nie aktualne -> blad rozwiazany ale nalezy zweryfikowac
//        error reason:
//        Podaj współrzędne pionka, którym chcesz wykonać ruch, np. A1
//        a10
//        Podaj współrzędne pola, na które chcesz wykonać ruch, np. A1
//                b10
//        ERROR:
//        Exception in thread "main" java.lang.NullPointerException: Cannot read field "color" because "fields[goalX][goalY]" is null
//        at draughts.Pawn.validateMoveWithCapture(Pawn.java:75)
//        at draughts.Game.tryToMakeMove(Game.java:175)
//        at draughts.Game.checkStartingPosition(Game.java:152)
//        at draughts.Game.playRound(Game.java:51)
//        at draughts.Game.start(Game.java:31)
//        at draughts.Main.main(Main.java:8)
        int startX = this.position.getX();
        int startY = this.position.getY();
        int goalX = position.getX();
        int goalY = position.getY();
        Color startColor = this.color;

        //TODO:sprawdz czy wybrane pole jest o dwa pola oraz czy jest puste po przekatnej dalej oraz czy miedzy nimi jest
        // pionek przeciwnego koloru,jezeli tak to zwroc pionek do bicia, w przeciwnym razie zwroc null
        //hint rozubodowana validacja z ValidateMove()
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
