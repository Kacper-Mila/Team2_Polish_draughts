package draughts;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(getBoardSize());
        game.start();
    }
    private static int getBoardSize() {
        Scanner readBoardSize= new Scanner(System.in);
        String boardSize;
        String regex = "1[0-9]|20";
        do {
            System.out.println("Podaj wielkosc tablicy z zakresu od 10 do 20");
            boardSize = readBoardSize.nextLine();
        }while(!boardSize.matches(regex));
        return Integer.parseInt(boardSize);
    }
}