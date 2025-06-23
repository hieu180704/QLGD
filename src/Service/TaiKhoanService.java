package Service;

import DAO.TaiKhoanDAO;
import Model.TaiKhoan;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanService {
    private TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanService() {
        this.taiKhoanDAO = new TaiKhoanDAO();
    }

    public boolean addTaiKhoan(TaiKhoan tk) {
        if (tk == null || tk.getTendangnhap() == null || tk.getTendangnhap().trim().isEmpty() ||
            tk.getMatkhau() == null || tk.getMatkhau().trim().isEmpty() ||
            tk.getEmail() == null || tk.getEmail().trim().isEmpty() ||
            tk.getLoaitaikhoan() != 1) {
            return false;
        }

        if (tk.getTendangnhap().length() > 50 || tk.getMatkhau().length() > 50 || tk.getEmail().length() > 100) {
            return false;
        }

        if (taiKhoanDAO.kiemTraTenDangNhap(tk.getTendangnhap())) {
            return false;
        }

        return taiKhoanDAO.insert(tk);
    }

    public boolean updateTaiKhoan(TaiKhoan tk) {
        if (tk == null || tk.getMataikhoan() <= 0 ||
            tk.getMatkhau() == null || tk.getMatkhau().trim().isEmpty() ||
            tk.getEmail() == null || tk.getEmail().trim().isEmpty() ||
            tk.getLoaitaikhoan() != 1) {
            return false;
        }

        if (tk.getMatkhau().length() > 50 || tk.getEmail().length() > 100) {
            return false;
        }

        return taiKhoanDAO.update(tk);
    }

    public boolean deleteTaiKhoan(int maTaiKhoan) {
        if (maTaiKhoan <= 0) {
            return false;
        }
        return taiKhoanDAO.delete(maTaiKhoan);
    }

    public TaiKhoan getTaiKhoanById(int maTaiKhoan) {
        if (maTaiKhoan <= 0) {
            return null;
        }
        return taiKhoanDAO.findById(maTaiKhoan);
    }

    public List<TaiKhoan> getAllTaiKhoan() {
        return taiKhoanDAO.findAll();
    }

    public List<TaiKhoan> findTaiKhoanByUsername(String tenDangNhap) {
        System.out.println("Tìm kiếm với: " + tenDangNhap); // Log
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            return getAllTaiKhoan();
        }
        List<TaiKhoan> allTaiKhoan = taiKhoanDAO.findAll();
        List<TaiKhoan> result = new ArrayList<>();
        for (TaiKhoan tk : allTaiKhoan) {
            if (tk.getTendangnhap() != null && tk.getTendangnhap().toLowerCase().contains(tenDangNhap.toLowerCase())) {
                result.add(tk);
                System.out.println("Tìm thấy: " + tk.getTendangnhap()); // Log
            }
        }
        System.out.println("Kết quả tìm kiếm: " + result.size() + " tài khoản"); // Log
        return result;
    }
}