package view;

import view.Panels.GradientPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JButton gameMode2;
    private JButton gameMode3;
    private JButton gameMode1;

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
                    ArchiveFrame archiveFrame = new ArchiveFrame();
                    archiveFrame.setVisible(true);
                });
            }
        });
    }

    private void createUIComponents() {
        titlePanel = new GradientPanel("#F1F2B5", "#135058");
    }
}
