package View.Dialog;

import Model.ChessColor;

import javax.swing.*;
import java.awt.*;

public class KingAttackedDialog extends JDialog {
    public KingAttackedDialog(ChessColor color) {
        setVisible(true);
        setLayout(null);
        setBounds(500, 300, 500, 150);


        JLabel statusLabel = new JLabel();
        statusLabel.setLocation(90, 0);
        statusLabel.setSize(400, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        if (color == ChessColor.WHITE) {
            statusLabel.setText("Black King is going to be attacked");
        } else {
            statusLabel.setText("White King is going to be attacked");
        }

        add(statusLabel);

        JButton buttonKnow = new JButton("I know");
        buttonKnow.setFont(new Font("Rockwell", Font.BOLD, 15));
        buttonKnow.setLocation(200, 60);
        buttonKnow.setSize(80, 30);
        buttonKnow.addActionListener((e) -> {
            this.dispose();
        });
        add(buttonKnow);
    }
}
