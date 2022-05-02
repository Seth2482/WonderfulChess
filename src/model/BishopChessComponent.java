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

    private Image bishopImage;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./resource/images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./resource/images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
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

        int rowDifference = source.getX() - destination.getX();
        int columnDifference = source.getY() - destination.getY();

        if (Math.abs(rowDifference) == Math.abs(columnDifference)) {
            if (rowDifference > 0 && columnDifference > 0) {
                for (int i = 1; i < rowDifference; i++) {
                    if (!(chessComponents[destination.getX() + i][destination.getY() + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }

            if (rowDifference < 0 & columnDifference < 0) {
                for (int i = 1; i < -rowDifference; i++) {
                    if (!(chessComponents[source.getX() + i][source.getY() + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }

            if (rowDifference > 0 && columnDifference < 0) {
                for (int i = 1; i < rowDifference; i++) {
                    if (!(chessComponents[destination.getX() + i][destination.getY() - i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }

            if (rowDifference < 0 && columnDifference > 0) {
                for (int i = 1; i < columnDifference; i++) {
                    if (!(chessComponents[destination.getX() - i][destination.getY() + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }

    }
}