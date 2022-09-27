package draughts;

import static java.awt.Color.black;
import static java.awt.Color.white;

public class Board {
    private Pawn[][] fields;
    private int whitePawnsCounter; // number of white pawns in the game at the moment
    private int blackPawnsCounter; // same as above but black one

    public Board(int n) {
        if (n >= 10 && n <= 20) {
            this.fields = new Pawn[n][n];
        }
        // one side of the board
        int numberOfPawns = 2 * n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j = j + 2) {
                Pawn pawn = new Pawn(new Coordinates(i, j), black);
                if (i % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[i][j + 1] = pawn;
                        numberOfPawns--;
                    }
                } else {
                    if (numberOfPawns > 0) {
                        this.fields[i][j] = pawn;
                        numberOfPawns--;
                    }
                }
            }
        }

        // other side of the board
        numberOfPawns = 2 * n;
        for (int i = n - 1; i > n - 5; i--) {
            for (int j = 0; j < n; j = j + 2) {
                Pawn pawn = new Pawn(new Coordinates(i, j), white);
                if (i % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[i][j + 1] = pawn;
                        numberOfPawns--;
                    }
                } else {
                    if (numberOfPawns > 0) {
                        this.fields[i][j] = pawn;
                        numberOfPawns--;
                    }
                }
            }
        }
    }

    /**
     * Print current board.
     * This method marks rows as numbers and columns as letters.
     * @return String representing board
     */
    @Override
    public String toString() {
        //TODO: zweryfikować poprawność działania na dalszym etapie prac!
        Pawn [][] board = this.fields;
        StringBuilder result = new StringBuilder();
        char col;
        int row;
        for(int i =0;i<board.length;i++){
            row = i+1;
            for(int j=0;j<board[0].length;j++){
                col = (char) (65 + j);
                result.append(row).append(col).append(" ");
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
        int x = pawn.getPosition().getX();
        int y = pawn.getPosition().getY();
        this.fields[x][y] = null;
    }

    public void movePawn(Pawn pawn, Coordinates position){
        //There is a movePawn() method that moves pawns from a specified position to another field.
        //This method is just changing pawns coordinates
        //removes pawn from startPosition and moves it to endPosition
        int startX = pawn.getPosition().getX();
        int startY = pawn.getPosition().getY();
        this.fields[startX][startY] = null;
        int goalX = position.getX();
        int goalY = position.getY();
        this.fields[goalX][goalY] = pawn;
    }

}
