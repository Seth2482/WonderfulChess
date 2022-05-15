package View;

import Controller.GameController;
import Archive.Archive;
import Model.GameMode;
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
    // 只作中间变量 实际的请以Chessboard的为准
    private JButton saveButton;
    private JLabel statusLabel;
    private static ChessGameFrame instance;
    private static Chessboard chessboard;
    JButton button = new JButton("Restart Game");

    public ChessGameFrame(int width, int height) {
        basicInitialize(width, height);

        // 不要改代码的顺序 不然会很难收场！！
        // 已经成屎山代码了
        addSaveButton();
        addLabel();
        addChessboard();
        Chessboard.getInstance().setStatusLabelText("Current Color: " + getChessboard().getCurrentColor().getName());
        addRestartButton();


    }

    public ChessGameFrame(int width, int height, Archive archive) {
        basicInitialize(width, height);

        addSaveButton();
        addLabel();
        addChessboard(archive);
        Chessboard.getInstance().setStatusLabelText("Current Color: " + getChessboard().getCurrentColor().getName());
        addRestartButton();
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
        chessboard.setRestartButton(button);
        chessboard.setStatusLabel(this.statusLabel);
        chessboard.setGameMode(GameMode.PVEHard);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
        this.repaint();
    }

    private void addChessboard(Archive archive) {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, archive);
        chessboard.setStatusLabel(this.statusLabel);
        gameController = new GameController(chessboard);
        chessboard.setGameMode(GameMode.PVP);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
        this.repaint();
    }

    /**
     * 在游戏面板中添加标签
     */
    private JLabel addLabel() {
        statusLabel = new JLabel();
        statusLabel.setLocation(HEIGHT - 30, HEIGHT / 10);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
        return statusLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addRestartButton() {
        button.addActionListener((e) -> {
            new RestartDialog();
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
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

    public void setSaveButtonEnabled(Boolean enabled) {
        saveButton.setEnabled(enabled);
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
}

