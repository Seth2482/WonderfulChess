package store.archive.dataModels;

import controller.ClickController;
import model.BishopChessComponent;
import model.ChessColor;
import model.ChessComponent;
import model.RookChessComponent;
import store.archive.ChessType;
import view.Chessboard;
import view.ChessboardPoint;

import java.awt.*;

public class RookDataModel extends ChessDataModel {

    public RookDataModel(int x, int y, ChessColor chessColor) {
        super(x, y, chessColor);
        chessType = ChessType.Rook;
    }

    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new RookChessComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x, y), chessColor, chessboard.getClickController(), chessboard.getCHESS_SIZE());
    }
}
