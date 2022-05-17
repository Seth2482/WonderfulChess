package VisualChessboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConcreteChessGame implements ChessGame {

    public ConcreteChessGame() {
        this.currentPlayer = ChessColorVisual.WHITE;
        this.chessComponentVisuals = new ChessComponentVisual[8][8];
        instanceChessGame = this;
    }

    private ChessComponentVisual[][] chessComponentVisuals = new ChessComponentVisual[8][8];
    private ChessColorVisual currentPlayer;
    private ArrayList<Model.ChessComponent> blackChessArray = new ArrayList<>();
    private ArrayList<Model.ChessComponent> whiteChessArray = new ArrayList<>();
    private int whiteKingX = 7;
    private int whiteKingY = 4;
    private int BlackKingX = 0;
    private int BlackKingY = 4;

    public static ConcreteChessGame instanceChessGame;

    public ConcreteChessGame getInstanceChessGame() {
        return instanceChessGame;
    }

    public ChessComponentVisual[][] getChessComponents() {
        return chessComponentVisuals;
    }

    @Override
    public void loadChessGame(List<String> chessboard) {
        for (int i = 0; i < 8; i++) {
            for (int i1 = 0; i1 < 8; i1++) {
                if (chessboard.get(i).charAt(i1) == 'R') {
                    chessComponentVisuals[i][i1] = new RookVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.BLACK);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'N') {
                    chessComponentVisuals[i][i1] = new KnightVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.BLACK);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'B') {
                    chessComponentVisuals[i][i1] = new BishopVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.BLACK);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'Q') {
                    chessComponentVisuals[i][i1] = new QueenVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.BLACK);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'K') {
                    chessComponentVisuals[i][i1] = new KingVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.BLACK);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'P') {
                    chessComponentVisuals[i][i1] = new PawnVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.BLACK);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'p') {
                    chessComponentVisuals[i][i1] = new PawnVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.WHITE);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'r') {
                    chessComponentVisuals[i][i1] = new RookVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.WHITE);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'n') {
                    chessComponentVisuals[i][i1] = new KnightVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.WHITE);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'b') {
                    chessComponentVisuals[i][i1] = new BishopVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.WHITE);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'k') {
                    chessComponentVisuals[i][i1] = new KingVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.WHITE);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == 'q') {
                    chessComponentVisuals[i][i1] = new QueenVisualChessVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.WHITE);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }
                if (chessboard.get(i).charAt(i1) == '_') {
                    chessComponentVisuals[i][i1] = new EmptySlotVisual();
                    chessComponentVisuals[i][i1].setChessColor(ChessColorVisual.NONE);
                    chessComponentVisuals[i][i1].setSource(new ChessboardPointVisual(i, i1));
                    chessComponentVisuals[i][i1].setChessboard(chessboard);
                }

            }
        }

        if (chessboard.get(8).charAt(0) == 'w') {
            currentPlayer = ChessColorVisual.WHITE;
        } else {
            currentPlayer = ChessColorVisual.BLACK;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessComponentVisuals[i][j].setChesses(chessComponentVisuals);
            }
        }


    }

    @Override
    public ChessColorVisual getCurrentPlayer() {
        return this.currentPlayer;
    }

    @Override
    public ChessComponentVisual getChess(int x, int y) {
        return chessComponentVisuals[x][y];
    }

    @Override
    public List<String> getChessboardGraph() {
        List<String> chessboardGraph = new ArrayList<>();

        for (ChessComponentVisual[] chessComponentVisual : chessComponentVisuals) {
            String chessboardGraphAline = new String();
            for (int j = 0; j < chessComponentVisuals.length; j++) {
                chessboardGraphAline = chessboardGraphAline + chessComponentVisual[j].toString();
            }
            chessboardGraph.add(chessboardGraphAline);
        }

        return chessboardGraph;
    }

    @Override
    public String getCapturedChess(ChessColorVisual player) {
        int rookCounter = 0;
        int pawnCounter = 0;
        int knightCounter = 0;
        int bishopCounter = 0;
        int queenCounter = 0;
        int kingCounter = 0;
        String lostChess = new String();

        for (int i = 0; i < chessComponentVisuals.length; i++) {
            for (int j = 0; j < chessComponentVisuals.length; j++) {
                if (chessComponentVisuals[i][j] instanceof RookVisualChessVisual && chessComponentVisuals[i][j].getChessColor() == player) {
                    rookCounter++;
                }
                if (chessComponentVisuals[i][j] instanceof PawnVisualChessVisual && chessComponentVisuals[i][j].getChessColor() == player) {
                    pawnCounter++;
                }
                if (chessComponentVisuals[i][j] instanceof QueenVisualChessVisual && chessComponentVisuals[i][j].getChessColor() == player) {
                    queenCounter++;
                }
                if (chessComponentVisuals[i][j] instanceof KingVisualChessVisual && chessComponentVisuals[i][j].getChessColor() == player) {
                    kingCounter++;
                }
                if (chessComponentVisuals[i][j] instanceof BishopVisualChessVisual && chessComponentVisuals[i][j].getChessColor() == player) {
                    bishopCounter++;
                }
                if (chessComponentVisuals[i][j] instanceof KnightVisualChessVisual && chessComponentVisuals[i][j].getChessColor() == player) {
                    knightCounter++;
                }
            }
        }

        if (kingCounter != 1) {
            if (player == ChessColorVisual.WHITE) {
                lostChess = lostChess + "k 1\n";
            } else {
                lostChess = lostChess + "K 1\n";
            }
        }
        if (queenCounter != 1) {
            if (player == ChessColorVisual.WHITE) {
                lostChess = lostChess + "q 1\n";
            } else {
                lostChess = lostChess + "Q 1\n";
            }
        }
        if (rookCounter != 2) {
            if (player == ChessColorVisual.WHITE) {
                lostChess = lostChess + String.format("r %d\n", 2 - rookCounter);
            } else {
                lostChess = lostChess + String.format("R %d\n", 2 - rookCounter);
            }
        }
        if (bishopCounter != 2) {
            if (player == ChessColorVisual.WHITE) {
                lostChess = lostChess + String.format("b %d\n", 2 - bishopCounter);
            } else {
                lostChess = lostChess + String.format("B %d\n", 2 - bishopCounter);
            }
        }
        if (knightCounter != 2) {
            if (player == ChessColorVisual.WHITE) {
                lostChess = lostChess + String.format("n %d\n", 2 - knightCounter);
            } else {
                lostChess = lostChess + String.format("N %d\n", 2 - knightCounter);
            }
        }
        if (pawnCounter != 8) {
            if (player == ChessColorVisual.WHITE) {
                lostChess = lostChess + String.format("p %d\n", 8 - pawnCounter);
            } else {
                lostChess = lostChess + String.format("P %d\n", 8 - pawnCounter);
            }
        }
        return lostChess;
    }

    @Override
    public List<ChessboardPointVisual> getCanMovePoints(ChessboardPointVisual source) {
        List<ChessboardPointVisual> whereCanMove = chessComponentVisuals[source.getX()][source.getY()].canMoveTo();
        Collections.sort(whereCanMove, new coordinateCompare());

        return whereCanMove;
    }

    @Override
    public boolean moveChess(int sourceX, int sourceY, int targetX, int targetY) {
        if (chessComponentVisuals[sourceX][sourceY] instanceof EmptySlotVisual) {
            return false;
        }
        if (sourceX > 7 || sourceX < 0 || sourceY > 7 || sourceY < 0 || targetX < 0 || targetX > 7 || targetY < 0 || targetY > 7) {
            return false;
        }
        ChessboardPointVisual target = new ChessboardPointVisual(targetX, targetY);
        List<ChessboardPointVisual> canMoveTo = chessComponentVisuals[sourceX][sourceY].canMoveTo();
        boolean judge = false;
        for (ChessboardPointVisual c : canMoveTo) {
            if (c.getX() == targetX && c.getY() == targetY) {
                judge = true;
                break;
            }
        }
        if (judge) {
            chessComponentVisuals[targetX][targetY] = chessComponentVisuals[sourceX][sourceY];
            chessComponentVisuals[sourceX][sourceY] = new EmptySlotVisual();

            chessComponentVisuals[targetX][targetY].setSource(new ChessboardPointVisual(targetX, targetY));
            if (currentPlayer == ChessColorVisual.WHITE) {
                currentPlayer = ChessColorVisual.BLACK;
            } else {
                currentPlayer = ChessColorVisual.WHITE;
            }


            return true;
        }
        return false;
    }
}

class coordinateCompare implements Comparator<ChessboardPointVisual> {
    @Override
    public int compare(ChessboardPointVisual c1, ChessboardPointVisual c2) {
        return (c1.getX() * 10000 + c1.getY()) - (c2.getX() * 10000 + c2.getY());
    }

}