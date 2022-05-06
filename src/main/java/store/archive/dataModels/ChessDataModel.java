package store.archive.dataModels;


import controller.ClickController;
import model.ChessColor;
import model.ChessComponent;
import model.PawnChessComponent;
import store.archive.ChessType;
import view.Chessboard;

import java.awt.*;

public abstract class ChessDataModel {
    int x, y;
    ChessColor chessColor;
    ChessType chessType;

    public ChessDataModel(int x, int y, ChessColor chessColor) {
        this.x = x;
        this.y = y;
        this.chessColor = chessColor;
    }


    public abstract ChessComponent toChessComponent(Chessboard chessboard);
}
