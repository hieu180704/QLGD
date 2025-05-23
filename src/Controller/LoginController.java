package controller;

import DAO.TaiKhoanDAO;
import Model.TaiKhoan;

public class LoginController {

    private TaiKhoanDAO taiKhoanDAO;

    public LoginController(TaiKhoanDAO taiKhoanDAO) {
        this.taiKhoanDAO = taiKhoanDAO;
    }

    public TaiKhoan login(String username, String password) {
        // Gọi DAO tìm tài khoản theo username và password
        TaiKhoan taiKhoan = taiKhoanDAO.findByUsernameAndPassword(username, password);

        if (taiKhoan != null) {
            // Có thể thêm kiểm tra thêm, ví dụ tài khoản bị khóa, ...
            return taiKhoan;
        }
        return null; // đăng nhập sai
    }
}
