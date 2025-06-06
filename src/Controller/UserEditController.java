package Controller;

import DAO.TaiKhoanDAO;
import Model.TaiKhoan;
import View.Admin.QuanLyView;
import View.Admin.UserEditPanel;
//import com.sun.jdi.connect.spi.Connection;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.sql.Connection;

public class UserEditController {

    private QuanLyView view;
    private TaiKhoanDAO taiKhoanDAO;

    public UserEditController(QuanLyView view) {
        this.view = view;
        this.taiKhoanDAO = new TaiKhoanDAO();
        attachEvents();
    }

    private void attachEvents() {
        // Bắt sự kiện click vào label username
        view.getUsernameLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openUserEditDialog();
            }
        });
    }

    private void openUserEditDialog() {
        TaiKhoan user = view.getUserCurrent();
        if (user == null) {
            JOptionPane.showMessageDialog(view, "Chưa có thông tin tài khoản!");
            return;
        }

        UserEditPanel userEditPanel = new UserEditPanel();
        // Đổ dữ liệu hiện tại vào form
        userEditPanel.setUserData(user.getTendangnhap(), user.getEmail(), user.getMatkhau());

        // Tạo dialog modal chứa panel chỉnh sửa user
        JDialog dialog = new JDialog(view, "Chỉnh sửa thông tin tài khoản", true);
        dialog.getContentPane().add(userEditPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(view);

        // Xử lý nút Lưu
        userEditPanel.getBtnSave().addActionListener(ev -> {
            String newEmail = userEditPanel.getEmail();
            String newPassword = userEditPanel.getPassword();

            // Cập nhật model
            user.setEmail(newEmail);
            user.setMatkhau(newPassword);
            
             if (newEmail.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Email và mật khẩu không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật database qua DAO
            boolean success = taiKhoanDAO.update(user);
            if (success) {
                view.updateUserInfoDisplay(user);
                JOptionPane.showMessageDialog(dialog, "Cập nhật thành công!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại!");
            }
        });

        // Xử lý nút Hủy
        userEditPanel.getBtnCancel().addActionListener(ev -> dialog.dispose());

        dialog.setVisible(true);
    }
}
