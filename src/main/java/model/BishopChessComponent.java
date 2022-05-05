package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BishopChessComponent extends ChessComponent {
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = getImage("images/bishop-white.png");
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = getImage("images/bishop-black.png");
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                trueImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                trueImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }


    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        boolean canGetThere = true;

        int rowDifference = destination.getY() - source.getY();
        int columnDifference = destination.getX() - source.getX();

        // 检查是否在对角线上
        if (Math.abs(rowDifference) == Math.abs(columnDifference)) {

            // 检查是否有拦路棋
            int xFactor = columnDifference > 0 ? 1 : -1;
            int yFactor = rowDifference > 0 ? 1 : -1;
            for (int i = 1; i < Math.abs(rowDifference); i++) {
                if (!(chessComponents[source.getX() + xFactor * i][source.getY() + yFactor * i] instanceof EmptySlotComponent)) {
                    canGetThere = false;
                }
            }
        } else {
            canGetThere = false;
        }

        return canGetThere;
    }
}