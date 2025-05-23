package Controller;

import View.Admin.QuanLyGiaiDau.DetailGiaiDauPanel;
import DAO.GiaiDauDAO;
import Model.GiaiDau;
import Model.NhaTaiTro;
import Model.TheThuc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;

public class DetailGiaiDauController implements ActionListener {

    private DetailGiaiDauPanel panel;
    private GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
    private View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel quanLyGiaiDauPanel;

    public DetailGiaiDauController(DetailGiaiDauPanel panel, View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel quanLyGiaiDauPanel) {
        this.panel = panel;
        this.quanLyGiaiDauPanel = quanLyGiaiDauPanel;
        panel.addController(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == panel.getBtnLuu()) {
            saveGiaiDau();
        } else if (src == panel.getBtnXoa()) {
            deleteGiaiDau();
        } else if (src == panel.getBtnQuayLai()) {
            closeWindow();
        } else if (src == panel.getBtnChonAnh()) {
            chooseImage();
        }
    }

    private void saveGiaiDau() {
        GiaiDau gd = panel.getCurrentGiaiDau();
        if (gd == null) {
            return;
        }

        String ten = panel.getTenGiaiDau();
        NhaTaiTro ntt = panel.getNhaTaiTro();
        TheThuc tt = panel.getTheThuc();
        Date ngayTao = panel.getNgayTao();
        Date ngayBatDau = panel.getNgayBatDau();
        byte[] anh = panel.getAnhGiaiDau();

        if (ten.isEmpty() || ntt == null || tt == null || anh == null) {
            JOptionPane.showMessageDialog(panel, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        gd.setTenGiaiDau(ten);
        gd.setMaNTT(ntt.getMaNTT());
        gd.setMaTheThuc(tt.getMaTheThuc());
        gd.setNgayTaoGiai(ngayTao);
        gd.setNgayBatDau(ngayBatDau);
        gd.setAnhGiaiDau(anh);

        boolean ok = giaiDauDAO.update(gd);
        if (ok) {
            JOptionPane.showMessageDialog(panel, "Cập nhật thành công!");
            if (quanLyGiaiDauPanel != null) {
                quanLyGiaiDauPanel.loadData();
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Cập nhật thất bại!");
        }
    }

    private void deleteGiaiDau() {
        GiaiDau gd = panel.getCurrentGiaiDau();
        if (gd == null) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(panel,
                "Bạn có chắc muốn xóa giải đấu này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = giaiDauDAO.delete(gd.getMaGiaiDau());
            if (ok) {
                JOptionPane.showMessageDialog(panel, "Xóa thành công!");
                if (quanLyGiaiDauPanel != null) {
                    quanLyGiaiDauPanel.loadData();
                }
                closeWindow();
            } else {
                JOptionPane.showMessageDialog(panel, "Xóa thất bại!");
            }

        }
    }

    private void closeWindow() {
        Window w = SwingUtilities.getWindowAncestor(panel);
        if (w != null) {
            w.dispose();
        }
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                panel.setAnhGiaiDau(bytes);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Không thể đọc ảnh!");
            }
        }
    }
}
