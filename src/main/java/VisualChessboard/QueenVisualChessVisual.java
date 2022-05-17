package VisualChessboard;

import java.util.ArrayList;
import java.util.List;

public class QueenVisualChessVisual extends ChessComponentVisual {
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

        if (getSource().getX() != 0) {
            for (int i = getSource().getX() - 1; i >= 0; i--) {
                if (getChesses()[i][getSource().getY()] instanceof EmptySlotVisual) {
                    chessboardPointVisuals.add(new ChessboardPointVisual(i, getSource().getY()));
                } else {
                    if (getChesses()[i][getSource().getY()].getChessColor() != getChessColor()) {
                        chessboardPointVisuals.add(new ChessboardPointVisual(i, getSource().getY()));
                        break;
                    }
                    break;
                }
            }
        }


        for (int i = getSource().getX() + 1; i <= 7; i++) {
            if (getChesses()[i][getSource().getY()] instanceof EmptySlotVisual) {
                chessboardPointVisuals.add(new ChessboardPointVisual(i, getSource().getY()));
            } else {
                if (getChesses()[i][getSource().getY()].getChessColor() != getChessColor()) {
                    chessboardPointVisuals.add(new ChessboardPointVisual(i, getSource().getY()));
                    break;
                }
                break;
            }
        }


        for (int i = getSource().getY() - 1; i >= 0; i--) {
            if (getChesses()[getSource().getX()][i] instanceof EmptySlotVisual) {
                chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX(), i));
            } else {
                if (getChesses()[getSource().getX()][i].getChessColor() != getChessColor()) {
                    chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX(), i));
                    break;
                }
                break;
            }
        }


        if (getSource().getY() != 7) {
            for (int i = getSource().getY() + 1; i <= 7; i++) {
                if (getChesses()[getSource().getX()][i] instanceof EmptySlotVisual) {
                    chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX(), i));
                } else {
                    if (getChesses()[getSource().getX()][i].getChessColor() != getChessColor()) {
                        chessboardPointVisuals.add(new ChessboardPointVisual(getSource().getX(), i));
                        break;
                    }
                    break;
                }
            }
        }

        return chessboardPointVisuals;
    }

    @Override
    public String toString() {
        return getChessColor() == ChessColorVisual.WHITE ? "q" : "Q";
    }

    @Override
    public void giveValueTo(ChessComponentVisual target) {
        target = new QueenVisualChessVisual();
        target.setChessColor(this.getChessColor());
        target.setSource(new ChessboardPointVisual(this.getSource().getX(),this.getSource().getY()));
    }


}
