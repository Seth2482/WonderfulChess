package Model;

import Controller.ClickController;
import View.ChessboardPoint;

import java.awt.*;
import java.io.IOException;

public class KnightChessComponent extends ChessComponent {
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;

    public KnightChessComponent() {

    }

    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = getImage("images/knight-white.png");
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = getImage("images/knight-black.png");
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                trueImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                trueImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        // Not on the same row or the same column.
        return Math.pow(Math.abs(source.getX() - destination.getX()), 2) + Math.pow(Math.abs(source.getY() - destination.getY()), 2) == 5;
    }
}
