package draughts;

public class Pawn {

    private Coordinates position;

    //The Pawn class contains a method that validates the move (whether it is within the game rules) before it is performed.
    public boolean validateMove(int x, int y, Board board){
        boolean repeat = true;
        int[] move = {x, y};
        Pawn [][] fields = board.getFields();

        while(repeat){
            if (x < 0 || x >= fields.length){
                System.out.println("Incorrect coordinates");
                continue;
            }
            if (y < 0 || y >= fields[0].length){
                System.out.println("Incorrect coordinates");
                continue;
            }
            if (fields[x][y] != null){
                System.out.println("Position already taken");
                continue;
            }
//            if (move[0]) {
//
//            }
            repeat = false;
        }
        return true;
    }

    /**
     * method that returns the color of the pawn(white or black).
     * @return colors as numbers??? eg white = 0, black =1
     */
    public int[] getColor(){
        return new int [0];
    }

    private class Coordinates {
        int x;
        int y;
    }

    /**
     * [Extra]
     * 'field' that returns true if a pawn is crowned.
     * @return
     */
    public boolean isCrowded(){
        return false;
    }

}
