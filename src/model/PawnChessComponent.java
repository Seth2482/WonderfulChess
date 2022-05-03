package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PawnChessComponent extends ChessComponent {
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;

    private final ChessColor thisChessColor;//record the color of chess;
    private boolean isTheFirstMove = true;//是否是第一步
    private boolean canBeEnAsPassant = false;//能否被别人当作过路兵吃掉

    public boolean isCanBeEnAsPassant() {
        return canBeEnAsPassant;
    }

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./resource/images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./resource/images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                trueImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                trueImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        thisChessColor = color;
        initiatePawnImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int factor = thisChessColor == ChessColor.WHITE ? 1 : -1;

        if (isTheFirstMove) {
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) && (destination.getX() == source.getX() - factor) && (destination.getY() == source.getY() + 1)) {
                return true;
            }
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) && (destination.getX() == source.getX() - factor) && (destination.getY() == source.getY() - 1)) {
                return true;
            }//斜着吃子

            if (source.getX() - destination.getX() == factor && source.getY() == destination.getY()) {
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                    return false;
                }
                isTheFirstMove = false;
                return true;
            }

            if (source.getX() - destination.getX() == 2 * factor && source.getY() == destination.getY()) {
                if (!(chessComponents[source.getX() - factor][source.getY()] instanceof EmptySlotComponent)) {
                    return false;
                }
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                    return false;
                }
                isTheFirstMove = false;
                canBeEnAsPassant = true;
                return true;
            }
            return false;
        } else {
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) && (destination.getX() == source.getX() - factor) && (destination.getY() == source.getY() + 1)) {
                return true;
            }
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) && (destination.getX() == source.getX() - factor) && (destination.getY() == source.getY() - 1)) {
                return true;
            }//斜着吃子
            if (source.getY() != chessComponents.length - 1) {
                if (chessComponents[source.getX()][source.getY() + 1] instanceof PawnChessComponent && destination.getX() == source.getX() - factor && destination.getY() == source.getY() + 1 ) {
                    if ( ((PawnChessComponent) chessComponents[source.getX()][source.getY() + 1]).canBeEnAsPassant){
                        return true;
                    }
                }
            }

            if (source.getY() != 0) {
                if (chessComponents[source.getX()][source.getY() - 1] instanceof PawnChessComponent && destination.getX() == source.getX() - factor && destination.getY() == source.getY() - 1 ) {
                    if (((PawnChessComponent) chessComponents[source.getX()][source.getY() - 1]).canBeEnAsPassant){
                        return true;
                    }
                }
            }//吃过路兵
            return source.getX() - destination.getX() == factor && source.getY() == destination.getY()&&(chessComponents[source.getX() - factor][source.getY()] instanceof EmptySlotComponent);
        }
    }
}




