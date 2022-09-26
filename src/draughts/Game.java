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

    }

    /**
     * method that checks whether there is a winner after each round.
     * also checks for draws.
     */
    public void checkForWinner(){

    }

}
