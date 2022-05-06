package store.archive.dataModels;

import controller.ClickController;
import model.BishopChessComponent;
import model.ChessColor;
import model.ChessComponent;
import model.EmptySlotComponent;
import store.archive.ChessType;
import view.Chessboard;
import view.ChessboardPoint;

import java.awt.*;

public class EmptyDataModel extends ChessDataModel {

    public EmptyDataModel(int x, int y, ChessColor chessColor) {
        super(x, y, chessColor);
        chessType = ChessType.Empty;
    }


    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new EmptySlotComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x, y), chessboard.getClickController(), chessboard.getCHESS_SIZE());
    }
}
