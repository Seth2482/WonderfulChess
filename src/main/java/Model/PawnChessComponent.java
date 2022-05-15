package Model;

import Controller.ClickController;
import View.Chessboard;
import View.ChessboardPoint;


import javax.swing.*;
import javax.xml.transform.Source;
import java.awt.*;
import java.io.IOException;

public class PawnChessComponent extends ChessComponent {
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;

    private final ChessColor thisChessColor;//record the color of chess;
    private boolean isTheFirstMove = true;//是否是第一步
    private boolean canBeEnAsPassant = false;//能否被别人当作过路兵吃掉

    public boolean isCanBeEnAsPassant() {
        return canBeEnAsPassant;
    }

    public boolean isTheFirstMove() {
        return isTheFirstMove;
    }

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = getImage("images/pawn-white.png");
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = getImage("images/pawn-black.png");
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                trueImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                trueImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        thisChessColor = color;
        initiatePawnImage(color);
    }

    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int factor = thisChessColor == ChessColor.WHITE ? 1 : -1;

        if (isTheFirstMove) {
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) && (destination.getX() == source.getX() - factor) && (destination.getY() == source.getY() + 1)) {
                return true;
            }
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) && (destination.getX() == source.getX() - factor) && (destination.getY() == source.getY() - 1)) {
                return true;
            }//斜着吃子

            if (source.getX() - destination.getX() == factor && source.getY() == destination.getY()) {
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                    return false;
                }
                return true;
            }

            if (source.getX() - destination.getX() == 2 * factor && source.getY() == destination.getY()) {
                if (!(chessComponents[source.getX() - factor][source.getY()] instanceof EmptySlotComponent)) {
                    return false;
                }
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                    return false;
                }

                return true;
            }
            return false;
        } else {
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) && (destination.getX() == source.getX() - factor) && (destination.getY() == source.getY() + 1)) {
                System.out.println("eat chess as cross");
                return true;
            }
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) && (destination.getX() == source.getX() - factor) && (destination.getY() == source.getY() - 1)) {
                System.out.println("eat chess as cross");
                return true;
            }//斜着吃子
            if (source.getY() != chessComponents.length - 1) {
                if (chessComponents[source.getX()][source.getY() + 1] instanceof PawnChessComponent && destination.getX() == source.getX() - factor && destination.getY() == source.getY() + 1) {
                    if (((PawnChessComponent) chessComponents[source.getX()][source.getY() + 1]).canBeEnAsPassant && chessComponents[source.getX()][source.getY() + 1].chessColor != thisChessColor) {
                        return true;
                    }
                }
            }

            if (source.getY() != 0) {
                if (chessComponents[source.getX()][source.getY() - 1] instanceof PawnChessComponent && destination.getX() == source.getX() - factor && destination.getY() == source.getY() - 1) {
                    if (((PawnChessComponent) chessComponents[source.getX()][source.getY() - 1]).canBeEnAsPassant && chessComponents[source.getX()][source.getY() - 1].chessColor != thisChessColor) {
                        return true;
                    }
                }
            }//吃过路兵

            if (source.getX() - destination.getX() == factor && source.getY() == destination.getY() && (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                return true;
            }

            return false;
        }
    }

    public void showDialog() {
        new SideLineTransitionDialog(this);
    }

    @Override
    public void swapLocation(ChessComponent another) {
        super.swapLocation(another);

        checkCanBeEnAsPassant();
        updateIsTheFirstMove();
        checkIfReachedBottom();


    }

    public void checkCanBeEnAsPassant() {
        if (isTheFirstMove) {
            canBeEnAsPassant = true;

            // 对手下完棋后 这个棋就不能吃了
            Chessboard.invokeLater(() -> {
                this.canBeEnAsPassant = false;
            }, 2);
        }
    }

    public void updateIsTheFirstMove() {
        if (isTheFirstMove) {
            isTheFirstMove = false;
        }
    }

    // 底线升变逻辑
    public void checkIfReachedBottom() {
        int bottomX = getChessColor() == ChessColor.WHITE ? 0 : 7;

        if (getChessboardPoint().getX() == bottomX){
            showDialog();
        }

    }
}

class SideLineTransitionDialog extends JDialog {
    SideLineTransitionDialog(PawnChessComponent pawn) {
        setVisible(true);
        setLayout(null);
        setBounds(500, 300, 500, 150);

        JLabel statusLabel = new JLabel("Which chess do you want your pawn to be?");
        statusLabel.setLocation(90, 0);
        statusLabel.setSize(400, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(statusLabel);

        JButton buttonQueen = new JButton("Queen");
        buttonQueen.setFont(new Font("Rockwell", Font.BOLD, 15));
        buttonQueen.setLocation(60, 60);
        buttonQueen.setSize(80, 30);
        buttonQueen.addActionListener((e) -> {
            Chessboard.getInstance().pawnTranslateToQueen(pawn);
            this.dispose();
        });
        add(buttonQueen);

        JButton buttonRook = new JButton("Rook");
        buttonRook.setFont(new Font("Rockwell", Font.BOLD, 15));
        buttonRook.setLocation(160, 60);
        buttonRook.setSize(80, 30);
        buttonRook.addActionListener((e) -> {
            Chessboard.getInstance().pawnTranslateToRook(pawn);
            this.dispose();
        });
        add(buttonRook);

        JButton buttonKnight = new JButton("Knight");
        buttonKnight.setFont(new Font("Rockwell", Font.BOLD, 15));
        buttonKnight.setLocation(260, 60);
        buttonKnight.setSize(80, 30);
        buttonKnight.addActionListener((e) -> {
            Chessboard.getInstance().pawnTranslateToKnight(pawn);
            this.dispose();
        });
        add(buttonKnight);

        JButton buttonBishop = new JButton("Bishop");
        buttonBishop.setFont(new Font("Rockwell", Font.BOLD, 15));
        buttonBishop.setLocation(360, 60);
        buttonBishop.setSize(80, 30);
        buttonBishop.addActionListener((e) -> {
            Chessboard.getInstance().pawnTranslateToBishop(pawn);
            this.dispose();
        });
        add(buttonBishop);
    }
}




