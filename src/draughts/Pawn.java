package draughts;

public class Pawn {

    private Coordinates position;
    private int color;
    private boolean isCrowned;

    public Pawn(Coordinates position, int color ){
        this.color = color;
        this.position = position;
    }

    //The Pawn class contains a method that validates the move (whether it is within the game rules) before it is performed.
    // missing other rules. Chapter 'Moves and captures' from https://en.wikipedia.org/wiki/International_draughts
    // separate method for each condition all merged inside one??
    public boolean validateMove(Coordinates startPosition, Coordinates endPosition, Board board){
        boolean repeat = true;
        //int[] move = {x, y};
        int x = startPosition.getX();
        int y = startPosition.getY();
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
    public int getColor(){
        return 0;
    }

    /**
     * [Extra]
     * 'field' that returns true if a pawn is crowned.
     * @return
     */
    public boolean isCrowned(){
        return this.isCrowned;
    }

    public void setCrowned(){
        this.isCrowned = true;
    }

}
