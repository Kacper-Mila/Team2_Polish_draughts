package draughts;

import java.sql.SQLOutput;

public class Pawn {

    //The Pawn class contains a method that validates the move (whether it is within the game rules) before it is performed.
    public boolean validateMove(int x, int y){
        boolean repeat = true;
        int[] move = {x, y};

        while(repeat){
            if (x < 0 || x >= board.length){
                System.out.println("Incorrect coordinates");
                continue;
            }
            if (y < 0 || y >= board[0].length){
                System.out.println("Incorrect coordinates");
                continue;
            }
            if (board[x][y] != 0){
                System.out.println("Position already taken");
                continue;
            }
//            if (move[0]) {
//
//            }
            repeat = false;
        }
        return true;
    }
}
