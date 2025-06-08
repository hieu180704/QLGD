package Service;

import DAO.TaiKhoanDAO;
import Model.TaiKhoan;

public class RegisterService {
    private TaiKhoanDAO taiKhoanDAO;

    public RegisterService() {
        this.taiKhoanDAO = new TaiKhoanDAO();
    }

    public String dangKyTaiKhoan(String tendangnhap, String matkhau, String nhaplai, String email) {
        // Kiểm tra trường rỗng
        if (tendangnhap == null || tendangnhap.trim().isEmpty()) {
            return "Tên đăng nhập không được để trống!";
        }
        if (matkhau == null || matkhau.trim().isEmpty()) {
            return "Mật khẩu không được để trống!";
        }
        if (nhaplai == null || nhaplai.trim().isEmpty()) {
            return "Mật khẩu nhập lại không được để trống!";
        }
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống!";
        }

        // Kiểm tra mật khẩu khớp
        if (!matkhau.equals(nhaplai)) {
            return "Mật khẩu nhập lại không khớp!";
        }

        // Kiểm tra tên đăng nhập đã tồn tại
        if (taiKhoanDAO.kiemTraTenDangNhap(tendangnhap)) {
            return "Tên đăng nhập đã tồn tại!";
        }

        // Kiểm tra định dạng email (cơ bản)
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return "Email không hợp lệ!";
        }

        // Tạo tài khoản mới
        TaiKhoan tk = new TaiKhoan();
        tk.setTendangnhap(tendangnhap.trim());
        tk.setMatkhau(matkhau);
        tk.setEmail(email.trim());
        tk.setLoaitaikhoan(0); // 0 = user thường

        // Thêm vào cơ sở dữ liệu
        boolean ketqua = taiKhoanDAO.insert(tk);
        if (ketqua) {
            return "Đăng ký thành công!";
        } else {
            return "Đăng ký thất bại, vui lòng thử lại!";
        }
    }
}