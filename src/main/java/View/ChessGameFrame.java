package View;

import Controller.GameController;
import Archive.Archive;
import Model.GameMode;
import Sound.SoundPlayer;
import View.Dialog.ChoosePathDialog;
import View.Dialog.RestartDialog;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private int WIDTH;
    private int HEIGHT;
    public int CHESSBOARD_SIZE;
    private GameController gameController;
    private JButton saveButton;
    private JLabel statusLabel;
    private static ChessGameFrame instance;
    private static Chessboard chessboard;
    private static SoundPlayer soundPlayer = new SoundPlayer();
    private static GameMode gameMode;
    private JButton restartButton;
    private JButton repentButton = new JButton("Placeholder");
    private JLabel roundLabel = new JLabel("Round #1");


    public ChessGameFrame(int width, int height, GameMode gameMode) {
        basicInitialize(width, height);
        ChessGameFrame.gameMode = gameMode;
        // 不要改代码的顺序 不然会很难收场！！
        // 已经成屎山代码了
        addSaveButton();
        addRestartButton();
        addStatusLabel();
        addRoundLabel();
        addChessboard();
        Chessboard.getInstance().setStatusLabelText("Current Color: " + getChessboard().getCurrentColor().getName());
        if (gameMode == GameMode.PVP) {
            addRepentButton();
        }

    }

    public ChessGameFrame(int width, int height, Archive archive, GameMode gameMode) {
        basicInitialize(width, height);

        ChessGameFrame.gameMode = gameMode;
        addSaveButton();
        addRestartButton();
        addStatusLabel();
        addRoundLabel();
        addChessboard(archive);
        Chessboard.getInstance().setStatusLabelText("Current Color: " + getChessboard().getCurrentColor().getName());
        if (gameMode == GameMode.PVP) {
            addRepentButton();
        }
    }

    private void basicInitialize(int width, int height) {
        setTitle("WonderfulChess"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;
        this.instance = this;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboard.setStatusLabel(this.statusLabel);
        chessboard.setRoundLabel(this.roundLabel);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
        this.repaint();
    }

    private void addChessboard(Archive archive) {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, archive);
        chessboard.setStatusLabel(this.statusLabel);
        chessboard.setRoundLabel(this.roundLabel);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
        this.repaint();
    }

    /**
     * 在游戏面板中添加标签
     */
    private JLabel addStatusLabel() {
        statusLabel = new JLabel();
        statusLabel.setLocation(HEIGHT - 30, HEIGHT / 10);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
        return statusLabel;
    }


    private void addRestartButton() {
        restartButton = new JButton("Restart Game");
        restartButton.addActionListener((e) -> {
            new RestartDialog();
        });
        restartButton.setLocation(HEIGHT, HEIGHT / 10 + 120);
        restartButton.setSize(200, 60);
        restartButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(restartButton);
    }

    private void addSaveButton() {
        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        saveButton.setLocation(HEIGHT, HEIGHT / 10 + 240);
        saveButton.setSize(200, 60);
        saveButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(saveButton);

        saveButton.addActionListener(e -> {
            Archive archive = Chessboard.getInstance().getArchive();
            if (archive.isFresh()) {
                ChoosePathDialog choosePathDialog = new ChoosePathDialog();
                choosePathDialog.setVisible(true);
            } else {
                archive.save();
                JOptionPane.showMessageDialog(Chessboard.getInstance(), "Your archive has been saved!");
            }
        });

    }

    private void addRepentButton() {
        repentButton = new JButton("Repent");
        repentButton.setEnabled(false);
        repentButton.setLocation(HEIGHT, HEIGHT / 10 + 360);
        repentButton.setSize(200, 60);
        repentButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(repentButton);

        repentButton.addActionListener(e -> {
            chessboard.repent();
        });
    }

    private void addRoundLabel(){
        roundLabel = new JLabel("Round #1");
        roundLabel.setLocation(70, 10);
        roundLabel.setSize(300, 60);
        roundLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(roundLabel);
    }

    public void setActionButtonsEnabled(boolean enabled) {
        restartButton.setEnabled(enabled);
        saveButton.setEnabled(enabled);
    }

    public void setSaveButtonEnabled(Boolean enabled) {
        saveButton.setEnabled(enabled);
    }

    public void setRepentButtonEnabled(Boolean enabled) {
        repentButton.setEnabled(enabled);
    }

    public static ChessGameFrame getInstance() {
        return instance;
    }

    public static Chessboard getChessboard() {
        return chessboard;
    }

    public static void setChessboard(Chessboard chessboard) {
        ChessGameFrame.chessboard = chessboard;
    }

    public static SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public void setRestartButton(boolean enabled) {
        restartButton.setEnabled(enabled);
    }

    public static JButton getRestartButton() {
        return getInstance().restartButton;
    }

    public static GameMode getGameMode() {
        return gameMode;
    }

}

