package VisualChessboard;

import java.util.List;

public interface ChessGame {

    public void loadChessGame(List<String> chessboard);


    public ChessColorVisual getCurrentPlayer();


    public ChessComponentVisual getChess(int x, int y);


    public List<String> getChessboardGraph();


    public String getCapturedChess(ChessColorVisual player);


    List<ChessboardPointVisual> getCanMovePoints(ChessboardPointVisual source);


    boolean moveChess(int sourceX, int sourceY, int targetX, int targetY);


}
