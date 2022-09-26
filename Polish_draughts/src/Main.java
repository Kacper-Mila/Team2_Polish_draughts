public class Main {
    public static void main(String[] args) {
        int number = 10;
        Pawn[][] fields;

        Board board = new Board(number);
        fields = board.getFields();
        for (int i = 0; i < number;  i++) {
            System.out.println();
            for(int j = 0; j < number; j++) {
                System.out.print("\t " + fields[i][j] + " ");
            }
        }
    }
}