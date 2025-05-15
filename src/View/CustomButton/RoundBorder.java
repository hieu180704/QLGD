package View.CustomButton;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundBorder extends AbstractBorder{
    private int radius;

    public RoundBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE); // màu viền
        g2.setStroke(new BasicStroke(2)); // độ dày viền
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = 8;
        insets.top = insets.bottom = 4;
        return insets;
    }
}
