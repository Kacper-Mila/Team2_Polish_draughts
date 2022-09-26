package draughts;

import java.util.Scanner;

public class Board {
    private Pawn[][] fields;
    public Board(int n) {
        if (n >= 10 && n <= 20) {
            this.fields = new Pawn[n][n];
        }
        // one side of the board
        int numberOfPawns = 2 * n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j=j+2) {
                Pawn pawn = new Pawn();
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
        for (int i = n-1; i > n-5; i--) {
            for (int j = 0; j < n; j=j+2) {
                Pawn pawn = new Pawn();
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

    public Pawn[][] getFields() {
        return fields;
    }

    public void setFields(Pawn[][] fields) {
        this.fields = fields;
    }
    public static Scanner scanner = new Scanner(System.in);
    //There is a movePawn() method that moves pawns from a specified position to another field.
    public int[] movePawn(){
        System.out.println("Which pawn you want to move?");
        String whichPawn = scanner.nextLine().toLowerCase().trim();
        System.out.println("Where do you want to move it?");
        String where = scanner.nextLine().toLowerCase().trim();

        char charWhitchPawn = whichPawn.charAt(0);
        char charWhere = where.charAt(0);
        return new int[4];
    }
}
