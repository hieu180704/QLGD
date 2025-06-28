package Controller;

import View.Admin.QuanLyHLV.DanhSachHLVPanel;
import DAO.HLVDAO;
import DAO.QuocGiaDAO;
import DAO.DoiBongDAO;
import Model.HLV;
import View.Admin.QuanLyHLV.HLVPanel;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.DoiBongItem;
import View.Admin.QuanLyHLV.SuaXoaHLVDialog;
import View.Admin.QuanLyHLV.ThemHLVDialog;
import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HLVController {
    private final DanhSachHLVPanel view;
    private final HLVDAO hlvDAO;
    private final QuocGiaDAO quocGiaDAO;
    private final DoiBongDAO doiBongDAO;
    private List<HLV> danhSachHLV;

    public HLVController(DanhSachHLVPanel view, HLVDAO hlvDAO, QuocGiaDAO quocGiaDAO, DoiBongDAO doiBongDAO) {
        this.view = view;
        this.hlvDAO = hlvDAO;
        this.quocGiaDAO = quocGiaDAO;
        this.doiBongDAO = doiBongDAO;

        this.view.addBtnTimKiemListener(e -> {
            try {
                timKiemVaHienThi();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        this.view.addBtnThemHLVListener(e -> {
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
                Logger.getLogger(HLVController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        loadData();
        try {
            hienThiHLV(danhSachHLV);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        danhSachHLV = hlvDAO.findAll();
    }

    public void hienThiHLV(List<HLV> ds) throws IOException {
        JPanel panel = view.getPanelDanhSachHLV();
        panel.removeAll();
        for (HLV hlv : ds) {
            panel.add(new HLVPanel(hlv, this));
        }
        panel.revalidate();
        panel.repaint();
    }

    public void timKiemVaHienThi() throws IOException {
        String key = view.getTimKiemText().toLowerCase().trim();
        if (key.isEmpty()) {
            loadData();
            hienThiHLV(danhSachHLV);
            return;
        }
        List<HLV> dsLoc = danhSachHLV.stream()
                .filter(hlv -> hlv.getTenHLV().toLowerCase().contains(key)
                        || (hlv.getTenQuocGia() != null && hlv.getTenQuocGia().toLowerCase().contains(key))
                        || (hlv.getTenDoi() != null && hlv.getTenDoi().toLowerCase().contains(key)))
                .collect(Collectors.toList());
        hienThiHLV(dsLoc);
    }

    public void showSuaXoaDialog(HLV hlv) {
        List<QuocGiaItem> dsQuocGia = quocGiaDAO.findAll().stream()
                .map(qg -> new QuocGiaItem(qg.getMaQuocGia(), qg.getTenQuocGia()))
                .collect(Collectors.toList());

        List<DoiBongItem> dsDoiBong = doiBongDAO.findAll().stream()
                .map(db -> new DoiBongItem(db.getMaDoiBong(), db.getTenDoi()))
                .collect(Collectors.toList());

        SuaXoaHLVDialog dialog = new SuaXoaHLVDialog(null, hlv, dsQuocGia, dsDoiBong);
        dialog.setVisible(true);

        if (dialog.isUpdated()) {
            HLV updated = dialog.getHLV();
            try {
                updateHLV(updated);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (dialog.isDeleted()) {
            try {
                deleteHLV(hlv.getMaHLV());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateHLV(HLV hlv) throws IOException {
        boolean success = hlvDAO.update(hlv);
        if (success) {
            int index = -1;
            for (int i = 0; i < danhSachHLV.size(); i++) {
                if (danhSachHLV.get(i).getMaHLV() == hlv.getMaHLV()) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                danhSachHLV.set(index, hlv);
                hienThiHLV(danhSachHLV);
                JOptionPane.showMessageDialog(null, "Cập nhật huấn luyện viên thành công");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật huấn luyện viên thất bại");
        }
    }

    public void deleteHLV(int maHLV) throws IOException {
        boolean success = hlvDAO.delete(maHLV);
        if (success) {
            danhSachHLV.removeIf(hlv -> hlv.getMaHLV() == maHLV);
            hienThiHLV(danhSachHLV);
            JOptionPane.showMessageDialog(null, "Xóa huấn luyện viên thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Xóa huấn luyện viên thất bại");
        }
    }

    public void lamMoiDanhSach() throws IOException {
        loadData();
        hienThiHLV(danhSachHLV);
    }

    public void showThemDialog() throws IOException {
        List<QuocGiaItem> dsQuocGia = quocGiaDAO.findAll().stream()
                .map(qg -> new QuocGiaItem(qg.getMaQuocGia(), qg.getTenQuocGia()))
                .collect(Collectors.toList());

        List<DoiBongItem> dsDoiBong = doiBongDAO.findAll().stream()
                .map(db -> new DoiBongItem(db.getMaDoiBong(), db.getTenDoi()))
                .collect(Collectors.toList());

        ThemHLVDialog dialog = new ThemHLVDialog(null, dsQuocGia, dsDoiBong);
        dialog.setVisible(true);

        HLV newHLV = dialog.getHLV();

        if (newHLV != null) {
            boolean success = hlvDAO.insert(newHLV);
            if (success) {
                danhSachHLV.add(newHLV);
                try {
                    hienThiHLV(danhSachHLV);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Thêm huấn luyện viên thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm huấn luyện viên thất bại");
            }
        }
        loadData();
        hienThiHLV(danhSachHLV);
    }
}