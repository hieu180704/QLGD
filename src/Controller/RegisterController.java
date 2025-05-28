package controller;

import DAO.TaiKhoanDAO;
import Model.TaiKhoan;

public class RegisterController {

    private TaiKhoanDAO taiKhoanDAO;

    public RegisterController() {
        taiKhoanDAO = new TaiKhoanDAO();
    }

    public String dangKyTaiKhoan(String tendangnhap, String matkhau, String nhaplai, String email) {
        if (tendangnhap == null || tendangnhap.isEmpty()) return "Tên đăng nhập không được để trống!";
        if (matkhau == null || matkhau.isEmpty()) return "Mật khẩu không được để trống!";
        if (!matkhau.equals(nhaplai)) return "Mật khẩu nhập lại không khớp!";
        if (email == null || email.isEmpty()) return "Email không được để trống!";

        if (taiKhoanDAO.kiemTraTenDangNhap(tendangnhap)) {
            return "Tên đăng nhập đã tồn tại!";
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setTendangnhap(tendangnhap);
        tk.setMatkhau(matkhau);
        tk.setEmail(email);
        tk.setLoaitaikhoan(0); // 0 = user thường

        boolean ketqua = taiKhoanDAO.insert(tk);
        if (ketqua) {
            return "Đăng ký thành công!";
        } else {
            return "Đăng ký thất bại, vui lòng thử lại!";
        }
    }
}
