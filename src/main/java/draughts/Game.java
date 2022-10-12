package main.java.draughts;

import java.awt.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Game {

    private final Board board;
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
        while (playRound()){
            //within playRound() player's moves are played. If none of them wins, or it is draw then game is kept running
        }
        System.out.println(board);
    }

    /**
     * determines one-round actions that is, checks which player is next and whether
     * there is a winner.
     *
     * @return true if game is over, otherwise false
     */
    public boolean playRound() {
        //ruch 1 gracza
        System.out.println("Player 1 move - white");
        checkStartingPosition(1);
        if (checkForTheDraw()){
            System.out.println("It's a draw!");
            return false;
        }
        if (checkForWinner(1)) {
            System.out.println("Player 1 won. Congratulations!");
            return false;
        }
        if(checkForWinner(2) ){
            System.out.println("Player 2 won. Congratulations!");
            return false;
        }
        //ruch 2 gracza
        System.out.println("Player 2 move- black");
        checkStartingPosition(2);
        if (checkForTheDraw()){
            System.out.println("It's a draw!");
            return false;
        }
        if (checkForWinner(1)) {
            System.out.println("Player 1 won. Congratulations!");
            return false;
        }
        if(checkForWinner(2) ){
            System.out.println("Player 2 won. Congratulations!");
            return false;
        }
        return true;
    }

    /**
     * method that checks whether there is a winner after each round.
     * also checks for draws.
     */
    public boolean checkForWinner(int player) {
        if(isItPossibleToMove(player)&&!isItPossibleToMove(3 - player)) return true;
        if (player == 1 && board.getBlackPawnsCounter() == 0) {
            return true;
        } else return player == 2 && board.getWhitePawnsCounter() == 0;
    }

    public boolean checkForTheDraw() {
        if (drawCondition == 0) {
            return true;
        }
        if ((!isItPossibleToMove(1) && !isItPossibleToMove(2))) {
            return true;
        }
        if (wasAtLeastOneQueen3TimesOnTheSameField()) return true;
        return areOnlyTwoCrownsOnBoard();
    }

    public boolean wasAtLeastOneQueen3TimesOnTheSameField(){
        for (Pawn[] pawns:
                board.getFields()) {
            for (Pawn pawn:
                    pawns) {
                if(pawn != null) {
                    if (pawn.isCrowned()) {
                        for (int [] rows:
                                pawn.getFieldsPickedWhenCrowned()) {
                            for (int col:
                                    rows) {
                                if(col >=3 ) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isItPossibleToMove(int player) {
        int moveFactor;
        Color color;
        if(player==1){
            color = Color.WHITE;
            moveFactor = 1;
        }else{
            color = Color.BLACK;
            moveFactor = -1;
        }
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                if(board.getFields()[i][j] != null){
                    if(board.getFields()[i][j].getColor().equals(color)){
                        try {
                            if (board.validateMove(board.getFields()[i][j], new Coordinates(i - moveFactor, j - moveFactor))) return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMove(board.getFields()[i][j], new Coordinates(i - moveFactor, j + moveFactor))) return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[i][j], new Coordinates(i - 2, j - 2)) != null)
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[i][j], new Coordinates(i - 2, j + 2)) != null)
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[i][j], new Coordinates(i + 2, j - 2)) != null)
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[i][j], new Coordinates(i + 2, j + 2)) != null)
                                return true;
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean areOnlyTwoCrownsOnBoard() {
        boolean isBlackCrowned = false;
        boolean isWhiteCrowned = false;
        if(board.getBlackPawnsCounter()==1 && board.getWhitePawnsCounter()==1){
            for (Pawn[] pawns:
                 board.getFields()) {
                for (Pawn pawn:
                     pawns) {
                    if(pawn != null) {
                        if (pawn.isCrowned()) {
                            if (pawn.getColor() == Color.black) {
                                isBlackCrowned = true;
                            } else {
                                isWhiteCrowned = true;
                            }
                        }
                    }
                }
            }
        }
        return (isBlackCrowned && isWhiteCrowned);
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
