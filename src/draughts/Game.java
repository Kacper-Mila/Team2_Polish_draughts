package draughts;

public class Game {

    private Board board;
    public Game(int n){
        this.board = new Board(n);
    }
    public Board getBoard(){
        return this.board;
    }


}
