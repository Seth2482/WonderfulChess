package View;

import Model.GameMode;
import View.panels.GradientPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JButton gameMode2;
    private JButton gameMode3;
    private JButton gameMode1;
    private JButton loadArchive;

    public WelcomeFrame() {
        setContentPane(mainPanel);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setTitle("Wonderful Chess - 玩得发棋");
        gameMode1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    ChessGameFrame mainFrame = new ChessGameFrame(1000, 760, GameMode.PVP);
                    mainFrame.setVisible(true);

                });
            }
        });
        gameMode2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playWithRobot(GameMode.PVEEasy);
            }
        });
        gameMode3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playWithRobot(GameMode.PVEHard);
            }
        });
        loadArchive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    ArchiveFrame archiveFrame = new ArchiveFrame();
                    archiveFrame.setVisible(true);
                });
            }
        });
    }

    public void playWithRobot(GameMode gameMode) {
        SwingUtilities.invokeLater(() -> {
            dispose();
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760, gameMode);
            mainFrame.setVisible(true);
        });
    }

    private void createUIComponents() {
        titlePanel = new GradientPanel("#F1F2B5", "#135058");
    }
}
