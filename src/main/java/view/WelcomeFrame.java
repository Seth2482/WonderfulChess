package view;

import view.Panels.TitlePanel;

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

        setTitle("Wonderful Chess - 玩得发棋");
        gameMode1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
                    mainFrame.setVisible(true);
                });
            }
        });
    }

    public void closeThis(){
        dispose();
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
        titlePanel = new TitlePanel();
    }
}
