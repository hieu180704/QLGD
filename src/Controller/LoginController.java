package Controller;

import Model.TaiKhoan;
import Service.LoginService;
import View.Admin.QuanLyView;
import ViewMain.LoginView;
import ViewMain.RegisterView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginController {
    private LoginView view;
    private LoginService service;

    public LoginController(LoginView view, LoginService service) {
        this.view = view;
        this.service = service;

        // Add button listeners
        view.getLoginButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        view.getRegisterLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterView().setVisible(true);
                view.dispose();
            }
        });

        view.getForgotPasswordLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.showMessage("Liên hệ quản trị viên để đặt lại mật khẩu!");
            }
        });
    }

    private void login() {
        // Lấy dữ liệu từ view
        String username = view.getUsernameField().getText().trim();
        String password = new String(view.getPasswordField().getPassword()).trim();
        boolean remember = view.getRememberCheckBox().isSelected();

        // Gọi service để kiểm tra đăng nhập
        String result = service.login(username, password);
        view.showMessage(result);

        if (result.contains("thành công")) { // Nếu đăng nhập thành công
            // Tạo đối tượng TaiKhoan với dữ liệu nhập vào
            TaiKhoan taiKhoan = new TaiKhoan();
            taiKhoan.setTendangnhap(username);
            taiKhoan.setMatkhau(password);

            // Lưu thông tin qua service
            service.saveCredentials(taiKhoan, remember);

            // Mở QuanLyView và đóng LoginView
            TaiKhoan user = service.getLoggedInUser(username, password);
            new QuanLyView(user.getMataikhoan()).setVisible(true);
            view.dispose();
        }
    }
}