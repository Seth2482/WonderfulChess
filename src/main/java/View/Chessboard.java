package View;


import Model.*;
import Controller.ClickController;
import Model.KingChessComponent;
import Model.KnightChessComponent;
import Model.PawnChessComponent;
import Model.QueenChessComponent;
import Archive.Archive;
import View.Dialog.KingAttackedDialog;
import View.Dialog.LoseDialog;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    // 等待执行的任务列表
    HashMap<String, Runnable> asyncTasks = new HashMap<String, Runnable>();
    HashMap<String, Integer> asyncTasksSteps = new HashMap<String, Integer>();
    protected Archive archive;
    private JLabel statusLabel = new JLabel();
    private HashMap<ChessComponent, ArrayList<ChessComponent>> aWhiteChessToWhichItCanMove = new HashMap<>();
    private HashMap<ChessComponent, ArrayList<ChessComponent>> aBlackChessToWhichItCanMove = new HashMap<>();
    private int whiteKingX = 7;
    private int whiteKingY = 4;
    private int BlackKingX = 0;
    private int BlackKingY = 4;
    private int currentStep = 1;
    private boolean inTest = false;

    public int getWhiteKingX() {
        return whiteKingX;
    }

    public void setWhiteKingX(int whiteKingX) {
        this.whiteKingX = whiteKingX;
    }

    public int getWhiteKingY() {
        return whiteKingY;
    }

    public void setWhiteKingY(int whiteKingY) {
        this.whiteKingY = whiteKingY;
    }

    public int getBlackKingX() {
        return BlackKingX;
    }

    public void setBlackKingX(int blackKingX) {
        BlackKingX = blackKingX;
    }

    public int getBlackKingY() {
        return BlackKingY;
    }

    public void setBlackKingY(int blackKingY) {
        BlackKingY = blackKingY;
    }

    public HashMap<ChessComponent, ArrayList<ChessComponent>> getaWhiteChessToWhichItCanMove() {
        return aWhiteChessToWhichItCanMove;
    }

    public HashMap<ChessComponent, ArrayList<ChessComponent>> getaBlackChessToWhichItCanMove() {
        return aBlackChessToWhichItCanMove;
    }

    public void setStatusLabelText(String text) {
        this.statusLabel.setText(text);
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Chessboard(int width, int height, Archive archive) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        this.archive = archive;

        recoverFromArchive();
//        scanTheChessboard();
    }

    public Chessboard(int width, int height, boolean inTest) {
        this.inTest = inTest;
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        archive = new Archive();
        archive.initialize();

        initialAllChess();
    }

    public Chessboard(int width, int height) {
        this(width, height, false);
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
        this.repaint();
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }

        if (chess1.getChessboardPoint().getY() != 0) {
            if (chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY() - 1] instanceof PawnChessComponent && chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY() - 1].getChessColor() != chess1.getChessColor()) {
                if (((PawnChessComponent) chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY() - 1]).isCanBeEnAsPassant() && chess2.getChessboardPoint().getY() == chess1.getChessboardPoint().getY() - 1) {
                    remove(chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY() - 1]);
                    putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(chess1.getChessboardPoint().getX(), chess1.getChessboardPoint().getY() - 1), calculatePoint(chess1.getChessboardPoint().getX(), chess1.getChessboardPoint().getY() - 1), clickController, CHESS_SIZE));
                }
            }
        }

        if (chess1.getChessboardPoint().getY() != CHESSBOARD_SIZE - 1) {
            if (chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY() + 1] instanceof PawnChessComponent && chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY() + 1].getChessColor() != chess1.getChessColor()) {
                if (((PawnChessComponent) chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY() + 1]).isCanBeEnAsPassant() && chess2.getChessboardPoint().getY() == chess1.getChessboardPoint().getY() + 1) {
                    remove(chessComponents[chess1.getChessboardPoint().getX()][chess1.getChessboardPoint().getY() + 1]);
                    putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(chess1.getChessboardPoint().getX(), chess1.getChessboardPoint().getY() + 1), calculatePoint(chess1.getChessboardPoint().getX(), chess1.getChessboardPoint().getY() + 1), clickController, CHESS_SIZE));
                }
            }
        }

        boolean leftWhiteKingChangeRook = chess1 instanceof KingChessComponent && chess1.getChessboardPoint().getX() == 7 && chess1.getChessboardPoint().getY() == 4 && chess2.getChessboardPoint().getX() == 7 && chess2.getChessboardPoint().getY() == 2 && chess1.getChessColor() == ChessColor.WHITE;
        if (leftWhiteKingChangeRook) {
            remove(chessComponents[7][0]);
            add(chessComponents[7][0] = new EmptySlotComponent(new ChessboardPoint(7, 0), calculatePoint(7, 0), clickController, CHESS_SIZE));
            putChessOnBoard(new RookChessComponent(new ChessboardPoint(7, 3), calculatePoint(7, 3), ChessColor.WHITE, clickController, CHESS_SIZE));
        }

        boolean rightWhiteKingChangeRook = chess1 instanceof KingChessComponent && chess1.getChessboardPoint().getX() == 7 && chess1.getChessboardPoint().getY() == 4 && chess2.getChessboardPoint().getX() == 7 && chess2.getChessboardPoint().getY() == 6 && chess1.getChessColor() == ChessColor.WHITE;
        if (rightWhiteKingChangeRook) {
            remove(chessComponents[7][7]);
            add(chessComponents[7][7] = new EmptySlotComponent(new ChessboardPoint(7, 7), calculatePoint(7, 7), clickController, CHESS_SIZE));
            putChessOnBoard(new RookChessComponent(new ChessboardPoint(7, 5), calculatePoint(7, 5), ChessColor.WHITE, clickController, CHESS_SIZE));
        }

        boolean leftBlackKingChangeRook = chess1 instanceof KingChessComponent && chess1.getChessboardPoint().getX() == 0 && chess1.getChessboardPoint().getY() == 4 && chess2.getChessboardPoint().getX() == 0 && chess2.getChessboardPoint().getY() == 2 && chess1.getChessColor() == ChessColor.BLACK;
        if (leftBlackKingChangeRook) {
            remove(chessComponents[0][0]);
            add(chessComponents[0][0] = new EmptySlotComponent(new ChessboardPoint(0, 0), calculatePoint(0, 0), clickController, CHESS_SIZE));
            putChessOnBoard(new RookChessComponent(new ChessboardPoint(0, 3), calculatePoint(0, 3), ChessColor.BLACK, clickController, CHESS_SIZE));
        }

        boolean rightBlackKingChangeRook = chess1 instanceof KingChessComponent && chess1.getChessboardPoint().getX() == 0 && chess1.getChessboardPoint().getY() == 4 && chess2.getChessboardPoint().getX() == 0 && chess2.getChessboardPoint().getY() == 6 && chess1.getChessColor() == ChessColor.BLACK;
        if (rightBlackKingChangeRook) {
            remove(chessComponents[0][7]);
            add(chessComponents[0][7] = new EmptySlotComponent(new ChessboardPoint(0, 7), calculatePoint(0, 7), clickController, CHESS_SIZE));
            putChessOnBoard(new RookChessComponent(new ChessboardPoint(0, 5), calculatePoint(0, 5), ChessColor.BLACK, clickController, CHESS_SIZE));
        }


        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();

        checkAndInvoke();
        archive.stepTrigger(this, chess1, chess2);

        // 步数计步器
        currentStep++;
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        statusLabel.setText("Current Color: " + currentColor.getName());
        statusLabel.repaint();
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void initialAllChess() {
        initiateEmptyChessboard();

        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);

        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);

        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);

        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);

        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
        }

        this.currentColor = ChessColor.WHITE;
        statusLabel.setText("Current Color: " + currentColor.getName());

        archive.initialize();

        if (!inTest){
            ChessGameFrame.getInstance().setSaveButtonEnabled(false);
            addAsyncTask(() -> {
                ChessGameFrame.getInstance().setSaveButtonEnabled(true);
            }, 1);
        }

