package draughts;

public class Game {

    private Board board;
    private int drawCondition = 15;
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
        //instrukcja/powitalna wiadomosc
        do{
            playRound();
        }while (!(checkForWinner(2)||checkForWinner(1)||drawCondition==0));
        //remis zachodzi gdy ostatnie bicie nastapilo nie pozniej niz 15 ruchow
    }

    /**
     * determines one-round actions that is, checks which player is next and whether
     * there is a winner.
     *
     * @return
     */
    public void playRound(){
        //ruch 1 gracza
        checkStartingPosition(1);
        if (checkForWinner(1)) return;
        //ruch 2 gracza
        checkStartingPosition(2);
        checkForWinner(2);
    }

    /**
     * method that checks whether there is a winner after each round.
     * also checks for draws.
     */
    public boolean checkForWinner(int player){
        //sprawdza czy podany gracz spelnil warunki zakonczenia gry
        //sprawdza czy zostaly pionki przeciwnika
        //dla gracza = 0 sprawdza czy gra zakonczyla sie remisem??
        return false;
    }

    /**
     * There is a method that checks if the starting position from user input
     * is a valid pawn and if the ending position is within board boundaries.
     * If so, it calls tryToMakeMove() on pawn instance.
     */
    public void checkStartingPosition(int player){
        //do{
        //ask user for start position (pawn)
        //ask user for end position sekwencja ruchów. max liczba ruchow to liczba pionkow przeciwnika
        //map inserted data to Coordinates class
        //use Pawn's method to validate:
        //  - if starting position is valid pawn (if at selected coordinates there is pawn, and it colors matches player)
        //  - if end position is within boundaries (is separate method needed? if so should it be implemented inside Board?)
        // if it is valid call tryToMakeMove()
        // }while(!tryToMakeMove()) "masz do wykonania lepszy ruch"

    }

    /**
     * There is a method ({@link Game#checkStartingPosition}) that checks if the starting position from user input
     * is a valid pawn and if the ending position is within board boundaries.
     * If so, it calls tryToMakeMove() on pawn instance.
     * @return true if move is possible and executed, otherwise false.
     */
    public boolean tryToMakeMove(Coordinates pawnPosition, Coordinates endPosition){
        //try to move pawn according to game rules (Pawn.validateMove())
        //if (Pawn.validateMove()){
        //  logika wykonania ruchu.
        //  sa dwi mozliwosci:
        //      etap 1 logiki:
            //      wykonuje board.makeMove() albo,
            //      bicie pionków i przesuniecie pionka.
            //      jezeli bicie nastapilo to ustawiamy licznik drawCondition na 15
        //          jezeli bicia nie bylo licznik zmniejszamy o 1
        //      etap 2 logiki:
        //          Sprawdzanie wszystkie mozliwe bicia przez rekurencyjne sprawdzanie warunku dla kazdego z pionkow
        //          sciezka bicia = sciezka z nadluzszego stosu.
        //
        //  }else return false // zwroc false jezeli ruch nie moze zostac wykonany
        //
        return false;
    }

}
