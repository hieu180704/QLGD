package Controller.CauThuController;

import View.Admin.QuanLyCauThu.QuanLyCauThuView;
import DAO.CauThuDAO;
import DAO.QuocGiaDAO;
import DAO.DoiBongDAO;
import Model.CauThu;
import View.Admin.QuanLyCauThu.CauThuPanel;
import View.Admin.QuanLyCauThu.SuaXoaCauThuDialog;
import View.Admin.QuanLyCauThu.ThemCauThuDialog;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.DoiBongItem;
import java.io.IOException;

import javax.swing.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
      ;


        // Đăng ký sự kiện nút
        this.view.addBtnTimKiemListener(e -> {
            try {
                timKiemVaHienThi();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        this.view.addBtnThemCauThuListener(e -> {
            try {
                showThemDialog();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        this.view.addBtnLamMoiListener(e -> {
            try {
                lamMoiDanhSach();
            } catch (IOException ex) {
                Logger.getLogger(QuanLyCauThuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        loadData();
        try {
            hienThiCauThu(danhSachCauThu);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public void loadData() {
        danhSachCauThu = cauThuDAO.findAll();
    }

    public void hienThiCauThu(List<CauThu> ds) throws IOException {
        JPanel panel = view.getPanelDanhSachCauThu();
        panel.removeAll();
        for (CauThu ct : ds) {
            panel.add(new CauThuPanel(ct, this));  // truyền this để xử lý update, delete
        }
        panel.revalidate();
        panel.repaint();
    }



    public void timKiemVaHienThi() throws IOException {
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
    
    public void showSuaXoaDialog(CauThu ct) {
        List<QuocGiaItem> dsQuocGia = quocGiaDAO.findAll().stream()
                .map(qg -> new QuocGiaItem(qg.getMaQuocGia(), qg.getTenQuocGia()))
                .collect(Collectors.toList());

        List<DoiBongItem> dsDoiBong = doiBongDAO.findAll().stream()
                .map(db -> new DoiBongItem(db.getMaDoiBong(), db.getTenDoi()))
                .collect(Collectors.toList());

        SuaXoaCauThuDialog dialog = new SuaXoaCauThuDialog(null, ct, dsQuocGia, dsDoiBong);
        dialog.setVisible(true);

        if (dialog.isUpdated()) {
            CauThu updated = dialog.getCauThuSuaXoa();
            try {
                updateCauThu(updated);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (dialog.isDeleted()) {
            try {
                deleteCauThu(ct.getMaCauThu());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateCauThu(CauThu ct) throws IOException {
    boolean success = cauThuDAO.update(ct);
    if (success) {
        int index = -1;
        for (int i = 0; i < danhSachCauThu.size(); i++) {
            if (danhSachCauThu.get(i).getMaCauThu() == ct.getMaCauThu()) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            danhSachCauThu.set(index, ct);
            hienThiCauThu(danhSachCauThu);
            JOptionPane.showMessageDialog(null, "Cập nhật cầu thủ thành công");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Cập nhật cầu thủ thất bại");
        }
    }

    public void deleteCauThu(int maCauThu) throws IOException {
        boolean success = cauThuDAO.delete(maCauThu);
        if (success) {
            danhSachCauThu.removeIf(ct -> ct.getMaCauThu() == maCauThu);
            hienThiCauThu(danhSachCauThu);
            JOptionPane.showMessageDialog(null, "Xóa cầu thủ thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Xóa cầu thủ thất bại");
        }
    }

    
    public void lamMoiDanhSach() throws IOException {
    loadData();  // tải lại dữ liệu từ DB
    hienThiCauThu(danhSachCauThu);  // hiển thị lại tất cả cầu thủ
    }

    public void showThemDialog() {
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
                try {
                    hienThiCauThu(danhSachCauThu);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Thêm cầu thủ thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm cầu thủ thất bại");
            }
        }
    }
}

