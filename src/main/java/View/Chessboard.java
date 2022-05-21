package View;


import Model.*;
import Controller.ClickController;
import Model.KingChessComponent;
import Model.KnightChessComponent;
import Model.PawnChessComponent;
import Model.QueenChessComponent;
import Archive.Archive;
import VisualChessboard.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Timer.ChessCountdown;

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
    private JLabel roundLabel = new JLabel();
    private JLabel countdownLabel = new JLabel();
    private JLabel whiteKingAlertLabel = new JLabel();
    private JLabel blackKingAlertLabel = new JLabel();
    private ArrayList<ChessComponent> blackChessArray = new ArrayList<>();
    private ArrayList<ChessComponent> whiteChessArray = new ArrayList<>();
    private boolean lose = false;
    private int whiteKingX = 7;
    private int whiteKingY = 4;
    private int BlackKingX = 0;
    private int BlackKingY = 4;
    private int currentStep = 1;
    private boolean whiteLose = false;
    private boolean blackLose = false;
    private boolean inTest = false;
    private ChessCountdown chessCountdown = new ChessCountdown(countdownLabel);

    public GameMode getGameMode() {
        return ChessGameFrame.getGameMode();
    }

    public void setStatusLabelText(String text) {
        this.statusLabel.setText(text);
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setRoundLabel(JLabel jLabel) {
        roundLabel = jLabel;
    }

    public void setRoundLabelText(String text) {
        roundLabel.setText(text);
    }

    public void setWhiteKingAlertLabel(JLabel whiteKingAlertLabel) {
        this.whiteKingAlertLabel = whiteKingAlertLabel;
    }

    public void setBlackKingAlertLabel(JLabel blackKingAlertLabel) {
        this.blackKingAlertLabel = blackKingAlertLabel;
    }

    public void setCountdownLabel(JLabel countdownLabel) {
        this.countdownLabel = countdownLabel;
    }

    public void setChessCountdown(ChessCountdown chessCountdown) {
        this.chessCountdown = chessCountdown;
    }

    public ChessCountdown getChessCountdown() {
        return chessCountdown;
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

        archive = new Archive(getGameMode());
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
        ChessGameFrame.getSoundPlayer().play();
        try {
            TimeUnit.MILLISECONDS.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        kingChangeWithRook(chessComponents, chess1, chess2);

        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        // 检查底线升变
        if (chess1 instanceof PawnChessComponent)
            ((PawnChessComponent) chess1).checkIfReachedBottom();

        chess1.repaint();
        chess2.repaint();

        checkAndInvoke();
        archive.stepTrigger(this, chess1, chess2);
        scanTheChessboard();
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
        checkKingAttacked();
        checkKingExist();
        statusLabel.setText("Current Color: " + currentColor.getName());
        updateRoundLabel();
        statusLabel.repaint();
        chessCountdown.startCount();
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

    private void initQueenOnBoard(int row, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, 3), calculatePoint(row, 3), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, 4), calculatePoint(row, 4), color, clickController, CHESS_SIZE);
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

        initQueenOnBoard(0, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        initKingOnBoard(0, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, ChessColor.WHITE);

        for (int i = 0; i < 8; i++) {
            initPawnOnBoard(1, i, ChessColor.BLACK);
            initPawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
        }

        this.currentColor = ChessColor.WHITE;
        this.currentStep = 1;
        setStatusLabelText("Current Color: " + currentColor.getName());
        updateRoundLabel();

        archive.initialize();

        if (!inTest) {
            ChessGameFrame.getInstance().setSaveButtonEnabled(false);
            addAsyncTask(() -> {
                ChessGameFrame.getInstance().setSaveButtonEnabled(true);
                ChessGameFrame.getInstance().setRepentButtonEnabled(true);
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
        if (!inTest) ChessGameFrame.setChessboard(this);

        if (archive.isEmpty()) {
            initialAllChess();
            return;
        }

        ChessComponent[][] chessComponents = archive.getChessComponents();

        for (int m = 0; m < chessComponents.length; m++) {
            for (int n = 0; n < chessComponents[m].length; n++) {
                ChessComponent chessComponent = chessComponents[m][n];
                chessComponent.setVisible(true);
                putChessOnBoard(chessComponent);
            }
        }

        this.currentColor = archive.getCurrentColor();
        this.currentStep = archive.getStepCount() + 1;
        updateRoundLabel();

        boolean enabled = !archive.isEmpty();
        ChessGameFrame.getInstance().setSaveButtonEnabled(enabled);
        ChessGameFrame.getInstance().setRepentButtonEnabled(enabled);

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
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void pawnTranslateToRook(PawnChessComponent pawn) {
        remove(pawn);
        ChessComponent chessComponent = new RookChessComponent(pawn.getChessboardPoint(), pawn.getLocation(), pawn.getChessColor(), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void pawnTranslateToBishop(PawnChessComponent pawn) {
        remove(pawn);
        ChessComponent chessComponent = new BishopChessComponent(pawn.getChessboardPoint(), pawn.getLocation(), pawn.getChessColor(), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void pawnTranslateToKnight(PawnChessComponent pawn) {
        remove(pawn);
        ChessComponent chessComponent = new KnightChessComponent(pawn.getChessboardPoint(), pawn.getLocation(), pawn.getChessColor(), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    public void scanTheChessboard() {
        whiteChessArray.clear();
        blackChessArray.clear();

        for (int x1 = 0; x1 < 8; x1++) {
            for (int y1 = 0; y1 < 8; y1++) {
                if (chessComponents[x1][y1].getChessColor() == ChessColor.BLACK && !(chessComponents[x1][y1] instanceof EmptySlotComponent)) {
                    blackChessArray.add(chessComponents[x1][y1]);
                }
                if (chessComponents[x1][y1].getChessColor() == ChessColor.WHITE && !(chessComponents[x1][y1] instanceof EmptySlotComponent)) {
                    whiteChessArray.add(chessComponents[x1][y1]);
                }

                if (chessComponents[x1][y1] instanceof KingChessComponent && chessComponents[x1][y1].getChessColor() == ChessColor.WHITE) {
                    whiteKingX = x1;
                    whiteKingY = y1;
                }
                if (chessComponents[x1][y1] instanceof KingChessComponent && chessComponents[x1][y1].getChessColor() == ChessColor.BLACK) {
                    BlackKingX = x1;
                    BlackKingY = y1;
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
    }

    public int[] scanTheChessboardAndJudgeKingAttacked(ChessComponent[][] chessComponents) {
        int[] recore = new int[2];
        int whiteKingX = 7;
        int whiteKingY = 4;
        int BlackKingX = 0;
        int BlackKingY = 4;


        for (int x1 = 0; x1 < 8; x1++) {
            for (int y1 = 0; y1 < 8; y1++) {
                if (chessComponents[x1][y1] instanceof KingChessComponent && chessComponents[x1][y1].getChessColor() == ChessColor.WHITE) {
                    whiteKingX = x1;
                    whiteKingY = y1;
                }
                if (chessComponents[x1][y1] instanceof KingChessComponent && chessComponents[x1][y1].getChessColor() == ChessColor.BLACK) {
                    BlackKingX = x1;
                    BlackKingY = y1;
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

        a:
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(chessComponents[i][j] instanceof EmptySlotComponent)) {


                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        if (chessComponents[i][j].getToWhereCanMove().contains(chessComponents[BlackKingX][BlackKingY])) {
                            recore[0] = 1;
                            break a;
                        }


                    }

                    if (chessComponents[i][j].getToWhereCanMove().contains(chessComponents[whiteKingX][whiteKingY]) && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        recore[1] = 1;
                        break a;
                    }

                }
            }
        }

        return recore;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public boolean isInTest() {
        return inTest;
    }

    public void kingChangeWithRook(ChessComponent[][] chessComponents, ChessComponent chess1, ChessComponent chess2) {
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

    }

    public void AIMove() {
        blackChessArray.clear();
        scanTheChessboard();
        int upperbound_chess = blackChessArray.size();
        Random random = new Random();
        int random_chess = random.nextInt(upperbound_chess);

        while (blackChessArray.get(random_chess).getToWhereCanMove().size() == 0) {
            random_chess = random.nextInt(upperbound_chess);
        }

        if (getGameMode() == GameMode.PVEEasy) {
            int upperbound_move = blackChessArray.get(random_chess).getToWhereCanMove().size();
            int random_move = random.nextInt(upperbound_move);

            ChessComponent beSwapChess = blackChessArray.get(random_chess).getToWhereCanMove().get(random_move);
            swapChessComponents(blackChessArray.get(random_chess), beSwapChess);
            swapColor();
        }
        if (getGameMode() == GameMode.PVEHard) {
            int canEatNumber = 0;
            int whichChessCanEat = 0;
            int whichMoveCanEat = 0;
            for (int i = 0; i < blackChessArray.size(); i++) {
                for (int i1 = 0; i1 < blackChessArray.get(i).getToWhereCanMove().size(); i1++) {
                    if (!(blackChessArray.get(i).getToWhereCanMove().get(i1) instanceof EmptySlotComponent)) {
                        canEatNumber++;
                        whichChessCanEat = i;
                        whichMoveCanEat = i1;
                        break;
                    }
                }
            }

            if (canEatNumber != 0) {
                ChessComponent beSwapChess = blackChessArray.get(whichChessCanEat).getToWhereCanMove().get(whichMoveCanEat);
                swapChessComponents(blackChessArray.get(whichChessCanEat), beSwapChess);
                swapColor();
            } else {
                int upperbound_move = blackChessArray.get(random_chess).getToWhereCanMove().size();
                int random_move = random.nextInt(upperbound_move);

                ChessComponent beSwapChess = blackChessArray.get(random_chess).getToWhereCanMove().get(random_move);
                swapChessComponents(blackChessArray.get(random_chess), beSwapChess);
                swapColor();
            }
        }
    }


    public boolean checkKingAttacked() {
        GameMode gameMode = getGameMode();
        scanTheChessboard();
        for (int i = 0; i < 8; i++) {
            for (int i1 = 0; i1 < 8; i1++) {

                if (chessComponents[i][i1].getChessColor() == ChessColor.BLACK) {
                    if (chessComponents[i][i1].getToWhereCanMove().contains(chessComponents[whiteKingX][whiteKingY])) {
                        CheckKingCanAlive();
                        if (whiteLose) {
                            ChessGameFrame.getInstance().gameOver(ChessColor.BLACK);
                        } else {
                            whiteKingAlertLabel.setVisible(true);
                        }

                    }
                }

                if (gameMode == GameMode.PVP) {
                    if (chessComponents[i][i1].getChessColor() == ChessColor.WHITE) {
                        if (chessComponents[i][i1].getToWhereCanMove().contains(chessComponents[BlackKingX][BlackKingY])) {
                            CheckKingCanAlive();
                            if (blackLose) {
                                ChessGameFrame.getInstance().gameOver(ChessColor.WHITE);
                            } else {
                                blackKingAlertLabel.setVisible(true);
                                return true;
                            }
                        }
                    }
                }

            }
        }

        blackKingAlertLabel.setVisible(false);
        whiteKingAlertLabel.setVisible(false);
        return false;
    }


    public boolean checkKingExist() {
        int blackKingCounter = 0;
        int whiteKingCounter = 0;

        for (int x1 = 0; x1 < 8; x1++) {
            for (int y1 = 0; y1 < 8; y1++) {
                if (chessComponents[x1][y1] instanceof KingChessComponent && chessComponents[x1][y1].getChessColor() == ChessColor.WHITE) {
                    whiteKingCounter++;
                }
                if (chessComponents[x1][y1] instanceof KingChessComponent && chessComponents[x1][y1].getChessColor() == ChessColor.BLACK) {
                    blackKingCounter++;
                }
            }
        }
        if (whiteKingCounter == 0) {
            ChessGameFrame.getInstance().gameOver(ChessColor.BLACK);
            return false;
        }
        if (blackKingCounter == 0) {
            ChessGameFrame.getInstance().gameOver(ChessColor.WHITE);
            return false;
        }

        return true;
    }


    public void repent() {
        if (!canRepent()) return;
        // 清除所有的棋子
        initiateEmptyChessboard();

        // 存档回档
        archive.withdraw();

        // 从存档恢复
        recoverFromArchive();

        // 清除所有任务
        asyncTasks.clear();
        asyncTasksSteps.clear();

        if (archive.isEmpty()) {
            ChessGameFrame.getInstance().setRepentButtonEnabled(false);
            ChessGameFrame.getInstance().setSaveButtonEnabled(false);

            addAsyncTask(() -> {
                ChessGameFrame.getInstance().setRepentButtonEnabled(true);
                ChessGameFrame.getInstance().setSaveButtonEnabled(true);
            }, 1);
        }

        currentStep--;

    }

    public boolean canRepent() {
        return !archive.isEmpty();
    }


    public List<String> captureChessboardPicture() {
        List<String> chessboardGraph = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String aLine = new String();
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof EmptySlotComponent) {
                    aLine = aLine + "_";
                }
                if (chessComponents[i][j] instanceof RookChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    aLine = aLine + "r";
                }
                if (chessComponents[i][j] instanceof BishopChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    aLine = aLine + "b";
                }
                if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    aLine = aLine + "k";
                }
                if (chessComponents[i][j] instanceof KnightChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    aLine = aLine + "n";
                }
                if (chessComponents[i][j] instanceof QueenChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    aLine = aLine + "q";
                }
                if (chessComponents[i][j] instanceof PawnChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    aLine = aLine + "p";
                }

                if (chessComponents[i][j] instanceof PawnChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    aLine = aLine + "P";
                }
                if (chessComponents[i][j] instanceof RookChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    aLine = aLine + "R";
                }
                if (chessComponents[i][j] instanceof BishopChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    aLine = aLine + "B";
                }
                if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    aLine = aLine + "K";
                }
                if (chessComponents[i][j] instanceof KnightChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    aLine = aLine + "N";
                }
                if (chessComponents[i][j] instanceof QueenChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    aLine = aLine + "Q";
                }
            }
            chessboardGraph.add(aLine);
        }
        if (currentColor == ChessColor.WHITE) {
            chessboardGraph.add("w");
        } else {
            chessboardGraph.add("b");
        }

        return chessboardGraph;
    }

    public void updateRoundLabel() {
        setRoundLabelText(String.format("Round #%d", getRound()));
    }

    public int getRound() {
        return (int) Math.ceil((float) currentStep / 2);
    }

    public void CheckKingCanAlive() {
        int blackShouldBe = 0;
        int whiteShouldBe = 0;
        int blackCounter = 0;
        int whiteCounter = 0;

        for (ChessComponent bChess : blackChessArray) {
            for (ChessComponent bChessCanMove : bChess.getToWhereCanMove()) {
                blackShouldBe++;
                ConcreteChessGame concreteChessGame = new ConcreteChessGame();
                concreteChessGame.loadChessGame(captureChessboardPicture());

                int sourceX = bChess.getChessboardPoint().getX();
                int sourceY = bChess.getChessboardPoint().getY();

                int targetX = bChessCanMove.getChessboardPoint().getX();
                int targetY = bChessCanMove.getChessboardPoint().getY();

                concreteChessGame.moveChess(sourceX, sourceY, targetX, targetY);

                ChessComponent[][] chessComponentsAfter = loadChessboard(concreteChessGame.getChessboardGraph());

                if (scanTheChessboardAndJudgeKingAttacked(chessComponentsAfter)[0] == 1) {
                    blackCounter++;
                }


            }
        }

        if (blackCounter == blackShouldBe) {
            blackLose = true;
        }


        for (ChessComponent wChess : whiteChessArray) {
            for (ChessComponent wChessCanMove : wChess.getToWhereCanMove()) {
                whiteShouldBe++;
                ConcreteChessGame concreteChessGame = new ConcreteChessGame();
                concreteChessGame.loadChessGame(captureChessboardPicture());

                int sourceX = wChess.getChessboardPoint().getX();
                int sourceY = wChess.getChessboardPoint().getY();

                int targetX = wChessCanMove.getChessboardPoint().getX();
                int targetY = wChessCanMove.getChessboardPoint().getY();

                concreteChessGame.moveChess(sourceX, sourceY, targetX, targetY);

                ChessComponent[][] chessComponentsAfter = loadChessboard(concreteChessGame.getChessboardGraph());

                scanTheChessboardAndJudgeKingAttacked(chessComponentsAfter);

                if (scanTheChessboardAndJudgeKingAttacked(chessComponentsAfter)[1] == 1) {
                    whiteCounter++;
                }


            }
        }
        if (whiteCounter == whiteShouldBe) {
            whiteLose = true;
        }


    }

    public ChessComponent[][] loadChessboard(List<String> str) {
        ChessComponent[][] chessComponents = new ChessComponent[8][8];

        for (int i = 0; i < 8; i++) {
            for (int i1 = 0; i1 < 8; i1++) {
                if (str.get(i).charAt(i1) == 'R') {
                    chessComponents[i][i1] = new RookChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.BLACK);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }
                if (str.get(i).charAt(i1) == 'Q') {
                    chessComponents[i][i1] = new QueenChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.BLACK);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }

                if (str.get(i).charAt(i1) == 'B') {
                    chessComponents[i][i1] = new BishopChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.BLACK);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }

                if (str.get(i).charAt(i1) == 'K') {
                    chessComponents[i][i1] = new KingChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.BLACK);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }

                if (str.get(i).charAt(i1) == 'N') {
                    chessComponents[i][i1] = new KnightChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.BLACK);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }
                if (str.get(i).charAt(i1) == 'P') {
                    chessComponents[i][i1] = new PawnChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.BLACK);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }

                if (str.get(i).charAt(i1) == 'q') {
                    chessComponents[i][i1] = new QueenChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.WHITE);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }

                if (str.get(i).charAt(i1) == 'p') {
                    chessComponents[i][i1] = new PawnChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.WHITE);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }

                if (str.get(i).charAt(i1) == 'r') {
                    chessComponents[i][i1] = new RookChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.WHITE);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }
                if (str.get(i).charAt(i1) == 'b') {
                    chessComponents[i][i1] = new BishopChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.WHITE);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }
                if (str.get(i).charAt(i1) == 'k') {
                    chessComponents[i][i1] = new KingChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.WHITE);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }
                if (str.get(i).charAt(i1) == 'n') {
                    chessComponents[i][i1] = new KnightChessComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.WHITE);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }

                if (str.get(i).charAt(i1) == '_') {
                    chessComponents[i][i1] = new EmptySlotComponent();
                    chessComponents[i][i1].setChessColor(ChessColor.NONE);
                    chessComponents[i][i1].setChessboardPoint(new ChessboardPoint(i, i1));
                }

            }
        }
        return chessComponents;
    }
}

