package main.java.draughts;

import java.awt.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    private Board board;
    private int drawCondition = 15;


    public Game() {
        this.board = new Board(getBoardSizeFromUser());
    }
    private static int getBoardSizeFromUser() {
        Scanner readBoardSize = new Scanner(System.in);
        String boardSize;
        String regex = "^(1[0-9]|20)$";
        do {
            System.out.println("Enter the board size, it must be larger or equal 10 and smaller or equal 20, also even.");
            boardSize = readBoardSize.nextLine();
        } while (!boardSize.matches(regex) || Integer.parseInt(boardSize) % 2 != 0);
        return Integer.parseInt(boardSize);
    }
    public Board getBoard() {
        return this.board;
    }

    /**
     * method that starts game between players.
     */
    public void start() {
        this.board.createBoard();
        do {
            playRound();
        } while (!showResults());
    }

    public boolean showResults(){
        boolean win1 = checkForWinner(1);
        boolean win2 = checkForWinner(2);
        if(win1|| win2 || drawCondition == 0){ //TODO drawCondition do rozbudowania
            if (win1) {
                System.out.println("Player 1 won. Congratulations!");
            } else if (win2) {
                System.out.println("Player 2 won. Congratulations!");
            } else {
                System.out.println("It's a draw!");
            }
            return true;
        }
        return false;
    }

    /**
     * determines one-round actions that is, checks which player is next and whether
     * there is a winner.
     */
    public void playRound() {
        //ruch 1 gracza
        System.out.println("Player 1 move - white");
        checkStartingPosition(1);
        if (checkForWinner(1)) return;

        //ruch 2 gracza
        System.out.println("Player 2 move- black");
        checkStartingPosition(2);
        checkForWinner(2);
    }

    /**
     * method that checks whether there is a winner after each round.
     * also checks for draws.
     */
    public boolean checkForWinner(int player) {
        if (drawCondition == 0) {
            System.out.println("It's draw! Game over! ");
            return false;
        }
        if (player == 1 && board.getBlackPawnsCounter() == 0) {
            return true;
        } else return player == 2 && board.getWhitePawnsCounter() == 0;
    }

    /**
     * There is a method that checks if the starting position from user input
     * is a valid pawn and if the ending position is within board boundaries.
     * If so, it calls tryToMakeMove() on pawn instance.
     */
    public boolean isValidCoordinate(String inputCoordinate) {
        return Pattern.compile("^((?i)([a-z])(\\d+))$").matcher(inputCoordinate).matches();
    }

    public void checkStartingPosition(int player) {
        Scanner scanner = new Scanner(System.in);
        String startCoordinate;
        String endCoordinate;
        int startColAsNumber;
        int startRowAsNumber;
        int endColAsNumber;
        int endRowAsNumber;
        char lastCol = (char) (board.getBoardSize() + (int) 'a' - 1);
        String regex = String.format("(?i)[a-%s]", String.valueOf(lastCol));
        Coordinates newPosition;
        //TODO
        //sprawdzam poprawnosc poczatkowych wspolrzednych
        //w osobnej metodzie wczytac dane a w osobnej dokonac ich validacji
        //podzielic na mniejsze metody
        do {

            do {
                System.out.println(board);
                System.out.println("Enter coordinates of the pawn you want to move. (eg. A1)");
                startCoordinate = scanner.nextLine();
                if (!isValidCoordinate(startCoordinate)) {
                    System.out.println("Coordinates are incorrect");
                    continue;
                }
                String startCol = startCoordinate.substring(0, 1);
                int startRow = Integer.parseInt(startCoordinate.substring(1));
                startColAsNumber = ((int) (startCol.toUpperCase().charAt(0))) - (int) 'A';
                startRowAsNumber = startRow - 1;
                if (!startCol.matches(regex) || startRow <= 0 || startRow > board.getBoardSize()) {
                    System.out.println("CoordFinates are out of the size of the board");
                    continue;
                }
                if ((board.getFields()[startRowAsNumber][startColAsNumber] == null)) {
                    System.out.println("Selected field is empty, choose one with your pawn");
                    continue;
                }
                if ((player == 1 && board.getFields()[startRowAsNumber][startColAsNumber].getColor() != Color.WHITE) ||
                        (player == 2 && board.getFields()[startRowAsNumber][startColAsNumber].getColor() != Color.BLACK)) {
                    System.out.println("Selected pawn belongs to the enemy, choose one with your pawn");
                    continue;
                }

                break;
            } while (true);

            //sprawdzam poprawnosc koncowych wspolrzednych
            do {
                System.out.println("Enter coordinates where you want to move your pawn. (eg. B2)");
                endCoordinate = scanner.nextLine();
                if (!isValidCoordinate(endCoordinate)) {
                    System.out.println("Coordinates are incorrect");
                    continue;
                }
                String endCol = endCoordinate.substring(0, 1);
                int endRow = Integer.parseInt(endCoordinate.substring(1));
                endColAsNumber = ((int) (endCol.toUpperCase().charAt(0))) - (int) 'A'; //error
                endRowAsNumber = endRow - 1; //zwieksza 0 1
                if (!endCol.matches(regex) || endRow <= 0 || endRow > board.getBoardSize()) {
                    System.out.println("Selected pawn belongs to the enemy, choose one with your pawn");
                    continue;
                }
                break;
            } while (true);

            newPosition = new Coordinates(endRowAsNumber, endColAsNumber);
        } while (!tryToMakeMove(board.getFields()[startRowAsNumber][startColAsNumber], newPosition));
        //TODO:
        //etap 2:
        //zapytaj o liste wspolrzednych
    }

    /**
     * There is a method that checks if the starting position from user input
     * is a valid pawn and if the ending position is within board boundaries.
     * If so, it calls tryToMakeMove() on pawn instance.
     *
     * @return true if move is possible and executed, otherwise false.
     */
    public boolean tryToMakeMove(Pawn pawn, Coordinates movePosition) {
        if (pawn.validateMove(board, movePosition)) {
            //nastepuje sam ruch, bez bicia
            board.movePawn(pawn, movePosition);
            drawCondition--;
            return true;
        }
        //sprawdz czy ruch jest o dwa pola a miedzy nimi jest pionek przeciwnika
        Pawn pawnToCapture = pawn.validateMoveWithCapture(board, movePosition);
        if (pawnToCapture != null) {
            //wykonaj ruch z biciem
            board.movePawn(pawn, movePosition);
            board.removePawn(pawnToCapture);
            drawCondition = 15; //TODO opracowac funkcje
            return true;
        }
        System.out.println("Your move is incorrect");
        return false;
    }
}

//  logika wykonania ruchu. (sa dwie mozliwosci: 1. ruch bez bicia, 2. ruch z biciem)
//  (etapy rozwoju logiki, od prostszego do bardziej zlozonego)
//      etap 1 logiki:
//          wykonuje jeden z mozilwych ruchow (z biciem albo bez) z wykorzystaniem metod z pozostalych klas.
//          prawdopodbnie logika bedzie rozbudowana dlatego sugerujemy z gory rozbijanie jej na osobne metody ktore,
//          tutaj beda wywolywane

//          jezeli bicie nastapilo to ustawiamy licznik drawCondition na 15
//          jezeli bicia nie bylo licznik zmniejszamy o 1

//      etap 2 logiki:
//          Sprawdzanie wszystkich mozliwych bic przez rekurencyjne sprawdzanie warunku az do wyczerpania mozliwosci
//          najlepsza sciezka bicia = sciezka z nadluzszego stosu.
//          jezeli wybor gracza nie bedzie najlepszym wyborem nie wykonuj ruchu, zwroc false
//
//  }else return false // zwroc false jezeli ruch nie moze zostac wykonany. true jeseli ruch zostal wykonany
//
