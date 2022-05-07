package Archive.DataModel;

import Archive.ChessType;
import Model.ChessColor;
import Model.ChessComponent;
import Model.KingChessComponent;
import View.Chessboard;
import View.ChessboardPoint;

public class KingDataModel extends ChessDataModel {

    public KingDataModel(int x, int y, ChessColor chessColor) {
        super(x, y, chessColor);
        chessType = ChessType.King;
    }

    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new KingChessComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x,y), chessColor, chessboard.getClickController(), chessboard.getCHESS_SIZE());
    }
}
