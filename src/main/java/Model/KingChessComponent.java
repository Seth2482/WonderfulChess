package Model;

import Controller.ClickController;
import View.ChessboardPoint;

import java.awt.*;
import java.io.IOException;

public class KingChessComponent extends ChessComponent {
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private boolean canBeChangeRook = true;
    private boolean hasMoved = false;

    public KingChessComponent() {

    }


    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = getImage("images/king-white.png");
        }

        if (KING_BLACK == null) {
            KING_BLACK = getImage("images/king-black.png");
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                trueImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                trueImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size, boolean canBeChangeRook, boolean hasMoved) {
        super(chessboardPoint, location, color, listener, size);
        this.canBeChangeRook = canBeChangeRook;
        this.hasMoved = hasMoved;
        initiateKingImage(color);
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        this(chessboardPoint, location, chessColor, clickController, size, true, false);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();

        int rowDifference = destination.getY() - source.getY();
        int columnDifference = destination.getX() - source.getX();

        boolean whiteKingSatisfyRequest = chessComponents[7][4] instanceof KingChessComponent
                && chessComponents[7][4].getChessColor() == ChessColor.WHITE
                && !((KingChessComponent) chessComponents[7][4]).isHasMoved();

        boolean whiteLeftRookSatisfyRequest = chessComponents[7][0] instanceof RookChessComponent
                && chessComponents[7][0].getChessColor() == ChessColor.WHITE
                && !((RookChessComponent) chessComponents[7][0]).isHasMoved();

        boolean whiteLeftEmptySatisfyRequest = chessComponents[7][2] instanceof EmptySlotComponent
                && chessComponents[7][3] instanceof EmptySlotComponent
                && chessComponents[7][1] instanceof EmptySlotComponent;

        boolean whiteRightRookSatisfyRequest = chessComponents[7][7] instanceof RookChessComponent
                && chessComponents[7][7].getChessColor() == ChessColor.WHITE
                && !((RookChessComponent) chessComponents[7][7]).isHasMoved();

        boolean whiteRightEmptySatisfyRequest = chessComponents[7][5] instanceof EmptySlotComponent
                && chessComponents[7][6] instanceof EmptySlotComponent;

        if (whiteKingSatisfyRequest && whiteLeftEmptySatisfyRequest && whiteLeftRookSatisfyRequest) {
            if (destination.getX() == 7 && destination.getY() == 2) {
                return true;
            }
        }
        if (whiteKingSatisfyRequest && whiteRightEmptySatisfyRequest && whiteRightRookSatisfyRequest) {
            if (destination.getX() == 7 && destination.getY() == 6) {
                return true;
            }
        }

        boolean blackKingSatisfyRequest = chessComponents[0][4] instanceof KingChessComponent
                && chessComponents[0][4].getChessColor() == ChessColor.BLACK
                && !((KingChessComponent) chessComponents[0][4]).isHasMoved();

        boolean blackLeftRookSatisfyRequest = chessComponents[0][0] instanceof RookChessComponent
                && chessComponents[0][0].getChessColor() == ChessColor.BLACK
                &&!((RookChessComponent) chessComponents[0][0]).isHasMoved();
        boolean blackLeftEmptySatisfyRequest = chessComponents[0][2] instanceof EmptySlotComponent
                && chessComponents[0][3] instanceof EmptySlotComponent
                && chessComponents[0][1] instanceof EmptySlotComponent;

        boolean blackRightRookSatisfyRequest = chessComponents[0][7] instanceof RookChessComponent
                && chessComponents[0][7].getChessColor() == ChessColor.BLACK
                && !((RookChessComponent) chessComponents[0][7]).isHasMoved();

        boolean blackRightEmptySatisfyRequest = chessComponents[0][5] instanceof EmptySlotComponent
                && chessComponents[0][6] instanceof EmptySlotComponent;

        if (blackKingSatisfyRequest && blackLeftEmptySatisfyRequest && blackLeftRookSatisfyRequest) {
            if (destination.getX() == 0 && destination.getY() == 2) {
                return true;
            }
        }
        if (blackKingSatisfyRequest && blackRightEmptySatisfyRequest && blackRightRookSatisfyRequest) {
            if (destination.getX() == 0 && destination.getY() == 6) {
                return true;
            }
        }

        // 检查是否在对角线上
        if (Math.abs(rowDifference) == Math.abs(columnDifference) && Math.abs(columnDifference) == 1) {

            // 检查是否有拦路棋
            int xFactor = columnDifference > 0 ? 1 : -1;
            int yFactor = rowDifference > 0 ? 1 : -1;
            for (int i = 1; i < Math.abs(rowDifference); i++) {
                if (!(chessComponents[source.getX() + xFactor * i][source.getY() + yFactor * i] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return true;
        }

        if (source.getX() == destination.getX() && Math.abs(rowDifference) == 1) {
            return true;
        } else return source.getY() == destination.getY() && Math.abs(columnDifference) == 1;
    }

    @Override
    public void swapLocation(ChessComponent another) {
        super.swapLocation(another);
        hasMoved = true;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public boolean isCanBeChangeRook() {
        return canBeChangeRook;
    }
}
