package Archive;

import Archive.DataModel.ChessDataModel;
import com.google.gson.Gson;
import Model.ChessComponent;
import Archive.DataModel.DataModelFactory;
import View.Chessboard;

public class Step {
    ChessDataModel chess1; // 行棋方
    ChessDataModel chess2;
    ChessDataModel[][] ChessboardAfterThisStep;

    public Step(Chessboard chessboard, ChessComponent chess1, ChessComponent chess2,boolean reverse) {
        this.chess1 = DataModelFactory.generateDataModel(chess1);
        this.chess2 = DataModelFactory.generateDataModel(chess2);
        if (reverse){
            int chess1X = this.chess2.getX(), chess1Y = this.chess2.getY(), chess2X = this.chess1.getX(), chess2Y = this.chess1.getY();
            this.chess1.setX(chess1X);
            this.chess1.setY(chess1Y);
            this.chess2.setX(chess2X);
            this.chess2.setY(chess2Y);
        }

        this.ChessboardAfterThisStep = new ChessDataModel[8][8];
        ChessComponent[][] chessComponents = chessboard.getChessComponents();

        for (int m = 0; m < chessboard.getChessComponents().length; m++) {
            for (int n = 0; n < chessComponents[m].length; n++) {
                ChessboardAfterThisStep[m][n] = DataModelFactory.generateDataModel(chessComponents[m][n]);
            }
        }
    }



    public ChessComponent[][] getChessComponents(Chessboard chessboard) {
        ChessComponent[][] chessComponents = new ChessComponent[8][8];

        ChessDataModel[][] dataModels = ChessboardAfterThisStep;
        for (int m = 0; m < dataModels.length; m++) {
            for (int n = 0; n < dataModels.length; n++) {
                chessComponents[m][n] = dataModels[m][n].toChessComponent(chessboard);
            }
        }

        return chessComponents;
    }

    public ChessDataModel[][] getChessDataModels() {
        return ChessboardAfterThisStep;
    }

    public ChessComponent getChess1Component(Chessboard chessboard){
        return chess1.toChessComponent(chessboard);
    }

    public ChessComponent getChess2Component(Chessboard chessboard){
        return chess2.toChessComponent(chessboard);
    }

    public ChessDataModel getChess1() {
        return chess1;
    }

    public ChessDataModel getChess2() {
        return chess2;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
