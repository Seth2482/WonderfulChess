package Model;

import Controller.ClickController;
import View.ChessboardPoint;

import java.awt.*;
import java.io.IOException;

public class QueenChessComponent extends ChessComponent {
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    public QueenChessComponent() {

    }

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = getImage("images/queen-white.png");
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = getImage("images/queen-black.png");
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                trueImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                trueImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();

        int rowDifference = destination.getY() - source.getY();
        int columnDifference = destination.getX() - source.getX();

        // 检查是否在对角线上
        if (Math.abs(rowDifference) == Math.abs(columnDifference)) {

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

        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return true;
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
