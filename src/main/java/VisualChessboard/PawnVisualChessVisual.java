package VisualChessboard;

import java.util.ArrayList;
import java.util.List;

public class PawnVisualChessVisual extends ChessComponentVisual {
    private boolean firstMove = true;

    @Override
    public List<ChessboardPointVisual> canMoveTo() {
        List<ChessboardPointVisual> chessboardPointVisuals = new ArrayList<>();

        int factor = getChessColor() == ChessColorVisual.WHITE ? -1 : 1;

        if (firstMove) {
            chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() + factor, getSource().getY()));
            chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() + 2 * factor, getSource().getY()));
        } else {
            chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() + factor, getSource().getY()));
        }

        return chessboardPointVisuals;
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
