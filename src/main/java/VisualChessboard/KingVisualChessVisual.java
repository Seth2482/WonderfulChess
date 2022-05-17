package VisualChessboard;

import java.util.ArrayList;
import java.util.List;

public class KingVisualChessVisual extends ChessComponentVisual {

    @Override
    public List<ChessboardPointVisual> canMoveTo() {
        List<ChessboardPointVisual> chessboardPointVisuals = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((Math.abs(this.getSource().getX() - i) == 1 && this.getSource().getY() == j) || (Math.abs(this.getSource().getY() - j) == 1 && this.getSource().getX() == i) || (Math.abs(this.getSource().getX() - i) == Math.abs(this.getSource().getY() - j) && Math.abs(this.getSource().getX() - i) == 1)) {
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
        return getChessColor() == ChessColorVisual.WHITE ? "k" : "K";
    }

    @Override
    public void giveValueTo(ChessComponentVisual target) {
        target = new KingVisualChessVisual();
        target.setChessColor(this.getChessColor());
        target.setSource(new ChessboardPointVisual(this.getSource().getX(), this.getSource().getY()));
    }
}
