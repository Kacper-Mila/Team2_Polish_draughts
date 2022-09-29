package draughts;

import java.awt.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

    private Board board;
    private int drawCondition = 15;
    private int sizeBoard;
    public Game(int sizeBoard){

        this.board = new Board(sizeBoard);
        this.sizeBoard = sizeBoard;
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
    public boolean checkForWinner(int player) {
        //jesli pionek 3 razy na jednym polu to remis
        if (drawCondition == 0) {
            System.out.println("It's draw! Game over! ");
            return false;
        }
        if(player == 1 && board.getBlackPawnsCounter() ==0){
          return true;
        } else return player == 2 && board.getWhitePawnsCounter() == 0;
    }

    /**
     * There is a method that checks if the starting position from user input
     * is a valid pawn and if the ending position is within board boundaries.
     * If so, it calls tryToMakeMove() on pawn instance.
     */
    public boolean isValidCoordinate(String inputCoordinate){
        Pattern r = Pattern.compile("(?i)([a-z])(\\d+)");
        Matcher m;
        m = r.matcher(inputCoordinate);

         if(m.matches()) return true;
         else return false;
    }
    public void checkStartingPosition(int player){
        Scanner scanner = new Scanner(System.in);
        String startCoordinate;
        String endCoordinate;
        int startColAsNumber;
        int startRowAsNumber;
        int endColAsNumber;
        int endRowAsNumber;
        char lastCol=(char)(sizeBoard + (int)'a' -1);
        String regex= String.format("(?i)[a-%s]", String.valueOf(lastCol));
        Coordinates newPosition;
        //sprawdzam poprawnosc poczatkowych wspolrzednych
        do {
            do {
                System.out.println("Podaj współrzędne pionka, którym chcesz wykonać ruch, np. A1");
                startCoordinate = scanner.nextLine();
                if (!isValidCoordinate(startCoordinate)) {
                    System.out.println("Niepoprawne współrzędne");
                    continue;
                }

                String startCol = startCoordinate.substring(0, 1);
                int startRow = Integer.parseInt(startCoordinate.substring(1));
                startColAsNumber = ((int) (startCol.toUpperCase().charAt(0))) - (int) 'A';
                startRowAsNumber = startRow - 1;
                if (!startCol.matches(regex) || startRow <= 0 || startRow > sizeBoard) {
                    System.out.println("Współrzędne poza zakresem");
                    continue;
                }
                if ((board.getFields()[startRowAsNumber][startColAsNumber] == null)) {
                    System.out.println("To pole jest puste, wybierz pole z Twoim pionkiem");
                    continue;
                }
                if ((player == 1 && board.getFields()[startRowAsNumber][startColAsNumber].getColor() != Color.WHITE) ||
                        (player == 2 && board.getFields()[startRowAsNumber][startColAsNumber].getColor() != Color.BLACK)) {
                    System.out.println("To jest pionek przeciwnika, wybierz swój pionek");
                    continue;
                }

                break;
            } while (true);

            //sprawdzam poprawnosc koncowych wspolrzednych
            do {
                System.out.println("Podaj współrzędne pola, na które chcesz wykonać ruch, np. A1");
                endCoordinate = scanner.nextLine();
                if (!isValidCoordinate(endCoordinate)) {
                    System.out.println("Niepoprawne współrzędne");
                    continue;
                }
                String endCol = endCoordinate.substring(0, 1);
                int endRow = Integer.parseInt(endCoordinate.substring(1));
                endColAsNumber = ((int) (endCol.toUpperCase().charAt(0))) - (int) 'A';
                endRowAsNumber = endRow - 1;
                if (!endCol.matches(regex) || endRow <= 0 || endRow > sizeBoard) {
                    System.out.println("Współrzędne poza zakresem");
                    continue;
                }
                break;
            } while (true);

            newPosition = new Coordinates(endRowAsNumber, endColAsNumber);
        } while( tryToMakeMove(board.getFields()[startRowAsNumber][startColAsNumber], newPosition));
        //TODO
        //etap 2:
        //zapytaj o liste wspolrzednych
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
