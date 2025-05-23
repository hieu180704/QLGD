package Controller.giaidaucontroller;

import View.Admin.QuanLyGiaiDau.DetailGiaiDauPanel;
import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;
import DAO.GiaiDauDAO;
import Model.GiaiDau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DetailGiaiDauController implements java.awt.event.ActionListener {

    private DetailGiaiDauPanel panel;
    private GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
    private QuanLyGiaiDauPanel quanLyGiaiDauPanel;

    public DetailGiaiDauController(DetailGiaiDauPanel panel, QuanLyGiaiDauPanel quanLyGiaiDauPanel) {
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
            JOptionPane.showMessageDialog(panel, "Lỗi: Không có giải đấu để cập nhật!");
            return;
        }

        String ten = panel.getTenGiaiDau();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Vui lòng nhập tên giải đấu!");
            return;
        }

        // Lấy ngày từ DatePicker
        LocalDate ngayTaoLocal = panel.getNgayTao();
        LocalDate ngayBatDauLocal = panel.getNgayBatDau();
        LocalDate ngayKetThucLocal = panel.getNgayKetThuc();

        if (ngayTaoLocal == null || ngayBatDauLocal == null || ngayKetThucLocal == null) {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn đầy đủ ngày tạo, ngày bắt đầu và ngày kết thúc!");
            return;
        }

        // Chuyển LocalDate -> Date
        Date ngayTao = Date.from(ngayTaoLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date ngayBatDau = Date.from(ngayBatDauLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date ngayKetThuc = Date.from(ngayKetThucLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Validate ngày hợp lý
        if (ngayTao.after(ngayBatDau)) {
            JOptionPane.showMessageDialog(panel, "Ngày tạo không được sau ngày bắt đầu!");
            return;
        }
        if (ngayBatDau.after(ngayKetThuc)) {
            JOptionPane.showMessageDialog(panel, "Ngày bắt đầu không được sau ngày kết thúc!");
            return;
        }

        byte[] anh = panel.getAnhGiaiDau();
        if (anh == null || anh.length == 0) {
            int result = JOptionPane.showConfirmDialog(panel, "Bạn chưa chọn ảnh giải đấu, có muốn tiếp tục lưu không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // Cập nhật giá trị vào model
        gd.setTenGiaiDau(ten);
        gd.setNgayTaoGiai(ngayTao);
        gd.setNgayBatDau(ngayBatDau);
        gd.setNgayKetThuc(ngayKetThuc);
        gd.setAnhGiaiDau(anh);

        // Cập nhật vào DB
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
            JOptionPane.showMessageDialog(panel, "Lỗi: Không có giải đấu để xóa!");
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
