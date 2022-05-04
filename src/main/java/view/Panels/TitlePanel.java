package view.Panels;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    @Override
    protected void paintChildren(Graphics g) {
        // codes from https://www.youtube.com/watch?v=nPFmxtRfhZk&ab_channel=RaVen
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradientPaint = new GradientPaint(0, 0, Color.decode("#F1F2B5"), 0, getHeight(), Color.decode("#135058"));
        g2.setPaint(gradientPaint);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintChildren(g);
    }
}
