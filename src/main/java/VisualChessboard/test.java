package VisualChessboard;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        String s  = "_NBQKBN_";
        list.add(s);
        s = "PRPPPPPP";
        list.add(s);
        s = "________";
        list.add(s); list.add(s); list.add(s); list.add(s);
        s = "________"; list.add(s);
        s = "rnbqkbnr"; list.add(s);
        s = "w"; list.add(s);

        ConcreteChessGame chessGame = new ConcreteChessGame();
        chessGame.loadChessGame(list);

        System.out.println(chessGame.getChessboardGraph());

        System.out.println(chessGame.moveChess(7,0,1,0));


        System.out.println(chessGame.getChessboardGraph());

        System.out.println(chessGame.moveChess(1,1,1,2));

        System.out.println(chessGame.getChessboardGraph());

        System.out.println(chessGame.moveChess(1,1,7,1));

        System.out.println(chessGame.getChessboardGraph());

        System.out.println(chessGame.moveChess(1,0,1,1));

        System.out.println(chessGame.getChessboardGraph());
    }
}

