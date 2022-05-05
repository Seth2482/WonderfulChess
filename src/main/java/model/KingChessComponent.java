package model;

import controller.ClickController;
import model.ChessColor;
import model.ChessComponent;
import model.EmptySlotComponent;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class KingChessComponent extends ChessComponent {
    private static Image KING_WHITE;
    private static Image KING_BLACK;

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

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKingImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();

        int rowDifference = destination.getY() - source.getY();
        int columnDifference = destination.getX() - source.getX();

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
}
