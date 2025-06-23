package Service;

import Model.TaiKhoan;
import DAO.TaiKhoanDAO;
import java.util.prefs.Preferences;

public class LoginService {
    private Preferences prefs;
    private TaiKhoanDAO taiKhoanDAO;

    public LoginService() {
        this.prefs = Preferences.userRoot().node(this.getClass().getName());
        this.taiKhoanDAO = new TaiKhoanDAO();
    }

    // Load thông tin đăng nhập đã lưu từ Preferences
    public TaiKhoan getSavedCredentials() {
        String username = prefs.get("username", "");
        String password = prefs.get("password", "");
        // Tạo đối tượng TaiKhoan với dữ liệu đã lưu
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTendangnhap(username);
        taiKhoan.setMatkhau(password);
        return taiKhoan;
    }

    // Load trạng thái "Ghi nhớ đăng nhập"
    public boolean getRememberStatus() {
        return prefs.getBoolean("remember", false);
    }

    // Lưu thông tin đăng nhập nếu chọn "Ghi nhớ"
    public void saveCredentials(TaiKhoan taiKhoan, boolean remember) {
        if (remember) {
            prefs.put("username", taiKhoan.getTendangnhap());
            prefs.put("password", taiKhoan.getMatkhau());
            prefs.putBoolean("remember", true);
        } else {
            prefs.remove("username");
            prefs.remove("password");
            prefs.putBoolean("remember", false);
        }
    }

    // Kiểm tra đăng nhập bằng cơ sở dữ liệu
    public String login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin!";
        }

        // Sử dụng TaiKhoanDAO để kiểm tra đăng nhập
        TaiKhoan taiKhoan = taiKhoanDAO.findByUsernameAndPassword(username, password);
        if (taiKhoan != null) {
            if (taiKhoan.getLoaitaikhoan() == 1) { // Chỉ cho phép Admin
                return "Đăng nhập thành công!";
            } else {
                return "Chỉ tài khoản Admin được phép đăng nhập!";
            }
        }
        return "Tài khoản hoặc mật khẩu không đúng!";
    }

    // Lấy thông tin người dùng đã đăng nhập từ cơ sở dữ liệu
    public TaiKhoan getLoggedInUser(String username, String password) {
        // Sử dụng TaiKhoanDAO để lấy thông tin người dùng
        return taiKhoanDAO.findByUsernameAndPassword(username, password);
    }
}