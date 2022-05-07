package Archive.DataModel;

import Archive.ChessType;
import Model.ChessColor;
import Model.ChessComponent;
import Model.RookChessComponent;
import View.Chessboard;
import View.ChessboardPoint;

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
