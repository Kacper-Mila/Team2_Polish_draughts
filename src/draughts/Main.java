package draughts;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Polish draughts!");
        System.out.println("Please enter length of the board side (between 10 and 20");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Game game = new Game(10);
        game.start();
    }
}