package VisualChessboard;

import java.util.ArrayList;
import java.util.List;

public class BishopVisualChessVisual extends ChessComponentVisual {
    @Override
    public List<ChessboardPointVisual> canMoveTo() {
        List<ChessboardPointVisual> chessboardPointVisuals = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            if (getSource().getX() + i <= 7 && getSource().getY() + i <= 7) {
                if (getChesses()[getSource().getX() + i][getSource().getY() + i] instanceof EmptySlotVisual) {
                    chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() + i, getSource().getY() + i));
                } else {
                    if (getChesses()[getSource().getX() + i][getSource().getY() + i].getChessColor() != this.getChessColor()) {
                        chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() + i, getSource().getY() + i));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (getSource().getX() - i >= 0 && getSource().getY() - i >= 0) {
                if (getChesses()[getSource().getX() - i][getSource().getY() - i] instanceof EmptySlotVisual) {
                    chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() - i, getSource().getY() - i));
                } else {
                    if (getChesses()[getSource().getX() - i][getSource().getY() - i].getChessColor() != this.getChessColor()) {
                        chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() - i, getSource().getY() - i));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        for (int i = 1; i <= 7; i++) {
            if (getSource().getX() - i >= 0 && getSource().getY() + i <= 7) {
                if (getChesses()[getSource().getX() - i][getSource().getY() + i] instanceof EmptySlotVisual) {
                    chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() - i, getSource().getY() + i));
                } else {
                    if (getChesses()[getSource().getX() - i][getSource().getY() + i].getChessColor() != this.getChessColor()) {
                        chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() - i, getSource().getY() + i));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        for (int i = 1; i <= 7; i++) {
            if (getSource().getX() + i <= 7 && getSource().getY() - i >= 0) {
                if (getChesses()[getSource().getX() + i][getSource().getY() - i] instanceof EmptySlotVisual) {
                    chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() + i, getSource().getY() - i));
                } else {
                    if (getChesses()[getSource().getX() + i][getSource().getY() - i].getChessColor() != this.getChessColor()) {
                        chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX() + i, getSource().getY() - i));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        return chessboardPointVisuals;
    }

    @Override
    public String toString() {
        return getChessColor() == ChessColorVisual.WHITE ? "b" : "B";
    }

    @Override
    public void giveValueTo(ChessComponentVisual target) {
        target = new BishopVisualChessVisual();
        target.setChessColor(this.getChessColor());
        target.setSource(new ChessboardPointVisual(this.getSource().getX(), this.getSource().getY()));
    }
}
