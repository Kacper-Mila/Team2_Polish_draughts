package main.java.draughts;

import java.awt.*;

import static java.awt.Color.*;
import static java.lang.Math.abs;

public class Board {
    private Pawn[][] fields;
    private int whitePawnsCounter; // number of white pawns in the game at the moment
    private int blackPawnsCounter; // same as above but black one

    public int getWhitePawnsCounter() {
        return whitePawnsCounter;
    }

    public int getBlackPawnsCounter() {
        return blackPawnsCounter;
    }

    public Board(int sideLength) { // n -> sideLength
        if (sideLength >= 10 && sideLength <= 20) {
            this.fields = new Pawn[sideLength][sideLength];
            whitePawnsCounter = 2 * sideLength;
            blackPawnsCounter = 2 * sideLength;
        }
    }

    public int getBoardSize() {
        return fields.length;
    }

    /**
     * Print current board.
     * This method marks row as numbers and col as letters.
     *
     * @return String representing board
     */
    @Override
    public String toString() {
        final String ANSI_GREY_BACKGROUND = "\u001B[47m";
        final String ANSI_WHITE_BACKGROUND = "\u001B[107m";
        final String ANSII_BLACK_BACKGROUND = "\u001B[40m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_RESET = "\u001B[0m"; //resets all formatting
        final String ANSI_WHITE = "\u001B[97m";
        final String ANSI_GREY = "\u001B[37m";
        final String ANSI_BOLD = "\u001B[1m";
        final String ANSI_PAWN = "\u2659";
        final String ANSI_QUEEN = "\u265B";
        final String FORMAT = "%-3.40s"; //String format that keeps field width min 3 chars and max 40.
        // Its 40 because invisible formatting escape symbols are included in string length
        Pawn[][] board = this.fields;
        StringBuilder result = new StringBuilder();
        Color background;
        for (int row = 0; row < board.length + 1; row++) { // i -> row
            for (int col = 0; col < board[0].length + 1; col++) { // j -> col
                if (row > 0 && col > 0) {
                    String pawn = "";
                    if (board[row - 1][col - 1] != null) {
                        if (board[row - 1][col - 1].isCrowned()) {
                            pawn = ANSI_QUEEN; //Crowned pawn symbol
                        } else {
                            pawn = ANSI_PAWN;//pawn symbol
                        }
                        if (board[row - 1][col - 1].getColor() == black) { //set pawns color on the board according to color set
                            pawn = ANSI_BLACK + " " + pawn + " "; //set pawn's color black
                        } else {
                            pawn = ANSI_WHITE + " " + pawn + " "; //set pawn's color white
                        }
                    }
                    if (row % 2 == 0) { //even coordinatesRow
                        if (col % 2 == 0) {
                            result.append(ANSI_WHITE_BACKGROUND);
                            background = WHITE;
                        } else {
                            result.append(ANSI_GREY_BACKGROUND);
                            background = BLACK;
                        }
                    } else { //odd coordinatesRow
                        if (col % 2 == 0) {
                            result.append(ANSI_GREY_BACKGROUND);
                            background = BLACK;
                        } else {
                            result.append(ANSI_WHITE_BACKGROUND);
                            background = WHITE;
                        }
                    }
                    if (pawn.equals("")) { //if field has no pawn then add circle with color of the background to keep
                        // fields alignment
                        if (background == black) {
                            pawn = " " + ANSI_GREY + ANSI_PAWN + " "; //empty field
                        } else {
                            pawn = " " + ANSI_WHITE + ANSI_PAWN + " "; //empty field
                        }
                    }
                    result.append(String.format(FORMAT, ANSI_BOLD + pawn)).append(ANSI_RESET);
                } else {
                    char colChar = (char) (64 + col); // col -> colChar
                    if (row == 0 && col == 0)
                        result.append(String.format(FORMAT, "   "));//empty field in left up corner
                    if (row == 0 && col > 0)
                        result.append(String.format(FORMAT, ANSII_BLACK_BACKGROUND + ANSI_BOLD + " " + colChar + ANSI_BLACK + ANSI_PAWN + ANSI_RESET));// Letters coordinatesRow //col -> colChar
                    if (col == 0 && row > 0) result.append(String.format(FORMAT, row));// Numbers column
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    public Pawn[][] getFields() {
        return fields;
    }

    public void setFields(Pawn[][] fields) {
        this.fields = fields;
    }

    public void removePawn(Pawn pawn) {
        //zmniejsza o 1 licznik pionkow w klasie board, zgodnie z kolorem jaki zawiera obiekt pawn
        int coordinatesRow = pawn.getPosition().getRow();
        int coordinatesCol = pawn.getPosition().getCol();
        this.fields[coordinatesRow][coordinatesCol] = null;
        if (pawn.getColor().equals(white)) {
            whitePawnsCounter--;
        } else blackPawnsCounter--;
    }

    public void movePawn(Pawn pawn, Coordinates position) {
        //There is a movePawn() method that moves pawns from a specified position to another field.
        //This method is just changing pawns coordinates
        //removes pawn from startPosition and moves it to endPosition
        int startRow = pawn.getPosition().getRow(); // startX -> startRow
        int startCol = pawn.getPosition().getCol();// startY -> startCol
        this.fields[startRow][startCol] = null;
        int goalRow = position.getRow(); // goalX -> goalRow
        int goalCol = position.getCol(); // goalY -> goalCol
        pawn.setPosition(position);
        this.fields[goalRow][goalCol] = pawn;
    }

    public void createBoard() {
        // one side of the board
        int sideLength = this.getBoardSize();
        int numberOfPawns = blackPawnsCounter;

        for (int row = 0; row < sideLength; row++) {
            for (int col = 0; col < sideLength; col = col + 2) {
                if (row % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[row][col + 1] = new Pawn(new Coordinates(row, col + 1), black);
                        numberOfPawns--;
                    }
                } else {
                    if (numberOfPawns > 0) {
                        this.fields[row][col] = new Pawn(new Coordinates(row, col), black);
                        numberOfPawns--;
                    }
                }
            }
        }

        // other side of the board
        numberOfPawns = whitePawnsCounter;
        for (int row = sideLength - 1; row > sideLength - 5; row--) {
            for (int col = 0; col < sideLength; col = col + 2) {
                if (row % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[row][col + 1] = new Pawn(new Coordinates(row, col + 1), white);
                        numberOfPawns--;
                    }
                } else {
                    if (numberOfPawns > 0) {
                        this.fields[row][col] = new Pawn(new Coordinates(row, col), white);
                        numberOfPawns--;
                    }
                }
            }
        }
    }

    /**
     * @param pawn pawn object
     * @param position coordinates of pawn move
     * @return true if move is valid, otherwise false
     */
    public boolean validateMove(Pawn pawn, Coordinates position) {
        //czy wybrane pole jest puste i jest w zasiegu
        // jednego pola po przekatnej do przodu
        if (pawn == null) {
            return false;
        }

        int startX = pawn.getPosition().getRow();
        int startY = pawn.getPosition().getCol();
        int goalX = position.getRow();
        int goalY = position.getCol();
        Color startColor = pawn.getColor();

        if (pawn.isCrowned()) {
            return validateQueenMove(pawn, position);
        } else {
            // if the goal field is empty check if move is diagonally by one square
            if ((this.fields[goalX][goalY]) == null) {
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
    }

    /**
     * Check if Queen can move on the given field (coordinates). Can move if the field is empty, and it is diagonally.
     * @param pawn pawn object that is crowned (queen)
     * @param position coordinates of target pawn move
     * @return true if move is valid, otherwise false
     */
    public boolean validateQueenMove(Pawn pawn, Coordinates position) {
        int startX = pawn.getPosition().getRow();
        int startY = pawn.getPosition().getCol();
        int goalX = position.getRow();
        int goalY = position.getCol();

        //check if the field is empty
        if (this.fields[goalX][goalY] != null) return false;
        //check if the move is diagonally
        if (!(abs(goalX - startX) == abs(goalY - startY))) return false;
        int col = 0;
        for (int row = (goalX - startX) / (abs(goalX - startX)); abs(row) < abs(goalX - startX); row += (goalX - startX) / (abs(goalX - startX))) {
            col += (goalY - startY) / (abs(goalY - startY));
            if (getFields()[startX + row][startY + col] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if Queen can move on the given field (coordinates). Can move if the field is empty, and it is diagonally.
     * Also chceck if on the way there is a
     * @param pawn pawn object that is crowned (queen)
     * @param position coordinates of target pawn move
     * @return true if move is valid, otherwise false
     */
    public Pawn validateQueenMoveWithCapture(Pawn pawn, Coordinates position) {
        int startX = pawn.getPosition().getRow();
        int startY = pawn.getPosition().getCol();
        int goalX = position.getRow();
        int goalY = position.getCol();
        Color pawnColor = pawn.getColor();

        //check if the field is empty
        if (this.fields[goalX][goalY] != null) return null;
        //check if the move is diagonally
        if (!(abs(goalX - startX) == abs(goalY - startY))) return null;
        int col = 0;
        for (int row = (goalX - startX) / (abs(goalX - startX)); abs(row) < abs(goalX - startX); row += (goalX - startX) / (abs(goalX - startX))) {
            col += (goalY - startY) / (abs(goalY - startY));
            if (getFields()[startX + row][startY + col] != null) {
                if (getFields()[startX + row][startY + col].getColor() == pawnColor) {
                    // there is pawn with the same color as the queen on the way of the queen's move. It can't move so far.
                    return null;
                } else {
                    Coordinates tmpFieldBehindThePawn = new Coordinates(
                            startX + row + (goalX - startX) / (abs(goalX - startX)),
                            startY + col + (goalY - startY) / (abs(goalY - startY)));
                    if (getFields()[tmpFieldBehindThePawn.getRow()][tmpFieldBehindThePawn.getCol()] == null
                            && position.getRow() == tmpFieldBehindThePawn.getRow()
                            && position.getCol() == tmpFieldBehindThePawn.getCol()
                    ) {
                        //the field behind the pawn ot the opposite color is empty so queen can capture this pawn.
                        //It is also the field that queen want to move.
                        return getFields()[startX + row][startY + col];
                    } else {
                        //the field behind the pawn is occupied so queen cant capture it, or the field behind the pawn is
                        //not the field queen wants to move.
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public Pawn checkForPossibleMoves(Pawn pawn, Coordinates position, Color color) {
        int startRow = pawn.getPosition().getRow();
        int startCol = pawn.getPosition().getCol();
        int goalRow = position.getRow();
        int goalCol = position.getCol();

        if ((goalRow == startRow - 2) && (goalCol == startCol - 2)) {
            if (this.fields[startRow - 1][startCol - 1] != null) {
                if (this.fields[startRow - 1][startCol - 1].getColor().equals(color)) {
                    return this.fields[startRow - 1][startCol - 1];
                }
            }
        } else if ((goalRow == startRow + 2) && (goalCol == startCol - 2)) {
            if (this.fields[startRow + 1][startCol - 1] != null) {
                if (this.fields[startRow + 1][startCol - 1].getColor().equals(color)) {
                    return this.fields[startRow + 1][startCol - 1];
                }
            }
        } else if ((goalRow == startRow - 2) && (goalCol == startCol + 2)) {
            if (this.fields[startRow - 1][startCol + 1] != null) {
                if (this.fields[startRow - 1][startCol + 1].getColor().equals(color)) {
                    return this.fields[startRow - 1][startCol + 1];
                }
            }
        } else if ((goalRow == startRow + 2) && (goalCol == startCol + 2)) {
            if (this.fields[startRow + 1][startCol + 1] != null) {
                if (this.fields[startRow + 1][startCol + 1].getColor().equals(color)) {
                    return this.fields[startRow + 1][startCol + 1];
                }
            }
        }
        return null;
    }

    public Pawn validateMoveWithCapture(Pawn pawn, Coordinates position) {
        if (pawn == null) {
            return null;
        }
        if (pawn.isCrowned()) {
            return validateQueenMoveWithCapture(pawn, position);
        } else {
            // if goal field is empty
            if (this.fields[position.getRow()][position.getCol()] == null) {
                // if my color is black
                if (pawn.getColor().equals(black)) {
                    // if my goal move is 2 fields diagonally away
                    return checkForPossibleMoves(pawn, position, Color.white);
                } else if (pawn.getColor().equals(white)) {
                    return checkForPossibleMoves(pawn, position, Color.black);
                }
            } else {
                return null;
            }
            return null;
        }
    }

    public boolean validateCrowning(Pawn pawn, Coordinates position) {
        //Crown pawn if allowed (is on the correct edge of the board and is not forced to capture an enemy pawn
        if (!pawn.isCrowned()) {
            //check if pawn is on the edge of the board
            if (pawn.getColor() == white && position.getRow() == 0 ||
                    pawn.getColor() == black && position.getRow() == this.getBoardSize() - 1) {
                //check if there is a possible capture not allowing to upgrade a pawn to a queen
                int horizontalMoveDirection = position.getRow() - pawn.getPosition().getRow(); //get direction in which
                // should be checked field for blocking capture
                if (pawn.getPosition().getRow() + 2 * horizontalMoveDirection > 0 && horizontalMoveDirection < this.getBoardSize()) {
                    //check if there is pawn that should be capture what disallow to crown a pawn
                    Pawn possiblePawnToCapture = this.getFields()
                            [pawn.getPosition().getRow() + 2 * horizontalMoveDirection]
                            [pawn.getPosition().getCol()];

                    return possiblePawnToCapture == null;
                    //there is no possible capture -> return true
                    //there is optional capture not allowing to crown a pawn. -> return false
                }

            }
        }
        //the Pawn can not be crowned
        return false;
    }

    public Pawn getPawnBlockingCrowning(Pawn pawn, Coordinates position) {
        int horizontalMoveDirection = position.getRow() - pawn.getPosition().getRow(); //get direction in which
        // should be checked field for blocking capture
        if (pawn.getPosition().getRow() + 2 * horizontalMoveDirection > 0 && horizontalMoveDirection < this.getBoardSize()) {
            Pawn possiblePawnToCapture = this.getFields()
                    [pawn.getPosition().getRow() + 2 * horizontalMoveDirection]
                    [pawn.getPosition().getCol()];
            //there is optional capture not allowing to crown a pawn
            return possiblePawnToCapture;
        } else {
            //there cant be pawn to capture because there is no field to check (corner scenarios)
            return null;
        }
    }
}
