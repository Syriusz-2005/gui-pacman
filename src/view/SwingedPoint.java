package view;

import javax.swing.*;
import java.awt.*;

public class SwingedPoint extends JPanel {
    private final int radius;
    private Color color;
    public SwingedPoint(int radius, Color color) {
        this.radius = radius;
        this.color = color;
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var s = getSize();
        g.setColor(color);
        g.fillOval((s.width - radius) / 2, (s.height - radius) / 2, radius, radius);
    }

    public void setColor(Color c) {
        color = c;
    }
}
