package Controller.CauThuController;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

import DAO.CauThuDAO;
import Model.CauThu;
import View.Admin.QuanLyCauThu.QuanLyCauThuView;
import View.Admin.QuanLyCauThu.CauThuPanel;  // nhớ import class panel
import View.Admin.QuanLyCauThu.ThemCauThuDialog;

public class QuanLyCauThuController {
    private QuanLyCauThuView view;
    private CauThuDAO dao;
    private List<CauThu> danhSachCauThu;

    public QuanLyCauThuController(QuanLyCauThuView view, CauThuDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    public void loadData() {
        danhSachCauThu = dao.findAll();  // dùng findAll trong DAO chuẩn
    }

    public List<CauThu> getDanhSachCauThu() {
        return danhSachCauThu;
    }

    public void hienThiCauThu(List<CauThu> ds) {
        JPanel panel = view.getPanelDanhSachCauThu();
        panel.removeAll();
        for (CauThu ct : ds) {
            panel.add(new CauThuPanel(ct));
        }
        panel.revalidate();
        panel.repaint();
    }

    public void timKiemVaHienThi() {
        String ten = view.getTimKiemTen().toLowerCase();
        String quocGia = view.getTimKiemQuocTich().toLowerCase();
        String doi = view.getTimKiemDoi().toLowerCase();

        List<CauThu> dsLoc = danhSachCauThu.stream()
            .filter(ct -> ct.getTenCauThu().toLowerCase().contains(ten)
                    && ct.getTenQuocGia() != null && ct.getTenQuocGia().toLowerCase().contains(quocGia)
                    && ct.getTenDoi() != null && ct.getTenDoi().toLowerCase().contains(doi))
            .collect(Collectors.toList());

        hienThiCauThu(dsLoc);
    }

    public void showThemDialog() {
        ThemCauThuDialog dialog = new ThemCauThuDialog(null);
        dialog.setVisible(true);
        CauThu newCauThu = dialog.getCauThu();
        if (newCauThu != null) {
            boolean added = dao.insert(newCauThu); // dùng insert theo DAO bạn gửi
            if (added) {
                danhSachCauThu.add(newCauThu);
                hienThiCauThu(danhSachCauThu);
            } else {
                JOptionPane.showMessageDialog(null, "Thêm cầu thủ thất bại");
            }
        }
    }
}
