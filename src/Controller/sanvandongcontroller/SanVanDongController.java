package Controller.sanvandongcontroller;

import DAO.LoaiSanDAO;
//import DAO.SanVanDongDAO;
import Model.LoaiSan;
import Model.SanVanDong;
import View.Admin.QuanLySanVanDong.FormThemSuaSanVanDong;
import View.Admin.SanVanDongPanel;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SanVanDongController {
    private SanVanDongPanel panel;
//    private SanVanDongDAO sanVanDongDAO;
    private LoaiSanDAO loaiSanDAO;
    private FormThemSuaSanVanDong formThemSua;

    public SanVanDongController(SanVanDongPanel panel) {
        this.panel = panel;
//        this.sanVanDongDAO = new SanVanDongDAO();
        this.loaiSanDAO = new LoaiSanDAO();
    }

    public void loadTatCaSanVanDong() {
//        List<SanVanDong> list = sanVanDongDAO.getAllSanVanDong();
//        panel.hienThiDanhSachSanVanDong(list);
    }

    public void timKiemSanVanDong() {
        String tuKhoa = panel.getTuKhoaTimKiem();
        List<SanVanDong> list;
        if (tuKhoa.isEmpty()) {
//            list = sanVanDongDAO.getAllSanVanDong();
        } else {
//            list = sanVanDongDAO.searchSanVanDongByName(tuKhoa);
        }
//        panel.hienThiDanhSachSanVanDong(list);
    }

    public void moFormThemSanVanDong() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        formThemSua = new FormThemSuaSanVanDong(frame, this);
        List<LoaiSan> listLS = loaiSanDAO.getAllLoaiSan();
        formThemSua.hienThiLoaiSan(listLS);
        formThemSua.hienThiSanVanDong(null);
        formThemSua.setVisible(true);
        loadTatCaSanVanDong();
    }

    public void moFormSuaSanVanDong() {
        int maSVD = panel.getMaSanVanDongDangChon();
        if (maSVD == -1) {
            panel.hienThongBao("Vui lòng chọn sân vận động để sửa");
            return;
        }
//        for (SanVanDong svd : sanVanDongDAO.getAllSanVanDong()) {
//            if (svd.getMaSVD()== maSVD) {
//                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
//                formThemSua = new FormThemSuaSanVanDong(frame, this);
//                formThemSua.hienThiLoaiSan(loaiSanDAO.getAllLoaiSan());
//                formThemSua.hienThiSanVanDong(svd);
//                formThemSua.setVisible(true);
//                loadTatCaSanVanDong();
//                break;
//            }
//        }
    }

    public void themSanVanDong(SanVanDong svd) {
//        if (sanVanDongDAO.insertSanVanDong(svd)) {
//            panel.hienThongBao("Thêm thành công!");
//        } else {
//            panel.hienThongBao("Thêm thất bại!");
//        }
//        loadTatCaSanVanDong();
    }

    public void suaSanVanDong(SanVanDong svd) {
//        if (sanVanDongDAO.updateSanVanDong(svd)) {
//            panel.hienThongBao("Cập nhật thành công!");
//        } else {
//            panel.hienThongBao("Cập nhật thất bại!");
//        }
//        loadTatCaSanVanDong();
    }

    public void xoaSanVanDong() {
        int maSVD = panel.getMaSanVanDongDangChon();
        if (maSVD == -1) {
            panel.hienThongBao("Vui lòng chọn sân vận động để xóa");
            return;
        }
//        if (panel.hoiXacNhan("Bạn có chắc chắn muốn xóa sân vận động này?")) {
//            if (sanVanDongDAO.deleteSanVanDong(maSVD)) {
//                panel.hienThongBao("Xóa thành công!");
//            } else {
//                panel.hienThongBao("Xóa thất bại!");
//            }
//            loadTatCaSanVanDong();
//        }
    }
}
