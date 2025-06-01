package View.Admin.QuanLyCauThu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ImageCircleLabel extends JLabel {
    private Image img;

    public void setImage(Image image) {
        this.img = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            Ellipse2D clip = new Ellipse2D.Float(0, 0, 90, 90);
            g2.setClip(clip);
            g2.drawImage(img, 0, 0, 90, 90, null);
            g2.dispose();
        } else {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.fillOval(0, 0, 90, 90);
            g2.dispose();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(90, 90);
    }
}
