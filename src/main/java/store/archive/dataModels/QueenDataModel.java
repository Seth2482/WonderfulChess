package store.archive.dataModels;

import controller.ClickController;
import model.*;
import store.archive.ChessType;
import view.Chessboard;
import view.ChessboardPoint;

import java.awt.*;

public class QueenDataModel extends ChessDataModel{

    public QueenDataModel(int x, int y, ChessColor chessColor) {
        super(x, y, chessColor);
        chessType = ChessType.Queen;
    }

    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new QueenChessComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x,y), chessColor, chessboard.getClickController(), chessboard.getCHESS_SIZE());
    }
}