//        this.scanTheChessboard();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    public Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }

    public void addAsyncTask(Runnable task, int steps) {
        // 打标签
        String uuid = UUID.randomUUID().toString();
        asyncTasks.put(uuid, task);
        asyncTasksSteps.put(uuid, steps);
    }

    public static void invokeLater(Runnable task, int steps) {
        getInstance().addAsyncTask(task, steps);
    }

    private void checkAndInvoke() {

        Iterator<Map.Entry<String, Integer>> iterator = asyncTasksSteps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();

            String id = entry.getKey();
            int steps = entry.getValue();
            steps--;

            if (steps <= 0) {
                Runnable task = asyncTasks.get(id);
                task.run();
                iterator.remove();
                asyncTasks.remove(id);
            } else {
                asyncTasksSteps.put(id, steps);
            }
        }
    }

    private void recoverFromArchive() {
        ChessGameFrame.setChessboard(this);
        ChessComponent[][] chessComponents = archive.getChessComponents();

        for (int m = 0; m < chessComponents.length; m++) {
            for (int n = 0; n < chessComponents[m].length; n++) {
                ChessComponent chessComponent = chessComponents[m][n];
                chessComponent.setVisible(true);
                putChessOnBoard(chessComponent);
            }
        }

        this.currentColor = archive.getCurrentColor();
        ChessGameFrame.getInstance().setSaveButtonEnabled(true);

    }

    public static Chessboard getInstance() {
        return ChessGameFrame.getChessboard();
    }

    public Archive getArchive() {
        return archive;
    }

    public ClickController getClickController() {
        return clickController;
    }

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

    public void pawnTranslateToQueen(PawnChessComponent pawn) {
        remove(pawn);
        ChessComponent chessComponent = new QueenChessComponent(pawn.getChessboardPoint(), pawn.getLocation(), pawn.getChessColor(), clickController, CHESS_SIZE);
        putChessOnBoard(chessComponent);
    }

    public void pawnTranslateToRook(PawnChessComponent pawn) {
        remove(pawn);
        ChessComponent chessComponent = new RookChessComponent(pawn.getChessboardPoint(), pawn.getLocation(), pawn.getChessColor(), clickController, CHESS_SIZE);
        putChessOnBoard(chessComponent);
    }

    public void pawnTranslateToBishop(PawnChessComponent pawn) {
        remove(pawn);
        ChessComponent chessComponent = new BishopChessComponent(pawn.getChessboardPoint(), pawn.getLocation(), pawn.getChessColor(), clickController, CHESS_SIZE);
        putChessOnBoard(chessComponent);
    }

    public void pawnTranslateToKnight(PawnChessComponent pawn) {
        remove(pawn);
        ChessComponent chessComponent = new KnightChessComponent(pawn.getChessboardPoint(), pawn.getLocation(), pawn.getChessColor(), clickController, CHESS_SIZE);
        putChessOnBoard(chessComponent);
    }

    public ArrayList<HashMap<ChessComponent, ArrayList<ChessComponent>>> scanTheChessboard() {
        int blackKingCounter = 0;
        int whiteKingCounter = 0;
        for (int x1 = 0; x1 < 8; x1++) {
            for (int y1 = 0; y1 < 8; y1++) {
                if (chessComponents[x1][y1] instanceof KingChessComponent && chessComponents[x1][y1].getChessColor() == ChessColor.WHITE) {
                    whiteKingCounter++;
                    whiteKingX = x1;
                    whiteKingY = y1;
                    System.out.printf("The white king is in %d,%d\n", whiteKingX, whiteKingY);
                }
                if (chessComponents[x1][y1] instanceof KingChessComponent && chessComponents[x1][y1].getChessColor() == ChessColor.BLACK) {
                    blackKingCounter++;
                    BlackKingX = x1;
                    BlackKingY = y1;
                    System.out.printf("The black king is in %d,%d\n", BlackKingX, BlackKingY);
                }
                chessComponents[x1][y1].getToWhereCanMove().clear();
                for (int x2 = 0; x2 < 8; x2++) {
                    for (int y2 = 0; y2 < 8; y2++) {
                        if (!(chessComponents[x1][y1] instanceof EmptySlotComponent)) {
                            if ((chessComponents[x1][y1].canMoveTo(chessComponents, new ChessboardPoint(x2, y2))) && (!chessComponents[x2][y2].getChessColor().equals(chessComponents[x1][y1].getChessColor()))) {
                                chessComponents[x1][y1].setToWhereCanMove(chessComponents[x2][y2]);
                            }
                        }
                    }
                }
            }
        }

        if (whiteKingCounter != 1) {
            new LoseDialog(ChessColor.BLACK);
        }
        if (blackKingCounter != 1) {
            new LoseDialog(ChessColor.WHITE);
        }

        a:
        for (int i = 0; i < 8; i++) {
            for (int i1 = 0; i1 < 8; i1++) {
                if (!(chessComponents[i][i1] instanceof EmptySlotComponent)) {
                    if (chessComponents[i][i1].getChessColor() == ChessColor.WHITE) {
                        if (chessComponents[i][i1].getToWhereCanMove().contains(chessComponents[BlackKingX][BlackKingY])) {
                            if (chessComponents[BlackKingX][BlackKingY].getToWhereCanMove().size() == 0) {
                                new LoseDialog(ChessColor.WHITE);
                                break a;
                            }

                            int counterWhereCanMove = 0;
                            for (int i2 = 0; i2 < chessComponents[BlackKingX][BlackKingY].getToWhereCanMove().size(); i2++) {
                                if (chessComponents[i][i1].getToWhereCanMove().contains(chessComponents[BlackKingX][BlackKingY].getToWhereCanMove().get(i2))) {
                                    counterWhereCanMove++;
                                }
                                if (counterWhereCanMove == chessComponents[BlackKingX][BlackKingY].getToWhereCanMove().size()) {
                                    new LoseDialog(ChessColor.WHITE);
                                    break a;
                                }
                            }

                            new KingAttackedDialog(chessComponents[i][i1].getChessColor());
                            break a;
                        }
                        aWhiteChessToWhichItCanMove.put(chessComponents[i][i1], chessComponents[i][i1].getToWhereCanMove());
                    }
                    if (chessComponents[i][i1].getChessColor() == ChessColor.BLACK) {

                        if (chessComponents[i][i1].getToWhereCanMove().contains(chessComponents[whiteKingX][whiteKingY])) {
                            if (chessComponents[whiteKingX][whiteKingY].getToWhereCanMove().size() == 0) {
                                new LoseDialog(ChessColor.BLACK);
                                break a;
                            }

                            int counterWhereCanMove = 0;
                            for (int i2 = 0; i2 < chessComponents[whiteKingX][whiteKingY].getToWhereCanMove().size(); i2++) {
                                if (chessComponents[i][i1].getToWhereCanMove().contains(chessComponents[whiteKingX][whiteKingY].getToWhereCanMove().get(i2))) {
                                    counterWhereCanMove++;
                                }
                                if (counterWhereCanMove == chessComponents[whiteKingX][whiteKingY].getToWhereCanMove().size()) {
                                    new LoseDialog(ChessColor.BLACK);
                                    break a;
                                }
                            }

                            new KingAttackedDialog(chessComponents[i][i1].getChessColor());
                            break a;
//
                        }
                        aBlackChessToWhichItCanMove.put(chessComponents[i][i1], chessComponents[i][i1].getToWhereCanMove());
                    }
                }
            }
        }

        ArrayList<HashMap<ChessComponent, ArrayList<ChessComponent>>> scannerChessboardResult = new ArrayList<>();
        scannerChessboardResult.add(aWhiteChessToWhichItCanMove);
        scannerChessboardResult.add(aBlackChessToWhichItCanMove);

        return scannerChessboardResult;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public boolean isInTest() {
        return inTest;
    }
}
