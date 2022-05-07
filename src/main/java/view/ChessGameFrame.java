package view;

import controller.GameController;
import model.ChessComponent;
import store.archive.Archive;

import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;

    public ChessGameFrame(int width, int height) {
        setTitle("WonderfulChess"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        Chessboard.chessboardInstance.setStatusLabelText("Current Color: " + Chessboard.chessboardInstance.getCurrentColor().getName());
        addLabel();
        addHelloButton();
        addLoadButton();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, new Archive());
        chessboard.setStatusLabel(addLabel());
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
        this.repaint();
    }

    /**
     * 在游戏面板中添加标签
     */
    private JLabel addLabel() {
        JLabel statusLabel = new JLabel();
        statusLabel.setLocation(HEIGTH - 30, HEIGTH / 10);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
        return statusLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {
        JButton button = new JButton("Restart Game");
        button.addActionListener((e) -> {
            new MyDialog();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

}

class MyDialog extends JDialog {
    public MyDialog() {
        setVisible(true);
        setLayout(null);
        setBounds(500, 300, 300, 150);

        JLabel statusLabel = new JLabel("Do you want to restart?");
        statusLabel.setLocation(30, 0);
        statusLabel.setSize(300, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);

        JButton buttonYes = new JButton("Yes");
        buttonYes.setFont(new Font("Rockwell", Font.BOLD, 20));
        buttonYes.setLocation(30, 60);
        buttonYes.setSize(100, 30);
        buttonYes.addActionListener((e) -> {
            Chessboard.chessboardInstance.reInitialAll();
            this.dispose();
        });
        add(buttonYes);

        JButton buttonNo = new JButton("No");
        buttonNo.setFont(new Font("Rockwell", Font.BOLD, 20));
        buttonNo.setLocation(150, 60);
        buttonNo.setSize(100, 30);
        buttonNo.addActionListener((e) -> {
            this.dispose();
        });
        add(buttonNo);
    }
}
