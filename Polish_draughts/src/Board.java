public class Board {
    public void removePawn(Pawn pawn, Coordinates coordinates){
        if(this.fielsds[coordinates.getX()][coordinates.getY()].equals(pawn)){
            fields[coordinates.getX()][coordinates.getY] = null;
        } else{
            System.out.println("this Pawn is not in that position");
        }
    }
}
