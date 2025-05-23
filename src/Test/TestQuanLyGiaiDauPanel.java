package Test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;

public class TestQuanLyGiaiDauPanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Quản lý Giải đấu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            QuanLyGiaiDauPanel panel = new QuanLyGiaiDauPanel();
            frame.setContentPane(panel);
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
