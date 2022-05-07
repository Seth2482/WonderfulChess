package View.panels;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private final String COLOR1, COLOR2;

    public GradientPanel(String color1, String color2) {
        this.COLOR1 = color1;
        this.COLOR2 = color2;
    }

    @Override
    protected void paintChildren(Graphics g) {
        // codes from https://www.youtube.com/watch?v=nPFmxtRfhZk&ab_channel=RaVen
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradientPaint = new GradientPaint(0, 0, Color.decode(this.COLOR1), 0, getHeight(), Color.decode(this.COLOR2));
        g2.setPaint(gradientPaint);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        super.paintChildren(g);
    }
}
