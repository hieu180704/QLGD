package Controller;

import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;
import View.Admin.QuanLyGiaiDau.ThemGiaiDauPanel;
import View.Admin.QuanLyView;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class QuanLyGiaiDauController implements ActionListener {

    private QuanLyGiaiDauPanel QuanLyGiaiDauPanel;

    public QuanLyGiaiDauController(QuanLyGiaiDauPanel quanLyGiaiDauPanel) {
        this.QuanLyGiaiDauPanel = quanLyGiaiDauPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if (src.equals("Thêm giải đấu")) {
            ThemGiaiDauPanel themPanel = new ThemGiaiDauPanel(QuanLyGiaiDauPanel); // Truyền chính panel quản lý vào để làm mới
            // controller đã được tạo trong ThemGiaiDauPanel constructor rồi, không cần tạo lại ở đây

            JFrame frame = new JFrame("Thêm Giải Đấu");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(themPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

}
