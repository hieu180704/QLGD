package Test;

import Model.GiaiDau;
import Model.TheThuc;
import View.CustomPanel.ItemGiaiDau;
import View.CustomPanel.ItemGiaiDau;

import javax.swing.*;
import java.util.Date;

public class TestItemGiaiDau {
    public static void main(String[] args) {
        // Tạo dữ liệu giả lập
        TheThuc theThuc = new TheThuc();
        theThuc.setMaTheThuc(1);
        theThuc.setTenTheThuc("Thể thức vòng bảng");

        GiaiDau giaiDau = new GiaiDau();
        giaiDau.setTenGiaiDau("Giải bóng đá Hạng Nhất");
        giaiDau.setTheThuc(theThuc);
        giaiDau.setNgayTaoGiai(new Date(System.currentTimeMillis() - 86400000L * 10)); // 10 ngày trước
        giaiDau.setNgayBatDau(new Date(System.currentTimeMillis() - 86400000L * 2));  // 2 ngày trước
        giaiDau.setNgayKetThuc(new Date(System.currentTimeMillis() + 86400000L * 5)); // 5 ngày sau
        giaiDau.setAnhGiaiDau(null); // Không ảnh demo

        // Tạo ItemGiaiDau với sự kiện click (in console)
        ItemGiaiDau item = new ItemGiaiDau(giaiDau, gd -> {
            System.out.println("Clicked: " + gd.getTenGiaiDau());
        });

        // Tạo frame để hiển thị item
        JFrame frame = new JFrame("Test ItemGiaiDau");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(item);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
