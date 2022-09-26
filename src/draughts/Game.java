package draughts;

public class Game {

    private Board board;
    public Game(int n){
        this.board = new Board(n);
    }
    public Board getBoard(){
        return this.board;
    }

    /**
     * method that starts game between players.
     */
    public void start(){
    }

    /**
     *  determines one-round actions that is, checks which player is next and whether
     *  there is a winner.
     */
    public void playRound(){
        //do-while loop calling game's methods
    }

    /**
     * method that checks whether there is a winner after each round.
     * also checks for draws.
     */
    public int checkForWinner(){
        return 0;//1 if first player wins, 2 if second, 0 if neither.
    }

    /**
     * There is a method that checks if the starting position from user input
     * is a valid pawn and if the ending position is within board boundaries.
     * If so, it calls tryToMakeMove() on pawn instance.
     */
    public void checkStartingPosition(int player){
        //ask user for start position
        //ask user for end position
        //map inserted data to Coordinates class
        //use Pawn's method to validate:
        //  - if starting position is valid pawn (if at selected coordinates there is pawn, and it colors matches player)
        //  - if end position is within boundaries (is separate method needed? if so should it be implemented inside Board?)
        // if it is valid call tryToMakeMove()
    }

    /**
     * There is a method ({@link Game#checkStartingPosition}) that checks if the starting position from user input
     * is a valid pawn and if the ending position is within board boundaries.
     * If so, it calls tryToMakeMove() on pawn instance.
     * @return true if move is possible and executed, otherwise false.
     */
    public boolean tryToMakeMove(Coordinates pawnPosition, Coordinates endPosition){
        //try to move pawn according to game rules (Pawn.validateMove())
        //verify here all the game rules:
        //  crowning (separate method ?)
        //  capture (separate method ?)
        return false;
    }

}
