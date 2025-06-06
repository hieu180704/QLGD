/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.QuocGiaDAO;
import DAO.TrongTaiDAO;
import Model.QuocGia;
import Model.TrongTai;
import View.Admin.QuanLyTrongTai.FormThemSuaTrongTai;
import View.Admin.QuanLyTrongTai.TrongTaiPanel;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TrongTaiController {
    private TrongTaiPanel panel;
    private TrongTaiDAO trongTaiDAO;
    private QuocGiaDAO quocGiaDAO;
    private FormThemSuaTrongTai formThemSua;

    public TrongTaiController(TrongTaiPanel panel) {
        this.panel = panel;
        this.trongTaiDAO = new TrongTaiDAO();
        this.quocGiaDAO = new QuocGiaDAO();
    }

    public void loadTatCaTrongTai() {
        List<TrongTai> list = trongTaiDAO.getAllTrongTai();
        panel.hienThiDanhSachTrongTai(list);
    }

    public void timKiemTrongTai() {
        String tuKhoa = panel.getTuKhoaTimKiem();
        List<TrongTai> list;
        if (tuKhoa.isEmpty()) {
            list = trongTaiDAO.getAllTrongTai();
        } else {
            list = trongTaiDAO.searchTrongTaiByName(tuKhoa);
        }
        panel.hienThiDanhSachTrongTai(list);
    }

    public void moFormThemTrongTai() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        formThemSua = new FormThemSuaTrongTai(frame, this);
        List<QuocGia> listQG = quocGiaDAO.findAll();
        formThemSua.hienThiQuocGia(listQG);
        formThemSua.hienThiTrongTai(null);
        formThemSua.setVisible(true);
        loadTatCaTrongTai();
    }

    public void moFormSuaTrongTai() {
        int maTT = panel.getMaTrongTaiDangChon();
        if (maTT == -1) {
            panel.hienThongBao("Vui lòng chọn trọng tài để sửa");
            return;
        }
        for (TrongTai tt : trongTaiDAO.getAllTrongTai()) {
            if (tt.getMaTrongTai() == maTT) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                formThemSua = new FormThemSuaTrongTai(frame, this);
                formThemSua.hienThiQuocGia(quocGiaDAO.findAll());
                formThemSua.hienThiTrongTai(tt);
                formThemSua.setVisible(true);
                loadTatCaTrongTai();
                break;
            }
        }
    }

    public void themTrongTai(TrongTai tt) {
        if (trongTaiDAO.insertTrongTai(tt)) {
            panel.hienThongBao("Thêm thành công!");
        } else {
            panel.hienThongBao("Thêm thất bại!");
        }
        loadTatCaTrongTai();
    }

    public void suaTrongTai(TrongTai tt) {
        if (trongTaiDAO.updateTrongTai(tt)) {
            panel.hienThongBao("Cập nhật thành công!");
        } else {
            panel.hienThongBao("Cập nhật thất bại!");
        }
        loadTatCaTrongTai();
    }

    public void xoaTrongTai() {
        int maTT = panel.getMaTrongTaiDangChon();
        if (maTT == -1) {
            panel.hienThongBao("Vui lòng chọn trọng tài để xóa");
            return;
        }
        if (panel.hoiXacNhan("Bạn có chắc chắn muốn xóa trọng tài này?")) {
            if (trongTaiDAO.deleteTrongTai(maTT)) {
                panel.hienThongBao("Xóa thành công!");
            } else {
                panel.hienThongBao("Xóa thất bại!");
            }
            loadTatCaTrongTai();
        }
    }
}

