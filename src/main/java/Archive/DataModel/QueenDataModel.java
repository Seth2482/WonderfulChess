package Archive.DataModel;

import Archive.ChessType;
import Model.*;
import View.Chessboard;
import View.ChessboardPoint;

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
