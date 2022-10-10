package main.java.draughts;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(getBoardSize());
        System.out.println(game.getBoard());
        game.start();
    }

    private static int getBoardSize() {
        Scanner readBoardSize = new Scanner(System.in);
        String boardSize;
        String regex = "^(1[0-9]|20)$";
        do {
            System.out.println("Enter the board size, it must be larger or equal 10 and smaller or equal 20, also even.");
            boardSize = readBoardSize.nextLine();
        } while (!boardSize.matches(regex) || Integer.parseInt(boardSize) % 2 != 0);
        return Integer.parseInt(boardSize);
    }
}