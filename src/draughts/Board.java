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
        final String ANSI_GREY_BACKGROUND = "\u001B[47m";
        final String ANSI_WHITE_BACKGROUND = "\u001B[107m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_WHITE = "\u001B[97m";
        final String ANSI_BOLD = "\u001B[1m";
        Pawn [][] board = this.fields;
        StringBuilder result = new StringBuilder();
        for(int i =0;i<board.length+1;i++){
            for(int j=0;j<board[0].length+1;j++){
                if(i>0&&j>0){
                    String pawn = " ";
                    if(board[i-1][j-1]!=null){
                        if(board[i-1][j-1].isCrowned()){
                            pawn = "X";
                        }else{
                            pawn = "O";
                        }
                        if(board[i-1][j-1].getColor()==black) {
                            pawn = ANSI_BOLD + ANSI_WHITE +" "+ pawn+" ";
                        }else{
                            pawn = ANSI_BOLD + ANSI_BLACK  +" "+ pawn + " ";
                        }
                    }
                    if(i%2==0) {
                        if (j % 2 == 0) {
                            result.append(ANSI_WHITE_BACKGROUND);
                        } else {
                            result.append(ANSI_GREY_BACKGROUND);
                        }
                    }else{
                        if (j % 2 == 0) {
                            result.append(ANSI_GREY_BACKGROUND);
                        } else {
                            result.append(ANSI_WHITE_BACKGROUND);
                        }
                    }
                    result.append(String.format("%3.40s",pawn)).append(ANSI_RESET);
                }else{
                    char col = (char) (64 + j);
                    if(i==0&&j==0) result.append("   ");
                    if(i==0&&j>0) result.append(String.format("%-3.3s"," " + col));
                    if(j==0&&i>0) result.append(String.format("%-3.3s",i));
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
