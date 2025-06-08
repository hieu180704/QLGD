package Controller;

import Service.RegisterService;
import ViewMain.RegisterView;
import ViewMain.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterController {
    private RegisterView view;
    private RegisterService registerService;

    public RegisterController(RegisterView view) {
        this.view = view;
        this.registerService = new RegisterService();

        // Thêm sự kiện cho nút Sign Up
        view.getBtnSignUp().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });

        // Thêm sự kiện cho nhãn "Login here"
        view.getLblLogin().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.dispose();
                new LoginView().setVisible(true);
            }
        });
    }

    private void handleSignUp() {
        String tenDangNhap = view.getTfName().getText().trim();
        String email = view.getTfEmail().getText().trim();
        String matKhau = new String(view.getPfPassword().getPassword()).trim();
        String nhapLaiMatKhau = new String(view.getPfRePassword().getPassword()).trim();

        // Kiểm tra placeholder
        if (tenDangNhap.equalsIgnoreCase("username") || view.getTfName().getForeground() == java.awt.Color.GRAY) {
            view.showMessage("Vui lòng nhập tên đăng nhập hợp lệ!");
            return;
        }
        if (email.equalsIgnoreCase("your email") || view.getTfEmail().getForeground() == java.awt.Color.GRAY) {
            view.showMessage("Vui lòng nhập email hợp lệ!");
            return;
        }
        if (matKhau.equalsIgnoreCase("password") || view.getPfPassword().getForeground() == java.awt.Color.GRAY) {
            view.showMessage("Vui lòng nhập mật khẩu hợp lệ!");
            return;
        }
        if (nhapLaiMatKhau.equalsIgnoreCase("repeat your password") || view.getPfRePassword().getForeground() == java.awt.Color.GRAY) {
            view.showMessage("Vui lòng nhập lại mật khẩu hợp lệ!");
            return;
        }

        // Gọi service để đăng ký
        String result = registerService.dangKyTaiKhoan(tenDangNhap, matKhau, nhapLaiMatKhau, email);
        view.showMessage(result);

        // Nếu đăng ký thành công, chuyển sang LoginView
        if (result.equals("Đăng ký thành công!")) {
            view.dispose();
            new LoginView().setVisible(true);
        }
    }
}