package Controller;

import DAO.DoiBongDAO;
import DAO.DoiBong_TranDauDAO;
import DAO.GiaiDauDAO;
import DAO.NhaTaiTro_GiaiDauDAO;
import DAO.TranDauDAO;
import Model.DoiBong;
import Model.GiaiDau;
import Model.TranDau;
import View.Admin.QuanLyGiaiDau.DetailGiaiDauDialog;
import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;
import View.Admin.QuanLyGiaiDau.ThemGiaiDauDialog;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

public class QuanLyGiaiDauController implements ActionListener {

    private QuanLyGiaiDauPanel quanLyGiaiDauPanel;

    private ThemGiaiDauDialog themGiaiDauDialog;
    private DetailGiaiDauDialog detailGiaiDauDialog;

    private GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
    private DoiBongDAO doiBongDAO = new DoiBongDAO();
    private TranDauDAO tranDauDAO = new TranDauDAO();

    public QuanLyGiaiDauController(QuanLyGiaiDauPanel panel) {
        this.quanLyGiaiDauPanel = panel;
    }

    public void setDetailGiaiDauDialog(DetailGiaiDauDialog dialog) {
        this.detailGiaiDauDialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Xử lý từ QuanLyGiaiDauPanel
        if (source == quanLyGiaiDauPanel.getBtnThem()) {
            openThemGiaiDauDialog();
            return;
        }

        // Xử lý từ ThemGiaiDauDialog
        if (themGiaiDauDialog != null) {
            if (source == themGiaiDauDialog.getBtnLuu()) {
                themGiaiDau();
            } else if (source == themGiaiDauDialog.getBtnHuy()) {
                themGiaiDauDialog.clearForm();
            } else if (source == themGiaiDauDialog.getBtnQuayLai()) {
                themGiaiDauDialog.dispose();
            } else if (source == themGiaiDauDialog.getBtnChonAnh()) {
                chonAnhThemGiaiDau();
            }
        }

        // Xử lý từ DetailGiaiDauDialog
        if (detailGiaiDauDialog != null) {
            if (source == detailGiaiDauDialog.getBtnLuu()) {
                saveGiaiDau();
            } else if (source == detailGiaiDauDialog.getBtnXoa()) {
                deleteGiaiDau();
            } else if (source == detailGiaiDauDialog.getBtnQuayLai()) {
                detailGiaiDauDialog.dispose();
            } else if (source == detailGiaiDauDialog.getBtnChonAnh()) {
                chonAnhDetailGiaiDau();
            } else if (source == detailGiaiDauDialog.getBtnThemDoi()) {
                themDoiBongVaoGiai();
            } else if (source == detailGiaiDauDialog.getBtnBoDoi()) {
                boDoiBongKhoiGiai();
            }
        }
    }

    // === ThemGiaiDauDialog methods ===
    private void openThemGiaiDauDialog() {
        Window owner = null;
        if (quanLyGiaiDauPanel.getTopLevelAncestor() instanceof Window) {
            owner = (Window) quanLyGiaiDauPanel.getTopLevelAncestor();
        }
        themGiaiDauDialog = new ThemGiaiDauDialog(owner);
        themGiaiDauDialog.addController(this);
        themGiaiDauDialog.setVisible(true);
    }

    private void themGiaiDau() {
        String ten = themGiaiDauDialog.getTenGiaiDau();
        var tt = themGiaiDauDialog.getTheThuc();
        LocalDate ngayBatDauLocal = themGiaiDauDialog.getNgayBatDau();
        LocalDate ngayKetThucLocal = themGiaiDauDialog.getNgayKetThuc();
        LocalDate ngayHienTai = LocalDate.now();
        byte[] anhGiaiDau = themGiaiDauDialog.getAnhGiaiDau();

        if (ten.isEmpty() || tt == null || ngayBatDauLocal == null || ngayKetThucLocal == null || anhGiaiDau == null) {
            JOptionPane.showMessageDialog(themGiaiDauDialog,
                    "Vui lòng nhập đầy đủ thông tin, bao gồm tên, thể thức, ngày bắt đầu, ngày kết thúc và chọn ảnh!");
            return;
        }
        if (ngayKetThucLocal.isBefore(ngayBatDauLocal)) {
            JOptionPane.showMessageDialog(themGiaiDauDialog, "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu!");
            return;
        }
        if (ngayBatDauLocal.isBefore(ngayHienTai)) {
            JOptionPane.showMessageDialog(themGiaiDauDialog, "Ngày bắt đầu không hợp lệ (Quá ngày hiện tại)");
            return;
        }

        GiaiDau gd = new GiaiDau();
        gd.setTenGiaiDau(ten);
        gd.setAnhGiaiDau(anhGiaiDau);
        gd.setTheThuc(tt);
        gd.setNgayTaoGiai(LocalDate.now());
        gd.setNgayBatDau(ngayBatDauLocal);
        gd.setNgayKetThuc(ngayKetThucLocal);
        
        

        boolean ok = giaiDauDAO.insert(gd);
        
        if (ok) {
            JOptionPane.showMessageDialog(themGiaiDauDialog, "Thêm giải đấu thành công!");
            themGiaiDauDialog.clearForm();
            quanLyGiaiDauPanel.loadData();
        } else {
            JOptionPane.showMessageDialog(themGiaiDauDialog, "Thêm giải đấu thất bại!");
        }
    }

