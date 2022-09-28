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

        System.out.println("Enter coordinates in format [letter][number], e.g. A5.");
        this.board.createBoard();
        do{
            playRound();
        }while (!(checkForWinner(2)||checkForWinner(1)||drawCondition==0));
        //remis zachodzi gdy ostatnie bicie nastapilo 15 ruchow temu
    }

    /**
     * determines one-round actions that is, checks which player is next and whether
     * there is a winner.
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
        //ask user for end position. sekwencja ruchów. max liczba ruchow to liczba pionkow przeciwnika
        //map inserted data to Coordinates class
        //use Pawn's method to validate:
        //  - if starting position is valid pawn (if at selected coordinates there is pawn, and it colors matches player)
        //  - if end position is within boundaries (is separate method needed? if so should it be implemented inside Board?)
        // if it is valid call tryToMakeMove()
        // }while(!tryToMakeMove()) "masz do wykonania lepszy ruch"
        //etap 1:
        //  zapytaj o pierwsze wspolrzedne
        //etap 2:
        //  zapytaj o liste wspolrzednych
    }

    /**
     * There is a method that checks if the starting position from user input
     * is a valid pawn and if the ending position is within board boundaries.
     * If so, it calls tryToMakeMove() on pawn instance.
     * @return true if move is possible and executed, otherwise false.
     */
    public boolean tryToMakeMove(Pawn pawn, Coordinates movePosition){
        //try to move pawn according to game rules (Pawn.validateMove())
        //if (Pawn.validateMove()){
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
        return false;
    }

}
