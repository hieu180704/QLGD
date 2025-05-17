package View.CustomButton;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundBorder extends AbstractBorder {
    private int radius;
    private Color borderColor;  // Màu của viền

    // Constructor nhận 2 tham số: bán kính và màu viền
    public RoundBorder(int radius, Color borderColor) {
        this.radius = radius;
        this.borderColor = borderColor;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(borderColor);  // Sử dụng màu viền được truyền vào
        g2.setStroke(new BasicStroke(2));  // Độ dày viền
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);  // Vẽ viền bo tròn
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);  // Kích thước padding cho viền
    }
}
