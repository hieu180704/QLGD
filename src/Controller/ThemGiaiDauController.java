package Controller;

import View.Admin.QuanLyGiaiDau.ThemGiaiDauPanel;
import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;
import DAO.GiaiDauDAO;
import Model.GiaiDau;
import Model.TheThuc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ThemGiaiDauController implements java.awt.event.ActionListener {

    private ThemGiaiDauPanel panel;
    private QuanLyGiaiDauPanel quanLyGiaiDauPanel;
    private GiaiDauDAO giaiDauDAO = new GiaiDauDAO();

    public ThemGiaiDauController(ThemGiaiDauPanel panel, QuanLyGiaiDauPanel quanLyGiaiDauPanel) {
        this.panel = panel;
        this.quanLyGiaiDauPanel = quanLyGiaiDauPanel;
        panel.addController(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == panel.getBtnLuu()) {
            themGiaiDau();
        } else if (source == panel.getBtnHuy()) {
            panel.clearForm();
        } else if (source == panel.getBtnQuayLai()) {
            quayLai();
        } else if (source == panel.getBtnChonAnh()) {
            chonAnh();
        }
    }

// Trong method themGiaiDau()
    private void themGiaiDau() {
        String ten = panel.getTenGiaiDau();
        TheThuc tt = panel.getTheThuc();
        LocalDate ngayBatDauLocal = panel.getNgayBatDau();
        LocalDate ngayKetThucLocal = panel.getNgayKetThuc();
        byte[] anhGiaiDau = panel.getAnhGiaiDau();

        if (ten.isEmpty() || tt == null || ngayBatDauLocal == null || ngayKetThucLocal == null || anhGiaiDau == null) {
            JOptionPane.showMessageDialog(panel, "Vui lòng nhập đầy đủ thông tin, bao gồm tên, thể thức, ngày bắt đầu, ngày kết thúc và chọn ảnh!");
            return;
        }

        if (ngayKetThucLocal.isBefore(ngayBatDauLocal)) {
            JOptionPane.showMessageDialog(panel, "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu!");
            return;
        }

        GiaiDau gd = new GiaiDau();
        gd.setTenGiaiDau(ten);
        gd.setAnhGiaiDau(anhGiaiDau);
        gd.setTheThuc(tt); // Sửa: set đối tượng TheThuc luôn
        gd.setNgayTaoGiai(new Date()); // Tự động lấy ngày hiện tại
        gd.setNgayBatDau(Date.from(ngayBatDauLocal.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gd.setNgayKetThuc(Date.from(ngayKetThucLocal.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        boolean ok = giaiDauDAO.insert(gd);
        if (ok) {
            JOptionPane.showMessageDialog(panel, "Thêm giải đấu thành công!");
            panel.clearForm();
            if (quanLyGiaiDauPanel != null) {
                quanLyGiaiDauPanel.loadData();
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Thêm giải đấu thất bại!");
        }
    }

    private void quayLai() {
        Component parent = SwingUtilities.getWindowAncestor(panel);
        if (parent instanceof Window) {
            ((Window) parent).dispose();
        }
    }

    private void chonAnh() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                panel.setAnhGiaiDau(bytes);
                panel.setPreviewImage(ImageIO.read(file));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Không thể đọc ảnh!");
            }
        }
    }
}
