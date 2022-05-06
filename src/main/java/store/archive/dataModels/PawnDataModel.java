package store.archive.dataModels;

import controller.ClickController;
import model.BishopChessComponent;
import model.ChessColor;
import model.ChessComponent;
import model.PawnChessComponent;
import store.archive.ChessType;
import view.Chessboard;
import view.ChessboardPoint;

import java.awt.*;

public class PawnDataModel extends ChessDataModel {
    private boolean isTheFirstMove;
    private boolean canBeEnAsPassant;

    public PawnDataModel(int x, int y, ChessColor chessColor, boolean isTheFirstMove, boolean canBeEnAsPassant) {
        super(x, y, chessColor);
        this.isTheFirstMove = isTheFirstMove;
        this.canBeEnAsPassant = canBeEnAsPassant;
        chessType = ChessType.Pawn;
    }

    public static ChessDataModel fromPawnComponent(PawnChessComponent chessComponent) {
        return new PawnDataModel(chessComponent.getX(), chessComponent.getY(), chessComponent.getChessColor(), chessComponent.isTheFirstMove(), chessComponent.isCanBeEnAsPassant());
    };

    @Override
    public ChessComponent toChessComponent(Chessboard chessboard) {
        return new PawnChessComponent(new ChessboardPoint(x, y), chessboard.calculatePoint(x,y), chessColor, chessboard.getClickController(), chessboard.getCHESS_SIZE());
    }
}
