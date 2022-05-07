package Archive.DataModel;

import Archive.ChessType;
import Model.ChessColor;
import Model.ChessComponent;
import Model.KnightChessComponent;
import View.Chessboard;
import View.ChessboardPoint;

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
