package main.java.draughts;

import java.awt.*;

import static java.awt.Color.*;

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
    public int getBoardSize(){
        return fields.length;
    }
    /**
     * Print current board.
     * This method marks row as numbers and col as letters.
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
        final String ANSI_PAWN= "\u2659";
        final String ANSI_QUEEN= "\u265B";
        final String FORMAT = "%-3.40s"; //String format that keeps field width min 3 chars and max 40.
        // Its 40 because invisible formatting escape symbols are included in string length
        Pawn [][] board = this.fields;
        StringBuilder result = new StringBuilder();
        Color background;
        for(int row = 0; row < board.length + 1; row++){ // i -> row
            for(int col = 0; col < board[0].length + 1; col++){ // j -> col
                if(row > 0 && col > 0){
                    String pawn = "";
                    if(board[row - 1][col - 1] != null){
                        if(board[row - 1][col - 1].isCrowned()){
                            pawn = ANSI_QUEEN; //Crowned pawn symbol
                        }else{
                            pawn = ANSI_PAWN;//pawn symbol
                        }
                        if(board[row - 1][col - 1].getColor() == black) { //set pawns color on the board according to color set
                            pawn =  ANSI_BLACK  + " " + pawn + " "; //set pawn's color black
                        }else{
                            pawn =  ANSI_WHITE + " " + pawn + " "; //set pawn's color white
                        }
                    }
                    if(row % 2 == 0) { //even coordinatesRow
                        if (col % 2 == 0) {
                            result.append(ANSI_WHITE_BACKGROUND);
                            background = WHITE;
                        } else {
                            result.append(ANSI_GREY_BACKGROUND);
                            background =BLACK;
                        }
                    }else{ //odd coordinatesRow
                        if (col % 2 == 0) {
                            result.append(ANSI_GREY_BACKGROUND);
                            background = BLACK;
                        } else {
                            result.append(ANSI_WHITE_BACKGROUND);
                            background = WHITE;
                        }
                    }
                    if(pawn.equals("")){ //if field has no pawn then add circle with color of the background to keep
                        // fields alignment
                        if(background==black){
                            pawn = " "+ANSI_GREY+ ANSI_PAWN + " "; //empty field
                        }else{
                            pawn = " "+ANSI_WHITE + ANSI_PAWN + " "; //empty field
                        }
                    }
                    result.append(String.format(FORMAT,ANSI_BOLD+pawn)).append(ANSI_RESET);
                }else{
                    char colChar = (char) (64 + col); // col -> colChar
                    if(row == 0 && col == 0) result.append(String.format(FORMAT, "   "));//empty field in left up corner
                    if(row == 0 && col > 0) result.append(String.format(FORMAT, ANSII_BLACK_BACKGROUND+ANSI_BOLD+ " "+colChar+ANSI_BLACK+ANSI_PAWN+ANSI_RESET));// Letters coordinatesRow //col -> colChar
                    if(col == 0 && row > 0) result.append(String.format(FORMAT, row));// Numbers column
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

    public void movePawn(Pawn pawn, Coordinates position){
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

    public void createBoard(){
        // one side of the board
        int sideLength = this.getBoardSize();
        int numberOfPawns = sideLength * 2;

        for (int row = 0; row < sideLength; row++) {
            for (int col = 0; col < sideLength; col = col + 2) {
                if (row % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[row][col + 1] = new Pawn(new Coordinates(row, col+1), black);
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
        numberOfPawns = 2 * sideLength;
        for (int row = sideLength - 1; row > sideLength - 5; row--) {
            for (int col = 0; col < sideLength; col = col + 2) {
                if (row % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[row][col + 1] = new Pawn(new Coordinates(row, col+1), white);
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
}
