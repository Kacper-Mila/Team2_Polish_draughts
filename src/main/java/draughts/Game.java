package main.java.draughts;

import java.awt.*;
import java.util.LinkedList;
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
        int[] startPawnCoordinates;
        int[] endPawnCoordianes;
        Coordinates newPawnPosition;
        do {
            startPawnCoordinates = getStartPawnPosition(player);
            endPawnCoordianes = getNewPawnPosition();
            newPawnPosition = new Coordinates(endPawnCoordianes[0], endPawnCoordianes[1]);
        } while (!tryToMakeMove(board.getFields()[startPawnCoordinates[0]][startPawnCoordinates[1]], newPawnPosition));
        //TODO:
        //etap 2: zapytaj o liste wspolrzednych
    }

    private int[] getStartPawnPosition(int player) {
        int[] pawnPosition;
        Scanner scanner = new Scanner(System.in);
        String startCoordinate;
        do {
            System.out.println(board);
            System.out.println("Enter coordinates of the pawn you want to move. (eg. A1)");
            startCoordinate = scanner.nextLine();
            if (!isValidCoordinate(startCoordinate)) {
                System.out.println("Coordinates are incorrect");
                continue;
            }

            pawnPosition = parseCoordinate(startCoordinate);
            if (!areCoordinatesInBoardRange(pawnPosition)) {
                System.out.println("Coordinates are out of the size of the board");
                continue;
            }

            if ((board.getFields()[pawnPosition[0]][pawnPosition[1]] == null)) {
                System.out.println("Selected field is empty, choose one with your pawn");
                continue;
            }
            if ((player == 1 && board.getFields()[pawnPosition[0]][pawnPosition[1]].getColor() != Color.WHITE) ||
                    (player == 2 && board.getFields()[pawnPosition[0]][pawnPosition[1]].getColor() != Color.BLACK)) {
                System.out.println("Selected pawn belongs to the enemy, choose one with your pawn");
                continue;
            }
            break;
        } while (true);
        return pawnPosition;
    }

    private int[] getNewPawnPosition() {
        int[] newPawnPosition;
        Scanner scanner = new Scanner(System.in);
        String endCoordinate;
        //sprawdzam poprawnosc koncowych wspolrzednych
        do {
            System.out.println("Enter coordinates where you want to move your pawn. (eg. B2)");
            endCoordinate = scanner.nextLine();
            if (!isValidCoordinate(endCoordinate)) {
                System.out.println("Coordinates are incorrect");
                continue;
            }

            newPawnPosition = parseCoordinate(endCoordinate);
            if (!areCoordinatesInBoardRange(newPawnPosition)) {
                System.out.println("Coordinates are out of the size of the board");
                continue;
            }

            break;
        } while (true);
        return newPawnPosition;
    }

    private boolean areCoordinatesInBoardRange(int[] coordinates) {
        return (
                coordinates[0] >= 0 && coordinates[0] < board.getBoardSize() &&
                        coordinates[1] >= 0 && coordinates[1] < board.getBoardSize()
        );
    }

    private int[] parseCoordinate(String coordinate) {
        String endCol = coordinate.toUpperCase().substring(0, 1);
        int endRow = Integer.parseInt(coordinate.substring(1));
        return new int[]{
                endRow - 1, // col
                ((int) (endCol.charAt(0))) - (int) 'A' // row
        };
    }

    /**
     * There is a method that checks if the starting position from user input
     * is a valid pawn and if the ending position is within board boundaries.
     * If so, it calls tryToMakeMove() on pawn instance.
     *
     * @return true if move is possible and executed, otherwise false.
     */
    public boolean tryToMakeMove(Pawn pawn, Coordinates movePosition) {
        if (this.board.validateMove(pawn, movePosition)) {
            //nastepuje sam ruch, bez bicia
            this.board.movePawn(pawn, movePosition);
            drawCondition--;
            return true;
        }
        //sprawdz czy ruch jest o dwa pola a miedzy nimi jest pionek przeciwnika
        Pawn pawnToCapture = this.board.validateMoveWithCapture(pawn, movePosition);
        if (pawnToCapture != null) {
            //wykonaj ruch z biciem
            this.board.movePawn(pawn, movePosition);
            this.board.removePawn(pawnToCapture);
            drawCondition = 15; //TODO opracowac funkcje
            return true;
        }
        System.out.println("Your move is incorrect");
        return false;
    }

    public void getAiMove (int player){
        // create list with all current player's pawns
        Color color;
        if (player == 1) {
            color = Color.white;
        } else {
            color = Color.black;
        }

        LinkedList <Pawn> pawns = new LinkedList<Pawn>();
        for (Pawn [] fields : this.board.getFields()) {
            for (Pawn pawn : fields) {
                if (pawn.getColor().equals(color)) {
                    pawns.add(pawn);
                }
            }
        }

        // select one random pawn to move
        int index = (int)(Math.random() * pawns.size());
        Pawn chosenPawn = pawns.get(index);

        // create lists with all possible pawn's moves
        int[] tempCoordinates = null;
        LinkedList<Coordinates> possibleCaptureMoves = null;
        LinkedList<Coordinates> possibleMovesWithoutCapture = null;

        if (color.equals(Color.white)) {
            tempCoordinates[0] = chosenPawn.getPosition().getRow() - 1;
            tempCoordinates[1] = chosenPawn.getPosition().getCol() - 1;
            if (areCoordinatesInBoardRange(tempCoordinates)) {
                possibleMovesWithoutCapture.add((tempCoordinates[0]), (tempCoordinates[1]));
            }

            tempCoordinates[0] = chosenPawn.getPosition().getRow() - 1;
            tempCoordinates[1] = chosenPawn.getPosition().getCol() + 1;
            if (areCoordinatesInBoardRange(tempCoordinates)) {
                possibleMovesWithoutCapture.add((tempCoordinates[0]), (tempCoordinates[1]));
            }


        } else {

        }


        // for white player
            //normal move: x-1 y-1 or x-1 y+1
            // capture move: x+2 y+2, x+2 y-2, x-2 y+2, x-2 y-2
        // for  black player:
            // normal move: x+1 y-1 or x+1 y+1
            // capture move: x+2 y+2, x+2 y-2, x-2 y+2, x-2 y-2
        // check if capture is possible





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
