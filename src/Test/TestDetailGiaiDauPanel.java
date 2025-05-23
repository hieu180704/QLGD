package Test;

import Model.GiaiDau;
import View.Admin.QuanLyGiaiDau.DetailGiaiDauPanel;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class TestDetailGiaiDauPanel {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Detail Giai Dau Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            DetailGiaiDauPanel panel = new DetailGiaiDauPanel();

            // Tạo dữ liệu giả lập
            GiaiDau gd = new GiaiDau();
            gd.setMaGiaiDau(1);
            gd.setTenGiaiDau("Giải bóng đá Quốc gia");
            gd.setNgayTaoGiai(Date.from(LocalDate.now().minusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            gd.setNgayBatDau(Date.from(LocalDate.now().minusDays(10).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            gd.setNgayKetThuc(Date.from(LocalDate.now().plusDays(20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            gd.setAnhGiaiDau(null); // Không có ảnh demo

            panel.setCurrentGiaiDau(gd);

            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
