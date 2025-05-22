package Controller;

import View.Admin.QuanLyGiaiDau.ThemGiaiDauPanel;
import View.Admin.QuanLyView;
import DAO.GiaiDauDAO;
import Model.GiaiDau;
import Model.NhaTaiTro;
import Model.TheThuc;
import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import javax.imageio.ImageIO;

public class ThemGiaiDauController implements java.awt.event.ActionListener {

    private ThemGiaiDauPanel panel;
    private QuanLyGiaiDauPanel quanLyGiaiDauPanel;

    public ThemGiaiDauController(ThemGiaiDauPanel panel, QuanLyGiaiDauPanel quanLyGiaiDauPanel) {
        this.panel = panel;
        this.quanLyGiaiDauPanel = quanLyGiaiDauPanel;
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

    private void themGiaiDau() {
        String ten = panel.getTenGiaiDau();
        NhaTaiTro ntt = panel.getNhaTaiTro();
        TheThuc tt = panel.getTheThuc();
        Date ngayTao = panel.getNgayTao();
        Date ngayBatDau = panel.getNgayBatDau();
        byte[] anhGiaiDau = panel.getAnhGiaiDau();

        if (ten.isEmpty() || ntt == null || tt == null || anhGiaiDau == null) {
            JOptionPane.showMessageDialog(panel, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        GiaiDau gd = new GiaiDau();
        gd.setTenGiaiDau(ten);
        gd.setAnhGiaiDau(anhGiaiDau);
        gd.setMaNTT(ntt.getMaNTT());
        gd.setMaTheThuc(tt.getMaTheThuc());
        gd.setNgayTaoGiai(ngayTao);
        gd.setNgayBatDau(ngayBatDau);

        GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
        boolean ok = giaiDauDAO.insert(gd);
        if (ok) {
            JOptionPane.showMessageDialog(panel, "Thêm giải đấu thành công!");
            panel.clearForm();
            if (quanLyGiaiDauPanel != null) {
                quanLyGiaiDauPanel.loadData();  // BẮT BUỘC PHẢI CÓ DÒNG NÀY!!!
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Thêm giải đấu thất bại!");
        }
    }

    private void quayLai() {
        Component parent = SwingUtilities.getWindowAncestor(panel);
        if (parent instanceof java.awt.Window) {
            ((java.awt.Window) parent).dispose();
        }
    }

    private void chonAnh() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                panel.setAnhGiaiDau(bytes);
                // Load ảnh preview
                panel.setPreviewImage(ImageIO.read(file));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Không thể đọc ảnh!");
            }
        }
    }
}
