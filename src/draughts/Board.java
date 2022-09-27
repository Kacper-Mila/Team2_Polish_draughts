package draughts;

import java.util.Scanner;

import static java.awt.Color.black;
import static java.awt.Color.white;

public class Board {
    private Pawn[][] fields;
    private int whitePawnsCounter;
    private int blackPawnsCounter;

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



    public static Scanner scanner = new Scanner(System.in);
    //There is a movePawn() method that moves pawns from a specified position to another field.
    //I think user interface method is Game.tryTomakeMove()
    //This method is just changing pawns coordinates
    public int[] movePawn(Coordinates startPosition, Coordinates endPosition){
        //removes pawn from startPosition and moves it to endPosition

//        System.out.println("Which pawn you want to move?");
//        String whichPawn = scanner.nextLine().toLowerCase().trim();
//        System.out.println("Where do you want to move it?");
//        String where = scanner.nextLine().toLowerCase().trim();
//
//        char charWhitchPawn = whichPawn.charAt(0);
//        char charWhere = where.charAt(0);
        return new int[4];
    }

    /**
     * Print current board.
     * This method marks rows as numbers and columns as letters.
     * @return String representing board
     */
    @Override
    public String toString() {
        return "Board{}";
    }

    /**
     * method that removes pawns from the specified position.
     */
    public void removePawn(Pawn pawn, Coordinates coordinates){
        //zmniejsza o 1 licznik pionkow w klasie board
        if(this.fields[coordinates.getX()][coordinates.getY()].equals(pawn)){
            fields[coordinates.getX()][coordinates.getY] = null;
        } else{
            System.out.println("this Pawn is not in that position");
        }
    }

    public Pawn[][] getFields() {
        return fields;
    }

    public void setFields(Pawn[][] fields) {
        this.fields = fields;
    }

    public void removePawn(Pawn pawn) {
        int x = pawn.getPosition().getX();
        int y = pawn.getPosition().getY();
        this.fields[x][y] = null;

    }

    public void movePawn(Pawn pawn, Coordinates position){
        int startX = pawn.getPosition().getX();
        int startY = pawn.getPosition().getY();
        this.fields[startX][startY] = null;
        int goalX = position.getX();
        int goalY = position.getY();
        this.fields[goalX][goalY] = pawn;
    }

}
