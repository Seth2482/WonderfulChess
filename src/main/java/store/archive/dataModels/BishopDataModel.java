package store.archive.dataModels;

import controller.ClickController;
import model.BishopChessComponent;
import model.ChessColor;
import model.ChessComponent;
import store.archive.ChessType;
import view.Chessboard;
import view.ChessboardPoint;

import java.awt.*;

public class BishopDataModel extends ChessDataModel {

    public BishopDataModel(int x, int y, ChessColor chessColor) {
        super(x, y, chessColor);
        chessType = ChessType.Bishop;
    }

    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new BishopChessComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x, y), chessColor, chessboard.getClickController(), chessboard.getCHESS_SIZE());
    }
}
