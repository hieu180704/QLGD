package View.CustomButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundBorder {

    public JButton createSidebarButton(String text, int radius, Color bgDefault, Color bgHover) {
    JButton btn = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    };
    btn.setContentAreaFilled(false);
    btn.setOpaque(false);
    btn.setForeground(Color.WHITE);
    btn.setBackground(bgDefault);
    btn.setFocusPainted(false);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btn.setBorder(new EmptyBorder(5, 15, 5, 15));
    btn.setPreferredSize(new Dimension(200, 50));
    btn.setMaximumSize(new Dimension(200, 50));
    btn.setMinimumSize(new Dimension(200, 50));
    btn.setHorizontalAlignment(SwingConstants.CENTER);
    btn.setAlignmentX(Component.CENTER_ALIGNMENT); 

    btn.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            btn.setBackground(bgHover);
            btn.repaint();
        }
        public void mouseExited(MouseEvent e) {
            btn.setBackground(bgDefault);
            btn.repaint();
        }
    });

    return btn;
}

}
