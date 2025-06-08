package Controller;

import Model.TaiKhoan;
import Service.UserEditService;
import View.Admin.QuanLyView;
import View.Admin.UserEditPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class UserEditController {
    private QuanLyView view;
    private UserEditService service;

    public UserEditController(QuanLyView view) {
        this.view = view;
        this.service = new UserEditService();
        attachEvents();
    }

    private void attachEvents() {
        // Bắt sự kiện click vào label username để mở form chỉnh sửa
        view.getUsernameLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openUserEditDialog();
            }
        });

        // Gán lại sự kiện cho nút Đăng Xuất (để chỉ đăng xuất)
        view.getBtnDangXuat().removeActionListener(view.getBtnDangXuat().getActionListeners()[0]);
        view.getBtnDangXuat().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(view,
                        "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    view.dispose();
                    new ViewMain.LoginView().setVisible(true);
                }
            }
        });
    }

    private void openUserEditDialog() {
        TaiKhoan currentUser = view.getUserCurrent();
        if (currentUser == null) {
            JOptionPane.showMessageDialog(view, "Chưa có thông tin tài khoản!");
            return;
        }

        UserEditPanel userEditPanel = new UserEditPanel();
        userEditPanel.setUserData(currentUser.getTendangnhap(), currentUser.getEmail(), currentUser.getMatkhau());
        userEditPanel.setUsernameEditable(false);

        JDialog dialog = new JDialog(view, "Chỉnh Sửa Thông Tin Tài Khoản", true);
        dialog.getContentPane().add(userEditPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(view);

        // Xử lý nút Lưu
        userEditPanel.getBtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser.setEmail(userEditPanel.getEmail());
                currentUser.setMatkhau(userEditPanel.getPassword());

                String result = service.updateUser(currentUser);
                JOptionPane.showMessageDialog(dialog, result);
                if (result.contains("thành công")) {
                    view.updateUserInfoDisplay(currentUser);
                    dialog.dispose();
                }
            }
        });

        // Xử lý nút Hủy
        userEditPanel.getBtnCancel().addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}