package view.dialogs;


import view.Chessboard;

import javax.swing.*;
import java.awt.*;

public class RestartDialog extends JDialog {
    public RestartDialog() {
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
            Chessboard.getInstance().initialAllChess();
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