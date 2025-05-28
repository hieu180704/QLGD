package Controller.giaidaucontroller;

import DAO.DoiBongDAO;
import DAO.DoiBong_TranDauDAO;
import View.Admin.QuanLyGiaiDau.DetailGiaiDauPanel;
import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;
import DAO.GiaiDauDAO;
import DAO.NhaTaiTro_GiaiDauDAO;
import DAO.TranDauDAO;
import Model.GiaiDau;
import Model.TranDau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class DetailGiaiDauController implements java.awt.event.ActionListener {

    private DetailGiaiDauPanel panel;
    private GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
    private DoiBongDAO doiBongDAO = new DoiBongDAO();
    private TranDauDAO tranDauDAO = new TranDauDAO();
    private QuanLyGiaiDauPanel quanLyGiaiDauPanel;

    public DetailGiaiDauController(DetailGiaiDauPanel panel, QuanLyGiaiDauPanel quanLyGiaiDauPanel) {
        this.panel = panel;
        this.quanLyGiaiDauPanel = quanLyGiaiDauPanel;
        panel.addController(this);
        loadDoiBongList();
    }

    // Gọi load dữ liệu đội bóng (2 list)
    public void loadDoiBongList() {
        if (panel.getCurrentGiaiDau() == null) {
            return;
        }

        int maGiaiDau = panel.getCurrentGiaiDau().getMaGiaiDau();

        List<Model.DoiBong> listChuaThamGia = doiBongDAO.findByMaGiaiDau(null);
        List<Model.DoiBong> listDaThamGia = doiBongDAO.findByMaGiaiDau(maGiaiDau);

        panel.setDoiChuaThamGia(listChuaThamGia);
        panel.setDoiDaThamGia(listDaThamGia);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == panel.getBtnThemDoi()) {
            themDoiBongVaoGiai();
        } else if (src == panel.getBtnBoDoi()) {
            boDoiBongKhoiGiai();
        } else if (src == panel.getBtnLuu()) {
            saveGiaiDau();
        } else if (src == panel.getBtnXoa()) {
            deleteGiaiDau();
        } else if (src == panel.getBtnQuayLai()) {
            closeWindow();
        } else if (src == panel.getBtnChonAnh()) {
            chooseImage();
        }
    }

    private void themDoiBongVaoGiai() {
        int maGiaiDau = panel.getCurrentGiaiDau().getMaGiaiDau();

        // Kiểm tra có trận đấu chưa
        if (tranDauDAO.hasMatchInGiaiDau(maGiaiDau)) {
            JOptionPane.showMessageDialog(panel,
                    "Giải đấu đã xếp lịch thi đấu, không thể đăng ký thêm đội bóng mới.",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Integer> selectedIds = panel.getSelectedMaDoiChuaThamGia();
        if (selectedIds.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn đội bóng để thêm.");
            return;
        }
        if (panel.getCurrentGiaiDau() == null) {
            JOptionPane.showMessageDialog(panel, "Giải đấu chưa được chọn.");
            return;
        }

        for (int maDoi : selectedIds) {
            doiBongDAO.updateMaGiaiDau(maDoi, maGiaiDau);
        }
        loadDoiBongList();
    }

    private void boDoiBongKhoiGiai() {
        int maGiaiDau = panel.getCurrentGiaiDau().getMaGiaiDau();

        // Kiểm tra giải đấu đã có trận chưa
        if (tranDauDAO.hasMatchInGiaiDau(maGiaiDau)) {
            JOptionPane.showMessageDialog(panel,
                    "Giải đấu đã xếp lịch thi đấu, không thể bỏ đội khỏi giải.",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Integer> selectedIds = panel.getSelectedMaDoiDaThamGia();
        if (selectedIds.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn đội bóng để bỏ.");
            return;
        }

        for (int maDoi : selectedIds) {
            doiBongDAO.updateMaGiaiDau(maDoi, null);
        }
        loadDoiBongList();
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
        LocalDate ngayBatDauLocal = panel.getNgayBatDau();
        LocalDate ngayKetThucLocal = panel.getNgayKetThuc();

        if (ngayBatDauLocal == null || ngayKetThucLocal == null) {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!");
            return;
        }

        Date ngayBatDau = Date.from(ngayBatDauLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date ngayKetThuc = Date.from(ngayKetThucLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Validate ngày hợp lý
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
                "Bạn có chắc muốn xóa giải đấu này không? Việc này sẽ xóa toàn bộ dữ liệu liên quan như đội bóng, trận đấu và nhà tài trợ.",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int maGiaiDau = gd.getMaGiaiDau();

        try {
            // 1. Cập nhật đội bóng thuộc giải này thành NULL maGiaiDau
            boolean doiBongOk = doiBongDAO.updateMaGiaiDauNull(maGiaiDau);
            if (!doiBongOk) {
                JOptionPane.showMessageDialog(panel, "Lỗi khi cập nhật đội bóng, không thể xóa giải đấu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Xóa các nhà tài trợ liên quan
            NhaTaiTro_GiaiDauDAO nttGiaiDauDAO = new NhaTaiTro_GiaiDauDAO();
            boolean nttOk = nttGiaiDauDAO.deleteByMaGiaiDau(maGiaiDau);
            if (!nttOk) {
                JOptionPane.showMessageDialog(panel, "Lỗi khi xóa nhà tài trợ giải đấu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Lấy danh sách trận đấu thuộc giải này
            List<TranDau> danhSachTranDau = tranDauDAO.findByMaGiaiDau(maGiaiDau);

            DoiBong_TranDauDAO dbtdDAO = new DoiBong_TranDauDAO();

            // 4. Xóa từng trận đấu cùng dữ liệu liên quan trong doibong_trandau
            for (TranDau td : danhSachTranDau) {
                boolean dbtdDelOk = dbtdDAO.deleteByMaTranDau(td.getMaTranDau());
                boolean tranDauDelOk = tranDauDAO.delete(td.getMaTranDau());

                if (!dbtdDelOk || !tranDauDelOk) {
                    JOptionPane.showMessageDialog(panel, "Lỗi khi xóa dữ liệu trận đấu hoặc đội bóng trận đấu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // 5. Cuối cùng xóa giải đấu
            boolean giaiDauDelOk = giaiDauDAO.delete(maGiaiDau);
            if (giaiDauDelOk) {
                JOptionPane.showMessageDialog(panel, "Xóa giải đấu và dữ liệu liên quan thành công!");
                if (quanLyGiaiDauPanel != null) {
                    quanLyGiaiDauPanel.loadData();
                }
                closeWindow();
            } else {
                JOptionPane.showMessageDialog(panel, "Xóa giải đấu thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Lỗi khi xóa giải đấu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
