package main.java.draughts;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Game {

    private final Board board;
    private int drawCondition = 15;
    private boolean isAIPlaying = false;

    public Game() {
        //singleton pattern. Create new board object then, assign it to the Game.board.
        Board.newInstance(getBoardSizeFromUser());
        this.board = Board.getInstance();
    }

    /**
     * Get size of board from user
     * @return size of board
     */
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

    /**
     * Method that starts game between players.
     * It allows us to choose game mode: human vs human or human vs AI player.
     */
    public void start() {
        PrintingRules.showRulesForPlayers();
        Scanner scanner = new Scanner(System.in);
        this.board.createBoard();
        int opponent = 0;

        while (opponent != 1 && opponent != 2 && opponent != 3) {
            try {
                System.out.println("\nChoose your opponent: \n1. Human player \n2. AI player ");
                opponent = scanner.nextInt();
            } catch (InputMismatchException ex) {
                scanner.nextLine();
            }
        }

        switch (opponent) {
            case 1: // human vs human
                while (playRound()) {
                }
                System.out.println(board);
                break;

            case 2: // human vs ai
                while (playRoundWithAI()) {
                }
                System.out.println(board);
                break;

            case 3:
                while (playRoundAIvsAI()) {
                }
                System.out.println(board);
                break;
        }
    }

    /**
     * determines one-round actions that is, checks which player is next and whether
     * there is a winner.
     *
     * @return true if game is over, otherwise false
     */
    public boolean playRound() {
        //player 1 move
        System.out.println("\n \tPlayer 1 move - white\n");
        getStatusOfGame();
        checkStartingPosition(1);
        if (isEndGame()) return false;
        //player 2 move
        System.out.println("\n \tPlayer 2 move- black\n");
        getStatusOfGame();
        checkStartingPosition(2);
        return !isEndGame();
    }

    /**
     * Method checks if any of the conditions (draw, win) are fulfilled to end a game.
     *
     * @return if game is over, otherwise false.
     */
    public boolean isEndGame() {
        if (checkForTheDraw()) {
            System.out.println("It's a draw!");
            return true;
        }
        if (checkForWinner(1)) {
            System.out.println("Player 1 won. Congratulations!");
            return true;
        }
        if (checkForWinner(2)) {
            System.out.println("Player 2 won. Congratulations!");
            return true;
        }
        return false;
    }

    /**
     * determines one-round actions between human player and AI player
     * @return true if game is over, otherwise false
     */
    public boolean playRoundWithAI() {
        //ruch 1 gracza - human
        System.out.println("Player 1 move - white");
        checkStartingPosition(1);
        if (isEndGame()) return false;
        //ruch 2 gracza - ai
        System.out.println("Player 2 move - black");
        getAiMove(2);
        return !isEndGame();
    }

    /**
     * determines one-round actions between AI player and AI player
     * @return true if game is over, otherwise false
     */
    public boolean playRoundAIvsAI() {
        //ruch 1 gracza - ai
        System.out.println("Player 1 move - white");
        getAiMove(1);
        if (isEndGame()) return false;
        //ruch 2 gracza - ai
        System.out.println("Player 2 move - black");
        getAiMove(2);
        return !isEndGame();
    }

    /**
     * Method that checks whether there is a winner.
     */
    public boolean checkForWinner(int player) {
        if (isItPossibleToMove(player) && !isItPossibleToMove(3 - player)) return true;
        if (player == 1 && board.getBlackPawnsCounter() == 0) {
            return true;
        } else return player == 2 && board.getWhitePawnsCounter() == 0;
    }

    /**
     * Method that checks whether a game is a draw.
     */
    public boolean checkForTheDraw() {
        //drawCondition ==0 -> for 15 moves none of the players have captured enemy.
        if (drawCondition == 0) {
            return true;
        }
        //it is draw if no one can move.
        if ((!isItPossibleToMove(1) && !isItPossibleToMove(2))) {
            return true;
        }
        //it is a draw if any of the queens on the bord was placed for the third time on the same field.
        if (wasAtLeastOneQueen3TimesOnTheSameField()) return true;
        return areOnlyTwoCrownsOnBoard();
    }

    public boolean wasAtLeastOneQueen3TimesOnTheSameField() {
        for (Pawn[] pawns :
                board.getFields()) {
            for (Pawn pawn :
                    pawns) {
                if (pawn != null) {
                    if (pawn.isCrowned()) {
                        for (int[] rows :
                                pawn.getFieldsPickedWhenCrowned()) {
                            for (int col :
                                    rows) {
                                if (col >= 3) {
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
        if (player == 1) {
            color = Color.WHITE;
            moveFactor = 1;
        } else {
            color = Color.BLACK;
            moveFactor = -1;
        }
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                if (board.getFields()[row][col] != null) {
                    if (board.getFields()[row][col].getColor().equals(color)) {
                        try {
                            if (board.validateMove(board.getFields()[row][col], new Coordinates(row - moveFactor, col - moveFactor)))
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMove(board.getFields()[row][col], new Coordinates(row - moveFactor, col + moveFactor)))
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[row][col], new Coordinates(row - 2, col - 2)) != null) //  ???
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[row][col], new Coordinates(row - 2, col + 2)) != null) //  ???
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[row][col], new Coordinates(row + 2, col - 2)) != null) //  ???
                                return true;
                        } catch (Exception ignored) {
                        }
                        try {
                            if (board.validateMoveWithCapture(board.getFields()[row][col], new Coordinates(row + 2, col + 2)) != null) //  ???
                                return true;
                        } catch (Exception ignored) {
                        }
                        if (board.getFields()[row][col].isCrowned()) {
                            try {
                                if (board.validateMove(board.getFields()[row][col], new Coordinates(row + moveFactor, col + moveFactor)))
                                    return true;
                            } catch (Exception ignored) {
                            }
                            try {
                                if (board.validateMove(board.getFields()[row][col], new Coordinates(row + moveFactor, col - moveFactor)))
                                    return true;
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
        if (board.getBlackPawnsCounter() == 1 && board.getWhitePawnsCounter() == 1) {
            for (Pawn[] pawns :
                    board.getFields()) {
                for (Pawn pawn :
                        pawns) {
                    if (pawn != null) {
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
     * Check if coordinate from user is correct
     * @param inputCoordinate coordinate from user
     * @return true when coordinate is correct
     */
    public boolean isValidCoordinate(String inputCoordinate) {
        return Pattern.compile("^((?i)([a-z])(\\d+))$").matcher(inputCoordinate).matches();
    }

    /**
     * Check if coordinate from user is correct, with 'end' move choose
     * @param inputCoordinate coordinate from user
     * @return true when coordinate is correct or user want end move by 'end'
     */
    public boolean isValidCoordinateWithEnd(String inputCoordinate) {
        return (Pattern.compile("^((?i)([a-z])(\\d+))$").matcher(inputCoordinate).matches() || inputCoordinate.equals("end"));
    }

    /**
     * Check if start coordinate and end coordinate from user are correct and consistent with rules of game
     * Ask user about multiple capture if it possible
     * @param player is a player who try to make move
     */
    public void checkStartingPosition(int player) {
        isAIPlaying = false;
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
            while (isNextCapturePossible(endPawnCoordinates)) {
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

    /**
     * Check if next capture is possible depending on if it is a pawn or queen
     * @param pawnCoordinates is actual coordinate of pawn
     * @return true when next capture is possible
     */
    private boolean isNextCapturePossible(int[] pawnCoordinates) {
        Pawn pawn = board.getFields()[pawnCoordinates[0]][pawnCoordinates[1]];
        if (pawn.isCrowned()) {
            return isNextCapturePossibleQueen(pawn);
        } else {
            return isNextCapturePossiblePawn(pawn);
        }
    }

    /**
     * Check if next capture for pawn is possible
     * @param pawn pawn which is checking
     * @return true when next capture is possible
     */
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

    /**
     * Check if next capture for queen is possible
     * @param pawn pawn which is checking
     * @return true when next capture is possible
     */
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

    /**
     * Get position the pawn which player want to move
     * @param player is a player who want to move
     * @return coordinate pawn
     */
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

    /**
     * Get possition where player want to move pawn
     * @param isEndAccepted means if it is first get pawn position or multiple capture pawn possition
     * @return coordinate where player want to move pawn
     */
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

    /**
     * Check if coordinate is in range of board
     * @param coordinates is coordinate to check
     * @return true when coordinate is in range of board
     */
    private boolean areCoordinatesInBoardRange(int[] coordinates) {
        return areCoordinatesInBoardRange(coordinates[0], coordinates[1]);
    }

    /**
     * Check if coordinate is in range of board
     * @param row is row of coordinate to check
     * @param col is column of coordinate to check
     * @return true when coordinate is in range of board
     */
    private boolean areCoordinatesInBoardRange(int row, int col) {
        return (
                row >= 0 && row < board.getBoardSize() &&
                        col >= 0 && col < board.getBoardSize()
        );
    }

    /**
     * Parse coordinate from String to array of coordinate
     * @param coordinate coordinate as String
     * @return coordinate as two elements array
     */
    private int[] parseCoordinate(String coordinate) {
        String endCol = coordinate.toUpperCase().substring(0, 1);
        int endRow = Integer.parseInt(coordinate.substring(1));
        return new int[]{
                endRow - 1,
                ((int) (endCol.charAt(0))) - (int) 'A'
        };
    }

    /**
     * Checks if the starting position from user input
     * is a pawn valid, and is an ending position within the board boundaries.
     * @return true if move is possible and executed, otherwise false.
     */
    public boolean tryToMakeMove(Pawn pawn, Coordinates movePosition) {
        if (this.board.validateMove(pawn, movePosition)) {
            //move without capture
            //check crown
            if (this.board.validateCrowning(pawn, movePosition)) pawn.setCrowned(this.board);
            this.board.movePawn(pawn, movePosition);
            //decrement drawCondition counter because in this move no pawn was captured
            drawCondition--;
            return true;
        }
        Pawn pawnToCapture = this.board.validateMoveWithCapture(pawn, movePosition);
        if (pawnToCapture != null) {
            //move with capture
            //check crown
            if (this.board.validateCrowning(pawn, movePosition) && !isNextCapturePossiblePawn(pawn))
                pawn.setCrowned(this.board);
            this.board.movePawn(pawn, movePosition);
            this.board.removePawn(pawnToCapture);
            //reset drawCondition because there was capture
            drawCondition = 15;
            return true;
        }

        if (!isAIPlaying) {
            System.out.println("Your move is incorrect");
        }

        return false;
    }

    public void getStatusOfGame() {
        int numberOfPawnsAtTheBeginningOfGame = board.getBoardSize() * 2;
        int numberOfWhitePawnsCaptured = numberOfPawnsAtTheBeginningOfGame - board.getWhitePawnsCounter();
        int numberOfBlackPawnsCaptured = numberOfPawnsAtTheBeginningOfGame - board.getBlackPawnsCounter();
        System.out.println("\tThere are " + numberOfWhitePawnsCaptured + " white pawns captured!");
        System.out.println("\tThere are " + numberOfBlackPawnsCaptured + " black pawns captured!");
    }

    /**
     * Selects random AI player's pawn, looking for valid capture and non-capture moves and
     * selects one of them, then makes chosen move.
     */
    public void getAiMove(int player) {
        // create list with all current player's pawns
        isAIPlaying = true;
        Color color;
        if (player == 1) {
            color = Color.white;
        } else {
            color = Color.black;
        }
        System.out.println(this.board);
        LinkedList<Pawn> pawns = new LinkedList<Pawn>();
        for (Pawn[] fields : this.board.getFields()) {
            for (Pawn pawn : fields) {
                if (pawn != null) {
                    if (pawn.getColor().equals(color)) {
                        pawns.add(pawn);
                    }
                }
            }
        }

        // create lists with all possible pawn's moves
        int[] tempCoordinates = {-1, -1};
        LinkedList<Coordinates> possibleCaptureMoves = new LinkedList<>();
        LinkedList<Coordinates> possibleMovesWithoutCapture = new LinkedList<>();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        while (!pawns.isEmpty()) {
            // select one random pawn to move
            int index = (int) (Math.random() * pawns.size());
            Pawn chosenPawn = pawns.get(index);
            // normal moves for white pawn
            if (color.equals(Color.white)) {
                tempCoordinates[0] = chosenPawn.getPosition().getRow() - 1;
                tempCoordinates[1] = chosenPawn.getPosition().getCol() - 1;
                if (areCoordinatesInBoardRange(tempCoordinates)) {
                    possibleMovesWithoutCapture.add(new Coordinates(tempCoordinates[0], tempCoordinates[1]));
                }

                tempCoordinates[0] = chosenPawn.getPosition().getRow() - 1;
                tempCoordinates[1] = chosenPawn.getPosition().getCol() + 1;
                if (areCoordinatesInBoardRange(tempCoordinates)) {
                    possibleMovesWithoutCapture.add(new Coordinates(tempCoordinates[0], tempCoordinates[1]));
                }

                // normal moves for black pawn
            } else {
                tempCoordinates[0] = chosenPawn.getPosition().getRow() + 1;
                tempCoordinates[1] = chosenPawn.getPosition().getCol() - 1;
                if (areCoordinatesInBoardRange(tempCoordinates)) {
                    possibleMovesWithoutCapture.add(new Coordinates(tempCoordinates[0], tempCoordinates[1]));
                }

                tempCoordinates[0] = chosenPawn.getPosition().getRow() + 1;
                tempCoordinates[1] = chosenPawn.getPosition().getCol() + 1;
                if (areCoordinatesInBoardRange(tempCoordinates)) {
                    possibleMovesWithoutCapture.add(new Coordinates(tempCoordinates[0], tempCoordinates[1]));
                }
            }

            //capture moves
            tempCoordinates[0] = chosenPawn.getPosition().getRow() + 2;
            tempCoordinates[1] = chosenPawn.getPosition().getCol() + 2;
            if (areCoordinatesInBoardRange(tempCoordinates)) {
                possibleCaptureMoves.add(new Coordinates(tempCoordinates[0], tempCoordinates[1]));
            }

            tempCoordinates[0] = chosenPawn.getPosition().getRow() + 2;
            tempCoordinates[1] = chosenPawn.getPosition().getCol() - 2;
            if (areCoordinatesInBoardRange(tempCoordinates)) {
                possibleCaptureMoves.add(new Coordinates(tempCoordinates[0], tempCoordinates[1]));
            }

            tempCoordinates[0] = chosenPawn.getPosition().getRow() - 2;
            tempCoordinates[1] = chosenPawn.getPosition().getCol() + 2;
            if (areCoordinatesInBoardRange(tempCoordinates)) {
                possibleCaptureMoves.add(new Coordinates(tempCoordinates[0], tempCoordinates[1]));
            }

            tempCoordinates[0] = chosenPawn.getPosition().getRow() - 2;
            tempCoordinates[1] = chosenPawn.getPosition().getCol() - 2;
            if (areCoordinatesInBoardRange(tempCoordinates)) {
                possibleCaptureMoves.add(new Coordinates(tempCoordinates[0], tempCoordinates[1]));
            }


            // if it is possible - make a random capture move, if not - remove move from the list
            while (!possibleCaptureMoves.isEmpty()) {
                int i = (int) (Math.random() * possibleCaptureMoves.size());
                if (tryToMakeMove(chosenPawn, possibleCaptureMoves.get(i))) {
                    return;
                } else {
                    possibleCaptureMoves.remove(i);
                }
            }

            // if it is possible - make a random normal move, if not - remove move from the list
            while (!possibleMovesWithoutCapture.isEmpty()) {
                int i = (int) (Math.random() * possibleCaptureMoves.size());
                if (tryToMakeMove(chosenPawn, possibleMovesWithoutCapture.get(i))) {
                    return;
                } else {
                    possibleMovesWithoutCapture.remove(i);
                }
            }
            pawns.remove(index);
        }
    }
}
