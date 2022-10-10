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
     * This method marks rows as numbers and columns as letters.
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
        for(int rows = 0; rows < board.length + 1; rows++){ // i -> rows
            for(int columns = 0; columns < board[0].length + 1; columns++){ // j -> columns
                if(rows > 0 && columns > 0){
                    String pawn = "";
                    if(board[rows - 1][columns - 1] != null){
                        if(board[rows - 1][columns - 1].isCrowned()){
                            pawn = ANSI_QUEEN; //Crowned pawn symbol
                        }else{
                            pawn = ANSI_PAWN;//pawn symbol
                        }
                        if(board[rows - 1][columns - 1].getColor() == black) { //set pawns color on the board according to color set
                            pawn =  ANSI_BLACK  + " " + pawn + " "; //set pawn's color black
                        }else{
                            pawn =  ANSI_WHITE + " " + pawn + " "; //set pawn's color white
                        }
                    }
                    if(rows % 2 == 0) { //even row
                        if (columns % 2 == 0) {
                            result.append(ANSI_WHITE_BACKGROUND);
                            background = WHITE;
                        } else {
                            result.append(ANSI_GREY_BACKGROUND);
                            background =BLACK;
                        }
                    }else{ //odd row
                        if (columns % 2 == 0) {
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
                    char colChar = (char) (64 + columns); // columns -> colChar
                    if(rows == 0 && columns == 0) result.append(String.format(FORMAT, "   "));//empty field in left up corner
                    if(rows == 0 && columns > 0) result.append(String.format(FORMAT, ANSII_BLACK_BACKGROUND+ANSI_BOLD+ " "+colChar+ANSI_BLACK+ANSI_PAWN+ANSI_RESET));// Letters row //columns -> colChar
                    if(columns == 0 && rows > 0) result.append(String.format(FORMAT, rows));// Numbers column
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
        int row = pawn.getPosition().getRow();
        int col = pawn.getPosition().getCol();
        this.fields[row][col] = null;
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

        for (int rows = 0; rows < sideLength; rows++) {
            for (int columns = 0; columns < sideLength; columns = columns + 2) {
                if (rows % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[rows][columns + 1] = new Pawn(new Coordinates(rows, columns+1), black);
                        numberOfPawns--;
                    }
                } else {
                    if (numberOfPawns > 0) {
                        this.fields[rows][columns] = new Pawn(new Coordinates(rows, columns), black);
                        numberOfPawns--;
                    }
                }
            }
        }

        // other side of the board
        numberOfPawns = 2 * sideLength;
        for (int rows = sideLength - 1; rows > sideLength - 5; rows--) {
            for (int columns = 0; columns < sideLength; columns = columns + 2) {
                if (rows % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[rows][columns + 1] = new Pawn(new Coordinates(rows, columns+1), white);
                        numberOfPawns--;
                    }
                } else {
                    if (numberOfPawns > 0) {
                        this.fields[rows][columns] = new Pawn(new Coordinates(rows, columns), white);
                        numberOfPawns--;
                    }
                }
            }
        }
    }
}
