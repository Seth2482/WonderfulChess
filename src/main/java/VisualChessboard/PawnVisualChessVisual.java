package VisualChessboard;

import java.util.ArrayList;
import java.util.List;

public class PawnVisualChessVisual extends ChessComponentVisual {
    @Override
    public List<ChessboardPointVisual> canMoveTo() {
        int factor = getChessColor() == ChessColorVisual.WHITE ? -1 : 1;

        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return getChessColor() == ChessColorVisual.WHITE ? "p" : "P";
    }

    @Override
    public void giveValueTo(ChessComponentVisual target) {
        target = new PawnVisualChessVisual();
        target.setChessColor(this.getChessColor());
        target.setSource(new ChessboardPointVisual(this.getSource().getX(), this.getSource().getY()));
    }
}
