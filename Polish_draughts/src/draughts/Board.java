package draughts;

public class Board {
    public void removePawn(Pawn pawn, Coordinates coordinates){
        if(this.fielsds[coordinates.getX()][coordinates.getY()].equals(pawn)){
            fields[coordinates.getX()][coordinates.getY] = null;
        } else{
            System.out.println("this Pawn is not in that position");
        }
    }
    private Pawn[][] fields;
    public Board(int n) {
        if (n >= 10 && n <= 20) {
            this.fields = new Pawn[n][n];
        }
        // one side of the board
        int numberOfPawns = 2 * n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j=j+2) {
                Pawn pawn = new Pawn();
                if (i % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[i][j + 1] = pawn;
                        numberOfPawns--;
                    }
                } else {
                    if (numberOfPawns > 0) {
                        this.fields[i][j] = pawn;
                        numberOfPawns--;
                    }
                }
            }
        }

        // other side of the board
        numberOfPawns = 2 * n;
        for (int i = n-1; i > n-5; i--) {
            for (int j = 0; j < n; j=j+2) {
                Pawn pawn = new Pawn();
                if (i % 2 == 0) {
                    if (numberOfPawns > 0) {
                        this.fields[i][j + 1] = pawn;
                        numberOfPawns--;
                    }
                } else {
                    if (numberOfPawns > 0) {
                        this.fields[i][j] = pawn;
                        numberOfPawns--;
                    }
                }
            }
        }
    }

    public Pawn[][] getFields() {
        return fields;
    }

    public void setFields(Pawn[][] fields) {
        this.fields = fields;
    }
}
