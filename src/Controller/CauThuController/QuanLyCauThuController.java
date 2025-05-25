package Controller.CauThuController;

import View.Admin.QuanLyCauThu.QuanLyCauThuView;
import DAO.CauThuDAO;
import DAO.QuocGiaDAO;
import DAO.DoiBongDAO;
import Model.CauThu;
import View.Admin.QuanLyCauThu.ThemCauThuDialog;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.DoiBongItem;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class QuanLyCauThuController {
    private final QuanLyCauThuView view;
    private final CauThuDAO cauThuDAO;
    private final QuocGiaDAO quocGiaDAO;
    private final DoiBongDAO doiBongDAO;

    private List<CauThu> danhSachCauThu;

    public QuanLyCauThuController(QuanLyCauThuView view, CauThuDAO cauThuDAO, QuocGiaDAO quocGiaDAO, DoiBongDAO doiBongDAO) {
        this.view = view;
        this.cauThuDAO = cauThuDAO;
        this.quocGiaDAO = quocGiaDAO;
        this.doiBongDAO = doiBongDAO;

        // Đăng ký sự kiện nút
        this.view.addBtnTimKiemListener(e -> timKiemVaHienThi());
        this.view.addBtnThemCauThuListener(e -> showThemDialog());

        loadData();
        hienThiCauThu(danhSachCauThu);
    }

    public void loadData() {
        danhSachCauThu = cauThuDAO.findAll();
        
    }

    public void hienThiCauThu(List<CauThu> ds) {
        JPanel panel = view.getPanelDanhSachCauThu();
        panel.removeAll();
        for (CauThu ct : ds) {
            panel.add(new View.Admin.QuanLyCauThu.CauThuPanel(ct));
        }
        panel.revalidate();
        panel.repaint();
    }

    public void timKiemVaHienThi() {
        String key = view.getTimKiemText().toLowerCase().trim();
        if (key.isEmpty()) {
            loadData();
            hienThiCauThu(danhSachCauThu);
            return;
        }
        List<CauThu> dsLoc = danhSachCauThu.stream()
                .filter(ct -> ct.getTenCauThu().toLowerCase().contains(key)
                        || (ct.getTenQuocGia() != null && ct.getTenQuocGia().toLowerCase().contains(key))
                        || (ct.getTenDoi() != null && ct.getTenDoi().toLowerCase().contains(key)))
                .collect(Collectors.toList());
        hienThiCauThu(dsLoc);
    }

    public void showThemDialog() {
        // Lấy danh sách quốc gia và đội bóng từ DAO, chuyển thành item cho combobox
        List<QuocGiaItem> dsQuocGia = quocGiaDAO.findAll().stream()
                .map(qg -> new QuocGiaItem(qg.getMaQuocGia(), qg.getTenQuocGia()))
                .collect(Collectors.toList());

        List<DoiBongItem> dsDoiBong = doiBongDAO.findAll().stream()
                .map(db -> new DoiBongItem(db.getMaDoiBong(), db.getTenDoi()))
                .collect(Collectors.toList());

        ThemCauThuDialog dialog = new ThemCauThuDialog(null, dsQuocGia, dsDoiBong);
        dialog.setVisible(true);

        CauThu newCauThu = dialog.getCauThu();

        if (newCauThu != null) {
            boolean success = cauThuDAO.insert(newCauThu);
            if (success) {
                danhSachCauThu.add(newCauThu);
                hienThiCauThu(danhSachCauThu);
                JOptionPane.showMessageDialog(null, "Thêm cầu thủ thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm cầu thủ thất bại");
            }
        }
    }
}
