import java.awt.*;

public class Pawn {

    public Color getColor(int player) {
        if (player == 1) {
            return Color.white;
        } else {
            return Color.black;
        }
    }
}
