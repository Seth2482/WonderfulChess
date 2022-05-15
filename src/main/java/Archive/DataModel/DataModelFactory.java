package Archive.DataModel;

import Model.*;

public class DataModelFactory {
    public static ChessDataModel generateDataModel(ChessComponent chessComponent) {
        int x = chessComponent.getChessboardPoint().getX();
        int y = chessComponent.getChessboardPoint().getY();
        ChessColor chessColor = chessComponent.getChessColor();


        if (chessComponent instanceof BishopChessComponent) {
            return new BishopDataModel(x, y, chessColor);
        }
        if (chessComponent instanceof EmptySlotComponent) {
            return new EmptyDataModel(x, y, chessColor);

        }

        if (chessComponent instanceof KingChessComponent) {
            return new KingDataModel(x, y, chessColor,
                    ((KingChessComponent) chessComponent).isCanBeChangeRook(),
                    ((KingChessComponent) chessComponent).isHasMoved()
            );
        }

        if (chessComponent instanceof KnightChessComponent) {
            return new KnightDataModel(x, y, chessColor);

        }

        if (chessComponent instanceof PawnChessComponent) {
            return new PawnDataModel(x, y, chessColor,
                    ((PawnChessComponent) chessComponent).isTheFirstMove(),
                    ((PawnChessComponent) chessComponent).isCanBeEnAsPassant());

        }

        if (chessComponent instanceof QueenChessComponent) {
            return new QueenDataModel(x, y, chessColor);

        }

        if (chessComponent instanceof RookChessComponent) {
            return new RookDataModel(x, y, chessColor,
                    ((RookChessComponent) chessComponent).isHasMoved(),
                    ((RookChessComponent) chessComponent).isCanBeChangeKing());
        }

        return null;
    }
}
