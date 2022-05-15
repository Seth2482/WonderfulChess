package Archive.DataModel;

import Archive.ChessType;
import Model.ChessColor;
import Model.ChessComponent;
import Model.KingChessComponent;
import View.Chessboard;
import View.ChessboardPoint;

public class KingDataModel extends ChessDataModel {
    private boolean canBeChangeRook = true;
    private boolean hasMoved = false;

    public KingDataModel(int x, int y, ChessColor chessColor, boolean canBeChangeRook, boolean hasMoved) {
        super(x, y, chessColor);
        this.canBeChangeRook = canBeChangeRook;
        this.hasMoved = hasMoved;
        chessType = ChessType.King;
    }

    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new KingChessComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x,y), chessColor, chessboard.getClickController(), chessboard.getCHESS_SIZE(), canBeChangeRook, hasMoved);
    }
}