    private void chonAnhThemGiaiDau() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(themGiaiDauDialog) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                themGiaiDauDialog.setAnhGiaiDau(bytes);
                themGiaiDauDialog.setPreviewImage(ImageIO.read(file));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(themGiaiDauDialog, "Không thể đọc ảnh!");
            }
        }
    }

    // === DetailGiaiDauDialog methods ===
    public void openDetailGiaiDauDialog(GiaiDau giaiDau) {
        Window owner = null;
        if (quanLyGiaiDauPanel.getTopLevelAncestor() instanceof Window) {
            owner = (Window) quanLyGiaiDauPanel.getTopLevelAncestor();
        }
        detailGiaiDauDialog = new DetailGiaiDauDialog(owner);
        detailGiaiDauDialog.addController(this);
        detailGiaiDauDialog.setCurrentGiaiDau(giaiDau);
        loadDoiBongList();
        detailGiaiDauDialog.setVisible(true);
    }

    private void loadDoiBongList() {
        if (detailGiaiDauDialog == null) {
            System.out.println("detailGiaiDauDialog is null");
            return;
        }
        GiaiDau gd = detailGiaiDauDialog.getCurrentGiaiDau();
        if (gd == null) {
            System.out.println("Current GiaiDau is null");
            return;
        }
        int maGiaiDau = gd.getMaGiaiDau();
        System.out.println("Loading teams for GiaiDau ma: " + maGiaiDau);
        List<DoiBong> listChuaThamGia = doiBongDAO.findByMaGiaiDau(null);
        List<DoiBong> listDaThamGia = doiBongDAO.findByMaGiaiDau(maGiaiDau);

        detailGiaiDauDialog.setDoiChuaThamGia(listChuaThamGia);
        detailGiaiDauDialog.setDoiDaThamGia(listDaThamGia);
    }

    private void themDoiBongVaoGiai() {
        if (detailGiaiDauDialog == null) {
            return;
        }
        GiaiDau gd = detailGiaiDauDialog.getCurrentGiaiDau();
        if (gd == null) {
            return;
        }

        int maGiaiDau = gd.getMaGiaiDau();

        if (tranDauDAO.hasMatchInGiaiDau(maGiaiDau)) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog,
                    "Giải đấu đã xếp lịch thi đấu, không thể đăng ký thêm đội bóng mới.",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Integer> selectedIds = detailGiaiDauDialog.getSelectedMaDoiChuaThamGia();
        if (selectedIds.isEmpty()) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Vui lòng chọn đội bóng để thêm.");
            return;
        }

        for (int maDoi : selectedIds) {
            doiBongDAO.updateMaGiaiDau(maDoi, maGiaiDau);
        }
        loadDoiBongList();
    }

    private void boDoiBongKhoiGiai() {
        if (detailGiaiDauDialog == null) {
            return;
        }
        GiaiDau gd = detailGiaiDauDialog.getCurrentGiaiDau();
        if (gd == null) {
            return;
        }

        int maGiaiDau = gd.getMaGiaiDau();

        if (tranDauDAO.hasMatchInGiaiDau(maGiaiDau)) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog,
                    "Giải đấu đã xếp lịch thi đấu, không thể bỏ đội khỏi giải.",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Integer> selectedIds = detailGiaiDauDialog.getSelectedMaDoiDaThamGia();
        if (selectedIds.isEmpty()) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Vui lòng chọn đội bóng để bỏ.");
            return;
        }

        for (int maDoi : selectedIds) {
            doiBongDAO.updateMaGiaiDau(maDoi, null);
        }
        loadDoiBongList();
    }

    private void saveGiaiDau() {
        if (detailGiaiDauDialog == null) {
            return;
        }
        GiaiDau gd = detailGiaiDauDialog.getCurrentGiaiDau();
        if (gd == null) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Lỗi: Không có giải đấu để cập nhật!");
            return;
        }

        String ten = detailGiaiDauDialog.getTenGiaiDau();
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Vui lòng nhập tên giải đấu!");
            return;
        }

        LocalDate ngayBatDauLocal = detailGiaiDauDialog.getNgayBatDau();
        LocalDate ngayKetThucLocal = detailGiaiDauDialog.getNgayKetThuc();
        LocalDate ngayHienTai = LocalDate.now();

        if (ngayBatDauLocal == null || ngayKetThucLocal == null) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!");
            return;
        }

        if (ngayBatDauLocal.isAfter(ngayKetThucLocal)) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Ngày bắt đầu không được sau ngày kết thúc!");
            return;
        }

        if (ngayBatDauLocal.isBefore(ngayHienTai)) {
            JOptionPane.showMessageDialog(themGiaiDauDialog, "Ngày bắt đầu không hợp lệ (Quá ngày hiện tại)");
            return;
        }

        byte[] anh = detailGiaiDauDialog.getAnhGiaiDau();
        if (anh == null || anh.length == 0) {
            int result = JOptionPane.showConfirmDialog(detailGiaiDauDialog,
                    "Bạn chưa chọn ảnh giải đấu, có muốn tiếp tục lưu không?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }

        gd.setTenGiaiDau(ten);
        gd.setNgayBatDau(ngayBatDauLocal);
        gd.setNgayKetThuc(ngayKetThucLocal);
        gd.setAnhGiaiDau(anh);

        boolean ok = giaiDauDAO.update(gd);
        if (ok) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Cập nhật thành công!");
            if (quanLyGiaiDauPanel != null) {
                quanLyGiaiDauPanel.loadData();
            }
        } else {
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Cập nhật thất bại!");
        }
    }

    private void deleteGiaiDau() {
        if (detailGiaiDauDialog == null) {
            return;
        }
        GiaiDau gd = detailGiaiDauDialog.getCurrentGiaiDau();
        if (gd == null) {
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Lỗi: Không có giải đấu để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(detailGiaiDauDialog,
                "Bạn có chắc muốn xóa giải đấu này không? Việc này sẽ xóa toàn bộ dữ liệu liên quan như đội bóng, trận đấu và nhà tài trợ.",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int maGiaiDau = gd.getMaGiaiDau();

        try {
            boolean doiBongOk = doiBongDAO.updateMaGiaiDauNull(maGiaiDau);
            if (!doiBongOk) {
                JOptionPane.showMessageDialog(detailGiaiDauDialog, "Lỗi khi cập nhật đội bóng, không thể xóa giải đấu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            NhaTaiTro_GiaiDauDAO nttGiaiDauDAO = new NhaTaiTro_GiaiDauDAO();
            boolean nttOk = nttGiaiDauDAO.deleteByMaGiaiDau(maGiaiDau);
            if (!nttOk) {
                JOptionPane.showMessageDialog(detailGiaiDauDialog, "Lỗi khi xóa nhà tài trợ giải đấu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<TranDau> danhSachTranDau = tranDauDAO.findByMaGiaiDau(maGiaiDau);
            DoiBong_TranDauDAO dbtdDAO = new DoiBong_TranDauDAO();

            for (TranDau td : danhSachTranDau) {
                boolean dbtdDelOk = dbtdDAO.deleteByMaTranDau(td.getMaTranDau());
                boolean tranDauDelOk = tranDauDAO.delete(td.getMaTranDau());
                if (!dbtdDelOk || !tranDauDelOk) {
                    JOptionPane.showMessageDialog(detailGiaiDauDialog, "Lỗi khi xóa dữ liệu trận đấu hoặc đội bóng trận đấu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            boolean giaiDauDelOk = giaiDauDAO.delete(maGiaiDau);
            if (giaiDauDelOk) {
                JOptionPane.showMessageDialog(detailGiaiDauDialog, "Xóa giải đấu và dữ liệu liên quan thành công!");
                if (quanLyGiaiDauPanel != null) {
                    quanLyGiaiDauPanel.loadData();
                }
                detailGiaiDauDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(detailGiaiDauDialog, "Xóa giải đấu thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(detailGiaiDauDialog, "Lỗi khi xóa giải đấu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chonAnhDetailGiaiDau() {
        if (detailGiaiDauDialog == null) {
            return;
        }
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(detailGiaiDauDialog) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                detailGiaiDauDialog.setAnhGiaiDau(bytes);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(detailGiaiDauDialog, "Không thể đọc ảnh!");
            }
        }
    }
}
