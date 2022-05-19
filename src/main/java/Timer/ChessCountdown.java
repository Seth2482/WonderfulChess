package Timer;

import View.Chessboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessCountdown {
    private JLabel countdownLabel;
    private int countdownTime = 20;
    private boolean continueToCount = false;
    private javax.swing.Timer counter;

    public ChessCountdown(JLabel countdownLabel) {
        this.countdownLabel = countdownLabel;
        counter = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trigger();
            }
        });
    }

    public void startCount() {
        countdownTime = 20;
        continueToCount = true;
        counter.start();
        updateCountdownLabel();
    }

    public void stopCount() {
        counter.stop();
        countdownTime = 20;
        continueToCount = false;
        updateCountdownLabel();
    }

    public void trigger(){
        countdownTime--;
        updateCountdownLabel();
        if (countdownTime <=0){
            Chessboard.getInstance().swapColor();
            startCount();
        }
    }

    public void updateCountdownLabel() {
        countdownLabel.setText(String.format("%02d:%02d", (int) Math.floor((float) countdownTime / 60), countdownTime % 60));
    }
}
