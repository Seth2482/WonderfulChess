package store.archive.dataModels;

import controller.ClickController;
import model.BishopChessComponent;
import model.ChessColor;
import model.ChessComponent;
import model.KnightChessComponent;
import store.archive.ChessType;
import view.Chessboard;
import view.ChessboardPoint;

import java.awt.*;

public class KnightDataModel extends ChessDataModel{

    public KnightDataModel(int x, int y, ChessColor chessColor) {
        super(x, y, chessColor);
        chessType = ChessType.Knight;
    }

    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new KnightChessComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x,y), chessColor, chessboard.getClickController(), chessboard.getCHESS_SIZE());
    }
}
