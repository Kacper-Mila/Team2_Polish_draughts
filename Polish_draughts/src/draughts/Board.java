package draughts;

import java.util.Scanner;

public class Board {
    public static Scanner scanner = new Scanner(System.in);
    //There is a movePawn() method that moves pawns from a specified position to another field.
    public int[] movePawn(){
        System.out.println("Which pawn you want to move?");
        String whichPawn = scanner.nextLine().toLowerCase().trim();
        System.out.println("Where do you want to move it?");
        String where = scanner.nextLine().toLowerCase().trim();

        char charWhitchPawn = whichPawn.charAt(0);
        char charWhere = where.charAt(0);
    }
}
