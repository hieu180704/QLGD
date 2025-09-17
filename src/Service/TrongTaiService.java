/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author ntnfa
 */
import DAO.QuocGiaDAO;
import DAO.TrongTaiDAO;
import Model.QuocGia;
import Model.TrongTai;
import java.util.List;

public class TrongTaiService {
    private TrongTaiDAO trongTaiDAO;
    private QuocGiaDAO quocGiaDAO;

    public TrongTaiService() {
        trongTaiDAO = new TrongTaiDAO();
        quocGiaDAO = new QuocGiaDAO();
    }

    public List<TrongTai> getAllTrongTai() {
        return trongTaiDAO.getAllTrongTai();
    }

    public List<QuocGia> getAllQuocGia() {
        return quocGiaDAO.getAllQuocGia();
    }

    public boolean addTrongTai(TrongTai tt) {
        if (tt.getTenTrongTai() == null || tt.getTenTrongTai().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên trọng tài không được để trống!");
        }
        if (tt.getNgaySinh() == null) {
            throw new IllegalArgumentException("Ngày sinh không được để trống!");
        }
        if (tt.getQuocGia() == null || tt.getQuocGia().getMaQuocGia() <= 0) {
            throw new IllegalArgumentException("Quốc gia không hợp lệ!");
        }
        return trongTaiDAO.addTrongTai(tt);
    }

    public boolean updateTrongTai(TrongTai tt) {
        if (tt.getTenTrongTai() == null || tt.getTenTrongTai().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên trọng tài không được để trống!");
        }
        if (tt.getNgaySinh() == null) {
            throw new IllegalArgumentException("Ngày sinh không được để trống!");
        }
        if (tt.getQuocGia() == null || tt.getQuocGia().getMaQuocGia() <= 0) {
            throw new IllegalArgumentException("Quốc gia không hợp lệ!");
        }
        return trongTaiDAO.updateTrongTai(tt);
    }

    public boolean deleteTrongTai(int maTrongTai) {
        if (maTrongTai <= 0) {
            throw new IllegalArgumentException("Mã trọng tài không hợp lệ!");
        }
        return trongTaiDAO.deleteTrongTai(maTrongTai);
    }

    public List<TrongTai> searchTrongTai(String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        return trongTaiDAO.searchTrongTai(keyword);
    }
}