package VisualChessboard;

import java.util.ArrayList;
import java.util.List;

public abstract class ChessComponentVisual {
    //should design
    private ChessboardPointVisual source;
    private ChessColorVisual chessColor;
    protected char name;
    private List<ChessboardPointVisual> whereCanMove = new ArrayList<>();
    private List<String> chessboard = new ArrayList<>();
    private ChessComponentVisual[][] chesses = new ChessComponentVisual[8][8];
    private ArrayList<Model.ChessComponent> toWhereCanMove = new ArrayList<>();

    public ArrayList<Model.ChessComponent> getToWhereCanMove() {
        return toWhereCanMove;
    }

    public void setToWhereCanMove(ArrayList<Model.ChessComponent> toWhereCanMove) {
        this.toWhereCanMove = toWhereCanMove;
    }

    public ChessComponentVisual[][] getChesses() {
        return chesses;
    }

    public void setChesses(ChessComponentVisual[][] chesses) {
        this.chesses = chesses;
    }

    public List<String> getChessboard() {
        return chessboard;
    }

    public void setChessboard(List<String> chessboard) {
        this.chessboard = chessboard;
    }

    public List<ChessboardPointVisual> getWhereCanMove() {
        return whereCanMove;
    }

    public void setWhereCanMove(List<ChessboardPointVisual> whereCanMove) {
        this.whereCanMove = whereCanMove;
    }

    //should design
    public ChessComponentVisual() {

    }

    public ChessboardPointVisual getSource() {
        return source;
    }

    public void setSource(ChessboardPointVisual source) {
        this.source = source;
    }

    public ChessColorVisual getChessColor() {
        return chessColor;
    }

    public void setChessColor(ChessColorVisual chessColor) {
        this.chessColor = chessColor;
    }

    public char getName() {
        return name;
    }

    public void setName(char name) {
        this.name = name;
    }

    // should design
    public abstract List<ChessboardPointVisual> canMoveTo();

    /**
     * should design
     *
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(this.name);
    }

    public void giveValueTo(ChessComponentVisual target) {

    }




}
