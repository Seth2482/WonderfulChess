package Archive.DataModel;

import Archive.ChessType;
import Model.ChessColor;
import Model.ChessComponent;
import Model.EmptySlotComponent;
import View.Chessboard;
import View.ChessboardPoint;

public class EmptyDataModel extends ChessDataModel {

    public EmptyDataModel(int x, int y, ChessColor chessColor) {
        super(x, y, chessColor);
        chessType = ChessType.Empty;
    }


    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new EmptySlotComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x, y), chessboard.getClickController(), chessboard.getCHESS_SIZE());
    }
}
