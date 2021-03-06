package Model;

import View.ChessboardPoint;
import Controller.ClickController;

import java.awt.*;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的车
 */
public class RookChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image ROOK_WHITE;
    private static Image ROOK_BLACK;
    private boolean canBeChangeKing = true;
    private boolean hasMoved = false;

    public RookChessComponent() {

    }

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (ROOK_WHITE == null) {
            ROOK_WHITE = getImage("images/rook-white.png");
        }

        if (ROOK_BLACK == null) {
            ROOK_BLACK = getImage("images/rook-black.png");
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                trueImage = ROOK_WHITE;
            } else if (color == ChessColor.BLACK) {
                trueImage = ROOK_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RookChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size, boolean canBeChangeKing, boolean hasMoved) {
        super(chessboardPoint, location, color, listener, size);
        this.hasMoved = hasMoved;
        this.canBeChangeKing = canBeChangeKing;
        initiateRookImage(color);
    }

    public RookChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        this(chessboardPoint, location, chessColor, clickController, size, true, false);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else { // Not on the same row or the same column.
            return false;
        }
        return true;
    }

    @Override
    public void swapLocation(ChessComponent another) {
        super.swapLocation(another);
        hasMoved = true;
    }

    public boolean isCanBeChangeKing() {
        return canBeChangeKing;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }
}
