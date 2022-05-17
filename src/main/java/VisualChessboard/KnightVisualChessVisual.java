package VisualChessboard;

import java.util.ArrayList;
import java.util.List;

public class KnightVisualChessVisual extends ChessComponentVisual {
    @Override
    public List<ChessboardPointVisual> canMoveTo() {
        List<ChessboardPointVisual> chessboardPointVisuals = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Math.pow((Math.abs(this.getSource().getX() - i)), 2) + Math.pow((Math.abs(this.getSource().getY() - j)), 2) == 5) {
                    if (getChesses()[i][j].getChessColor() != this.getChessColor()) {
                        chessboardPointVisuals.add(new ChessboardPointVisual(i, j));
                    }
                }
            }
        }
        return chessboardPointVisuals;
    }

    @Override
    public String toString() {
        return getChessColor() == ChessColorVisual.WHITE ? "n" : "N";
    }

    @Override
    public void giveValueTo(ChessComponentVisual target) {
        target = new KnightVisualChessVisual();
        target.setChessColor(this.getChessColor());
        target.setSource(new ChessboardPointVisual(this.getSource().getX(), this.getSource().getY()));
    }
}
