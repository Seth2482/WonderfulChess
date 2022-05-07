package Archive.DataModel;

import Archive.ChessType;
import Model.BishopChessComponent;
import Model.ChessColor;
import Model.ChessComponent;
import View.Chessboard;
import View.ChessboardPoint;

public class BishopDataModel extends ChessDataModel {

    public BishopDataModel(int x, int y, ChessColor chessColor) {
        super(x, y, chessColor);
        chessType = ChessType.Bishop;
    }

    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new BishopChessComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x, y), chessColor, chessboard.getClickController(), chessboard.getCHESS_SIZE());
    }
}
