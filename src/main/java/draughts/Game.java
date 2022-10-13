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
        PrintingRules.showRulesForPlayers();
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
        System.out.println("\n \tPlayer 1 move - white\n");
        getStatusOfGame();
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
        System.out.println("\n \tPlayer 2 move- black\n");
        getStatusOfGame();
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
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                if(board.getFields()[row][col] != null){
                    if(board.getFields()[row][col].getColor().equals(color)){
                        try {
                            if (board.validateMove(board.getFields()[row][col], new Coordinates(row - moveFactor, col - moveFactor))) return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMove(board.getFields()[row][col], new Coordinates(row - moveFactor, col + moveFactor))) return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[row][col], new Coordinates(row - 2, col - 2)) != null) //  ↖
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[row][col], new Coordinates(row - 2, col + 2)) != null) //  ↙
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[row][col], new Coordinates(row + 2, col - 2)) != null) //  ↗
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[row][col], new Coordinates(row + 2, col + 2)) != null) //  ↘
                                return true;
                        } catch (Exception ignored) {
                        }
                        if(board.getFields()[row][col].isCrowned()){
                            try {
                                if (board.validateMove(board.getFields()[row][col], new Coordinates(row + moveFactor, col + moveFactor))) return true;
                            } catch (Exception ignored) {
                            }
                            try {
                                if (board.validateMove(board.getFields()[row][col], new Coordinates(row + moveFactor, col - moveFactor))) return true;
                            } catch (Exception ignored) {
                            }
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

    public boolean isValidCoordinateWithEnd(String inputCoordinate) {
        return (Pattern.compile("^((?i)([a-z])(\\d+))$").matcher(inputCoordinate).matches() || inputCoordinate.equals("end"));
    }

    //return user color
    public int getUserPawnColor() {
        Scanner scanner = new Scanner(System.in);
        int userColor = scanner.nextInt();
        System.out.println("Choose yours pawns color");
        String pattern = "^[1-2]$";
        do {

        } while (!String.valueOf(userColor).matches(pattern));
        return 0;
    }

    public void checkStartingPosition(int player) {
        int[] startPawnCoordinates = {0, 0};
        int[] endPawnCoordinates = {0, 0};
        Coordinates newPawnPosition;
        boolean isCapture = false;
        Pawn pawn = null;
        do {
            startPawnCoordinates = getStartPawnPosition(player);
            endPawnCoordinates = getNewPawnPosition(false);
            newPawnPosition = new Coordinates(endPawnCoordinates[0], endPawnCoordinates[1]);
            pawn = board.getFields()[startPawnCoordinates[0]][startPawnCoordinates[1]];
            isCapture = board.validateMoveWithCapture(pawn, newPawnPosition) != null;
        } while (!tryToMakeMove(pawn, newPawnPosition));

        if (isCapture) {
            while(isNextCapturePossible(endPawnCoordinates)) {
                startPawnCoordinates = endPawnCoordinates;
                do {
                    endPawnCoordinates = getNewPawnPosition(true);
                    if (endPawnCoordinates == null) {
                        return;
                    }
                    newPawnPosition = new Coordinates(endPawnCoordinates[0], endPawnCoordinates[1]);
                } while (!tryToMakeMove(board.getFields()[startPawnCoordinates[0]][startPawnCoordinates[1]], newPawnPosition));
            }
        }
    }


    private boolean isNextCapturePossible(int[] pawnCoordinates) {
        Pawn pawn = board.getFields()[pawnCoordinates[0]][pawnCoordinates[1]];
        if(pawn.isCrowned()) {
            return isNextCapturePossibleQueen(pawn);
        } else {
            return isNextCapturePossiblePawn(pawn);
        }
    }

    private boolean isNextCapturePossiblePawn(Pawn pawn) {
        Coordinates pawnCoordinates = pawn.getPosition();
        for (int row = pawnCoordinates.getRow() - 2; row <= pawnCoordinates.getRow() + 2; row += 4) {
            for (int col = pawnCoordinates.getCol() - 2; col <= pawnCoordinates.getCol() + 2; col += 4) {
                if (areCoordinatesInBoardRange(row, col) &&
                    board.validateMoveWithCapture(pawn, new Coordinates(row, col)) != null
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isNextCapturePossibleQueen(Pawn pawn) {
        Coordinates pawnCoordinates = pawn.getPosition();
        for (int i = -board.getBoardSize(); i <= board.getBoardSize(); i++) {
            // Check diagonal movements up-left to down-right
            if (areCoordinatesInBoardRange(pawnCoordinates.getRow() + i, pawnCoordinates.getCol() + i) &&
                    board.validateMoveWithCapture(pawn, new Coordinates(pawnCoordinates.getRow() + i, pawnCoordinates.getCol() + i)) != null
            ) {
                return true;
            }

            // Check diagonal movements up-right to down-left
            if (areCoordinatesInBoardRange(pawnCoordinates.getRow() + i, pawnCoordinates.getCol() - i) &&
                    board.validateMoveWithCapture(pawn, new Coordinates(pawnCoordinates.getRow() + i, pawnCoordinates.getCol() - i)) != null
            ) {
                return true;
            }
        }

        return false;
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

    private int[] getNewPawnPosition(boolean isEndAccepted) {
        int[] newPawnPosition = null;
        Scanner scanner = new Scanner(System.in);
        String endCoordinate;
        do {
            if (!isEndAccepted) {
                System.out.println("Enter coordinates where you want to move your pawn. (eg. B2)");
                endCoordinate = scanner.nextLine();
                if (!isValidCoordinate(endCoordinate)) {
                    System.out.println("Coordinates are incorrect");
                    continue;
                }
            } else {
                System.out.println(board);
                System.out.println("You have optional movement with capture (multicapture), if you want capture enter coordinate, else press 'end'");
                endCoordinate = scanner.nextLine();
                if (!isValidCoordinateWithEnd(endCoordinate)) {
                    System.out.println("Incorrect value, enter coordinate or 'end'");
                    continue;

                }
                if (endCoordinate.equals("end")) {
                    return null;
                }
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
        return areCoordinatesInBoardRange(coordinates[0], coordinates[1]);
    }

    private boolean areCoordinatesInBoardRange(int row, int col) {
        return (
                row >= 0 && row < board.getBoardSize() &&
                        col >= 0 && col < board.getBoardSize()
        );
    }

    private int[] parseCoordinate(String coordinate) {
        String endCol = coordinate.toUpperCase().substring(0, 1);
        int endRow = Integer.parseInt(coordinate.substring(1));
        return new int[]{
                endRow - 1,
                ((int) (endCol.charAt(0))) - (int) 'A'
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
                //check crown
                if(this.board.validateCrowning(pawn,movePosition)) pawn.setCrowned(this.board);
                this.board.movePawn(pawn, movePosition);
                drawCondition--;
                if(pawn.isCrowned()){
                    int [][] tmpQueenFields = pawn.getFieldsPickedWhenCrowned();
                    tmpQueenFields[movePosition.getRow()][movePosition.getCol()] ++;
                    pawn.setFieldsPickedWhenCrowned(tmpQueenFields);
                }
                return true;
            }
            //sprawdz czy ruch jest o dwa pola a miedzy nimi jest pionek przeciwnika
            Pawn pawnToCapture = this.board.validateMoveWithCapture(pawn, movePosition);
            if (pawnToCapture != null ) {
                //wykonaj ruch z biciem
                //check crown
                if(this.board.validateCrowning(pawn,movePosition)&&!isNextCapturePossiblePawn(pawn)) pawn.setCrowned(this.board);
                this.board.movePawn(pawn, movePosition);
                this.board.removePawn(pawnToCapture);
                drawCondition = 15;
                if(pawn.isCrowned()){
                    int [][] tmpQueenFields = pawn.getFieldsPickedWhenCrowned();
                    tmpQueenFields[movePosition.getRow()][movePosition.getCol()] ++;
                    pawn.setFieldsPickedWhenCrowned(tmpQueenFields);
                }
                return true;
            }

        System.out.println("Your move is incorrect");
        return false;
    }
    public void getStatusOfGame(){
        int numberOfPawnsAtTheBeginningOfGame = board.getBoardSize() *2;
        int numberOfWhitePawnsCaptured = numberOfPawnsAtTheBeginningOfGame - board.getWhitePawnsCounter();
        int numberOfBlackPawnsCaptured = numberOfPawnsAtTheBeginningOfGame - board.getBlackPawnsCounter();
        System.out.println("\tThere are " + numberOfWhitePawnsCaptured + " white pawns captured!");
        System.out.println("\tThere are " + numberOfBlackPawnsCaptured + " black pawns captured!");
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
