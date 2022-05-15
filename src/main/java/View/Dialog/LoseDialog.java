package View.Dialog;

import Model.ChessColor;
import View.Chessboard;

import javax.swing.*;
import java.awt.*;

public class LoseDialog extends JDialog {
    public LoseDialog(ChessColor color) {
        setLayout(null);
        setModal(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(500, 300, 500, 150);


        JLabel statusLabel = new JLabel();
        statusLabel.setLocation(90, 0);
        statusLabel.setSize(400, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 15));
        if (color == ChessColor.WHITE) {
            statusLabel.setText("Black Player is lose");
        } else {
            statusLabel.setText("White Player is lose");
        }

        add(statusLabel);

        JButton buttonKnow = new JButton("Restart");
        buttonKnow.setFont(new Font("Rockwell", Font.BOLD, 15));
        buttonKnow.setLocation(200, 60);
        buttonKnow.setSize(80, 30);
        buttonKnow.addActionListener((e) -> {
            Chessboard.getInstance().initialAllChess();
            this.dispose();
        });
        add(buttonKnow);
        setVisible(true);
    }
}
