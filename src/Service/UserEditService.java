package Service;

import DAO.TaiKhoanDAO;
import Model.TaiKhoan;

public class UserEditService {
    private TaiKhoanDAO dao;

    public UserEditService() {
        this.dao = new TaiKhoanDAO();
    }

    // Cập nhật thông tin người dùng (email và mật khẩu)
    public String updateUser(TaiKhoan tk) {
        // Kiểm tra đầu vào
        if (tk.getEmail() == null || tk.getEmail().trim().isEmpty()) {
            return "Email không được để trống!";
        }
        if (tk.getMatkhau() == null || tk.getMatkhau().trim().isEmpty()) {
            return "Mật khẩu không được để trống!";
        }

        // Gọi DAO để cập nhật
        boolean success = dao.updateMatKhauVaEmail(tk);
        if (success) {
            return "Cập nhật thông tin thành công!";
        } else {
            return "Cập nhật thông tin thất bại.";
        }
    }

    // Lấy thông tin người dùng theo mã
    public TaiKhoan findById(int maTaiKhoan) {
        return dao.findById(maTaiKhoan);
    }
}