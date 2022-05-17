package Controller;


import Model.*;
import View.ChessGameFrame;
import View.Chessboard;
import View.ChessboardPoint;

import javax.swing.*;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            // 人机模式时 屏蔽对黑棋的操作
            if ((chessboard.getGameMode() == GameMode.PVEEasy || chessboard.getGameMode() == GameMode.PVEHard)
                    && chessComponent.getChessColor() == ChessColor.BLACK) {
                return;
            }

            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                ChessGameFrame.getRestartButton().setEnabled(false);
                for (int i = 0; i < 8; i++) {//遍历哪一个can move to
                    for (int j = 0; j < 8; j++) {
                        if ((first.canMoveTo(chessboard.getChessComponents(), new ChessboardPoint(i, j)))
                                && (!chessboard.getChessComponents()[i][j].getChessColor().equals(first.getChessColor()))) {
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
                ChessGameFrame.getRestartButton().setEnabled(true);

                for (int i = 0; i < 8; i++) {//遍历哪一个can move to
                    for (int j = 0; j < 8; j++) {
                        if (chessboard.getChessComponents()[i][j].isCanBeMoveTo()) {
                            chessboard.getChessComponents()[i][j].setCanBeMoveTo(false);
                            chessboard.getChessComponents()[i][j].repaint();
                        }
                    }
                }
            } else if (handleSecond(chessComponent) && !chessComponent.equals(first)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);

                chessboard.swapColor();


                first.setSelected(false);
                first = null;
                ChessGameFrame.getRestartButton().setEnabled(true);

                for (int i = 0; i < 8; i++) {//遍历哪一个can move to
                    for (int j = 0; j < 8; j++) {
                        if (chessboard.getChessComponents()[i][j].isCanBeMoveTo()) {
                            chessboard.getChessComponents()[i][j].setCanBeMoveTo(false);
                            chessboard.getChessComponents()[i][j].repaint();
                        }
                    }
                }
                if (chessboard.getGameMode() != GameMode.PVP) {
                    //TODO:: 底线升变时要延迟执行 以及游戏结束后就不要再行棋了
                    ChessGameFrame.getInstance().setActionButtonsEnabled(false);
                    Timer timer = new Timer(2000, e -> {

                        chessboard.AIMove();
                        ChessGameFrame.getInstance().setActionButtonsEnabled(true);
                    });
                    timer.setRepeats(false);
                    timer.start();

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

