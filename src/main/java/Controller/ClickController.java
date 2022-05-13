package Controller;


import Model.ChessColor;
import Model.ChessComponent;
import Model.KingChessComponent;
import Model.PawnChessComponent;
import View.Chessboard;
import View.ChessboardPoint;
import View.Dialog.KingAttackedDialog;
import View.Dialog.LoseDialog;

import javax.swing.*;
import java.awt.*;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;

                for (int i = 0; i < 8; i++) {//遍历哪一个can move to
                    for (int j = 0; j < 8; j++) {
                        if ((first.canMoveTo(chessboard.getChessComponents(), new ChessboardPoint(i, j))) && (!chessboard.getChessComponents()[i][j].getChessColor().equals(first.getChessColor()))) {
                            chessboard.getChessComponents()[i][j].setCanBeMoveTo(true);
                            chessboard.getChessComponents()[i][j].repaint();
                        }
                    }
                }

                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();

                for (int i = 0; i < 8; i++) {//遍历哪一个can move to
                    for (int j = 0; j < 8; j++) {
                        if (chessboard.getChessComponents()[i][j].isCanBeMoveTo()) {
                            chessboard.getChessComponents()[i][j].setCanBeMoveTo(false);
                            chessboard.getChessComponents()[i][j].repaint();
                        }
                    }
                }
            } else if (handleSecond(chessComponent)) {
                if (first instanceof PawnChessComponent) {
                    if (((PawnChessComponent) first).isTheFirstMove()) {//Todo:: 吃过路兵的判断
                        if (Math.abs(((PawnChessComponent) first).getChessboardPoint().getX() - chessComponent.getChessboardPoint().getX()) == 2) {
                            ((PawnChessComponent) first).setCanBeEnAsPassant(true);
                        }
                        ((PawnChessComponent) first).setTheFirstMove(false);
                    }
                }
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);

                if (first instanceof PawnChessComponent && first.getChessboardPoint().getX() == 0 && first.getChessColor() == ChessColor.WHITE) {
                    ((PawnChessComponent) first).showDialog();
                }
                if (first instanceof PawnChessComponent && first.getChessboardPoint().getX() == 7 && first.getChessColor() == ChessColor.BLACK) {
                    ((PawnChessComponent) first).showDialog();
                }


                chessboard.swapColor();


                first.setSelected(false);
                first = null;

                for (int i = 0; i < 8; i++) {//遍历哪一个can move to
                    for (int j = 0; j < 8; j++) {
                        if (chessboard.getChessComponents()[i][j].isCanBeMoveTo()) {
                            chessboard.getChessComponents()[i][j].setCanBeMoveTo(false);
                            chessboard.getChessComponents()[i][j].repaint();
                        }
                    }
                }
            }
        }
        chessboard.scanTheChessboard();
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
}

