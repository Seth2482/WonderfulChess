package store.archive;

import com.google.gson.Gson;
import model.ChessComponent;
import store.archive.dataModels.ChessDataModel;
import store.archive.dataModels.DataModelFactory;
import view.Chessboard;

public class Step {
    ChessDataModel chess1; // 行棋方
    ChessDataModel chess2;
    ChessDataModel[][] ChessboardAfterThisStep = new ChessDataModel[8][8];

    public Step(Chessboard chessboard, ChessComponent chess1, ChessComponent chess2) {

        this.chess1 = DataModelFactory.generateDataModel(chess1);
        this.chess2 = DataModelFactory.generateDataModel(chess2);
        ChessComponent[][] chessComponents = chessboard.getChessComponents();

        for (int m = 0; m < chessboard.getChessComponents().length; m++) {
            for (int n = 0; n < chessComponents[m].length; n++) {
                ChessboardAfterThisStep[m][n] = DataModelFactory.generateDataModel(chessComponents[m][n]);
            }
        }
    }

    public ChessComponent[][] getChessComponents() {
        ChessComponent[][] chessComponents = new ChessComponent[8][8];

        ChessDataModel[][] dataModels = ChessboardAfterThisStep;
        Chessboard chessboard = Chessboard.getInstance();
        for (int m = 0; m < dataModels.length; m++) {
            for (int n = 0; n < dataModels.length; n++) {
                chessComponents[m][n] = dataModels[m][n].toChessComponent(chessboard);
            }
        }

        return chessComponents;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
