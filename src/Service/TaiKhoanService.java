package Service;

import DAO.TaiKhoanDAO;
import Model.TaiKhoan;
import java.util.List;
import java.util.stream.Collectors;

public class TaiKhoanService {
    private TaiKhoanDAO dao;

    public TaiKhoanService() {
        this.dao = new TaiKhoanDAO();
    }

    // Lấy tất cả tài khoản
    public List<TaiKhoan> getAllTaiKhoan() {
        return dao.findAll();
    }

    // Thêm tài khoản mới
    public String addTaiKhoan(TaiKhoan tk) {
        // Kiểm tra đầu vào
        if (tk.getTendangnhap() == null || tk.getTendangnhap().trim().isEmpty()) {
            return "Tên đăng nhập không được để trống!";
        }
        if (tk.getEmail() == null || tk.getEmail().trim().isEmpty()) {
            return "Email không được để trống!";
        }
        if (tk.getMatkhau() == null || tk.getMatkhau().trim().isEmpty()) {
            return "Mật khẩu không được để trống!";
        }

        // Kiểm tra tên đăng nhập đã tồn tại
        if (dao.kiemTraTenDangNhap(tk.getTendangnhap())) {
            return "Tên đăng nhập đã tồn tại!";
        }

        // Gọi DAO để thêm
        boolean success = dao.insert(tk);
        if (success) {
            return "Thêm tài khoản thành công!";
        } else {
            return "Thêm tài khoản thất bại.";
        }
    }

    // Cập nhật tài khoản
    public String updateTaiKhoan(TaiKhoan tk) {
        // Kiểm tra đầu vào
        if (tk.getEmail() == null || tk.getEmail().trim().isEmpty()) {
            return "Email không được để trống!";
        }
        if (tk.getMatkhau() == null || tk.getMatkhau().trim().isEmpty()) {
            return "Mật khẩu không được để trống!";
        }

        // Gọi DAO để cập nhật
        boolean success = dao.update(tk);
        if (success) {
            return "Cập nhật tài khoản thành công!";
        } else {
            return "Cập nhật tài khoản thất bại.";
        }
    }

    // Xóa tài khoản
    public String deleteTaiKhoan(int maTaiKhoan) {
        boolean success = dao.delete(maTaiKhoan);
        if (success) {
            return "Xóa tài khoản thành công!";
        } else {
            return "Xóa tài khoản thất bại.";
        }
    }

    // Tìm kiếm tài khoản theo từ khóa
    public List<TaiKhoan> searchTaiKhoan(String keyword) {
        List<TaiKhoan> list = dao.findAll();
        if (keyword == null || keyword.trim().isEmpty()) {
            return list;
        }
        return list.stream()
                .filter(tk -> tk.getTendangnhap().toLowerCase().contains(keyword.toLowerCase())
                        || tk.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Tìm tài khoản theo mã
    public TaiKhoan findById(int maTaiKhoan) {
        return dao.findById(maTaiKhoan);
    }
}