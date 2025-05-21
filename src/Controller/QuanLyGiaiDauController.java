package Controller;

import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;
import View.Admin.QuanLyGiaiDau.ThemGiaiDauPanel;
import View.Admin.QuanLyView;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            // Lấy parent từ panel UI, không phải từ controller
            Component parent = SwingUtilities.getWindowAncestor(QuanLyGiaiDauPanel);
            if (parent instanceof QuanLyView) {
                ((QuanLyView) parent).openThemGiaiDauPanel();
            }
        }
    }

}
