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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public ChessType getChessType() {
        return chessType;
    }

    public abstract ChessComponent toChessComponent(Chessboard chessboard);
}
