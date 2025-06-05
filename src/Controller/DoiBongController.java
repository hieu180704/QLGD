package Controller;

import View.Admin.QuanLyDoiBong.DoiBongPanel;
import View.Admin.QuanLyDoiBong.DanhSachDoiBongPanel;
import View.Admin.QuanLyDoiBong.SuaXoaDoiBongDialog;
import View.Admin.QuanLyDoiBong.ThemDoiBongDialog;
import DAO.DoiBongDAO;
import DAO.QuocGiaDAO;
import DAO.SanVanDongDAO;
import Model.DoiBong;
import Model.QuocGia;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import View.Admin.QuanLyDoiBong.ThemDoiBongDialog.SanVanDongItem;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DoiBongController {

    private final DanhSachDoiBongPanel view;
    private final DoiBongDAO doiBongDAO;
    private final QuocGiaDAO quocGiaDAO;
    private final SanVanDongDAO sanVanDongDAO;
    
    private List<DoiBong> danhSachDoiBong;

    public DoiBongController(DanhSachDoiBongPanel view, DoiBongDAO doiBongDAO, QuocGiaDAO quocGiaDAO, SanVanDongDAO sanVanDongDAO) {
        this.view = view;
        this.doiBongDAO = doiBongDAO;
        this.quocGiaDAO = quocGiaDAO;
        this.sanVanDongDAO = sanVanDongDAO;

        this.view.addBtnTimKiemListener(e -> {
            System.out.println("Button clicked!");
            try {
                timKiemVaHienThi();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        this.view.addBtnThemDoiBongListener(e -> {
            System.out.println("Button clicked!");
            try {
                showThemDialog();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        this.view.addBtnLamMoiListener(e -> {
            System.out.println("Button clicked!");
            try {
                lamMoiDanhSach();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        loadData();
        try {
            hienThiDoiBong(danhSachDoiBong);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public void loadData() {
        danhSachDoiBong = doiBongDAO.findAll();
    }

    public void hienThiDoiBong(List<DoiBong> ds) throws IOException {
        JPanel panel = view.getPanelDanhSachDoiBong();
        panel.removeAll();

        for (DoiBong db : ds) {
            Image logoDoiImage = Toolkit.getDefaultToolkit().createImage(db.getLogoDoi());
            String tenDoi = db.getTenDoi();
            String tenGiaiDau = db.getGiaiDau() != null ? db.getGiaiDau().getTenGiaiDau() : "Không có giải đấu";  

            panel.add(new DoiBongPanel(db, this));  
        }

        panel.revalidate();
        panel.repaint();
    }

    public void timKiemVaHienThi() throws IOException {
        String key = view.getTimKiemText().toLowerCase().trim();
        if (key.isEmpty()) {
            loadData();
            hienThiDoiBong(danhSachDoiBong);
            return;
        }
        List<DoiBong> dsLoc = danhSachDoiBong.stream()
                .filter(db -> db.getTenDoi().toLowerCase().contains(key)
                        || (db.getTenQuocGia() != null && db.getTenQuocGia().toLowerCase().contains(key))
                        || (db.getTenSVD() != null && db.getTenSVD().toLowerCase().contains(key)))
                .collect(Collectors.toList());
        hienThiDoiBong(dsLoc);
    }

    public void showSuaXoaDialog(DoiBong db) {
        List<QuocGia> dsQuocGia = quocGiaDAO.findAll();
        
        List<QuocGiaItem> dsQuocGiaItems = dsQuocGia.stream()
        .map(qg -> new QuocGiaItem(qg.getMaQuocGia(), qg.getTenQuocGia()))  
        .collect(Collectors.toList());
        
        List<SanVanDongItem> dsSanVanDong = sanVanDongDAO.getAllSanVanDong().stream()
            .map(svd -> new SanVanDongItem(svd.getMaSVD(), svd.getTenSVD())) 
            .collect(Collectors.toList());

        SuaXoaDoiBongDialog dialog = new SuaXoaDoiBongDialog(null, db, dsQuocGiaItems, dsSanVanDong);  
        dialog.setVisible(true);

        if (dialog.isUpdated()) {
            DoiBong updated = dialog.getDoiBongSuaXoa();
            try {
                updateDoiBong(updated);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (dialog.isDeleted()) {
            try {
                deleteDoiBong(db.getMaDoiBong());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDoiBong(DoiBong db) throws IOException {
        boolean success = doiBongDAO.update(db);
        if (success) {
            int index = -1;
            for (int i = 0; i < danhSachDoiBong.size(); i++) {
                if (danhSachDoiBong.get(i).getMaDoiBong() == db.getMaDoiBong()) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                danhSachDoiBong.set(index, db);
                hienThiDoiBong(danhSachDoiBong);
                JOptionPane.showMessageDialog(null, "Cập nhật đội bóng thành công");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật đội bóng thất bại");
        }
    }

    public void deleteDoiBong(int maDoiBong) throws IOException {
        boolean success = doiBongDAO.delete(maDoiBong);
        if (success) {
            danhSachDoiBong.removeIf(db -> db.getMaDoiBong() == maDoiBong);
            hienThiDoiBong(danhSachDoiBong);
            JOptionPane.showMessageDialog(null, "Xóa đội bóng thành công");
        } else {
            JOptionPane.showMessageDialog(null, "Xóa đội bóng thất bại");
        }
    }

    public void lamMoiDanhSach() throws IOException {
        loadData();  // tải lại dữ liệu từ DB
        hienThiDoiBong(danhSachDoiBong);  // hiển thị lại tất cả đội bóng
    }

    public void showThemDialog() throws IOException {
        List<QuocGia> dsQuocGia = quocGiaDAO.findAll();
        
        List<QuocGiaItem> dsQuocGiaItems = dsQuocGia.stream()
        .map(qg -> new QuocGiaItem(qg.getMaQuocGia(), qg.getTenQuocGia())) 
        .collect(Collectors.toList());
        
        List<SanVanDongItem> dsSanVanDong = sanVanDongDAO.getAllSanVanDong().stream()
            .map(svd -> new SanVanDongItem(svd.getMaSVD(), svd.getTenSVD())) 
            .collect(Collectors.toList());

        ThemDoiBongDialog dialog = new ThemDoiBongDialog(null, dsQuocGiaItems, dsSanVanDong);  
        dialog.setVisible(true);

        DoiBong newDoiBong = dialog.getDoiBong();

        if (newDoiBong != null) {
            boolean success = doiBongDAO.insert(newDoiBong);
            if (success) {
                danhSachDoiBong.add(newDoiBong);
                try {
                    hienThiDoiBong(danhSachDoiBong);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "Thêm đội bóng thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm đội bóng thất bại");
            }
        }
        loadData();
        hienThiDoiBong(danhSachDoiBong);
    }
}

