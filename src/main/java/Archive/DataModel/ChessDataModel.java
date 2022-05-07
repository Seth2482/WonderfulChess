package Archive.DataModel;


import Model.ChessColor;
import Model.ChessComponent;
import Archive.ChessType;
import View.Chessboard;

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
