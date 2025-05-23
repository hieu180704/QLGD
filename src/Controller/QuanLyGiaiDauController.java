package Controller;

import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;
import View.Admin.QuanLyGiaiDau.ThemGiaiDauPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyGiaiDauController implements ActionListener {

    private QuanLyGiaiDauPanel quanLyGiaiDauPanel;

    public QuanLyGiaiDauController(QuanLyGiaiDauPanel quanLyGiaiDauPanel) {
        this.quanLyGiaiDauPanel = quanLyGiaiDauPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("Thêm giải đấu".equals(cmd)) {
            openThemGiaiDauDialog();
        }
        // Có thể thêm các xử lý khác nếu cần sau này
    }

    private void openThemGiaiDauDialog() {
        ThemGiaiDauPanel themPanel = new ThemGiaiDauPanel();
        ThemGiaiDauController controller = new ThemGiaiDauController(themPanel, quanLyGiaiDauPanel);

        JFrame frame = new JFrame("Thêm Giải Đấu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(themPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
