package Archive.DataModel;

import Archive.ChessType;
import Model.ChessColor;
import Model.ChessComponent;
import Model.RookChessComponent;
import View.Chessboard;
import View.ChessboardPoint;

public class RookDataModel extends ChessDataModel {
    private boolean canBeChangeKing = true;
    private boolean hasMoved = false;

    public RookDataModel(int x, int y, ChessColor chessColor, boolean hasMoved, boolean canBeChangeKing) {
        super(x, y, chessColor);
        this.canBeChangeKing = canBeChangeKing;
        this.hasMoved = hasMoved;
        chessType = ChessType.Rook;

    }

    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new RookChessComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x, y), chessColor, chessboard.getClickController(), chessboard.getCHESS_SIZE(), canBeChangeKing, hasMoved);
    }
}
