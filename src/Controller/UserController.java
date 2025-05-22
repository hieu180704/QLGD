package Controller;

import View.Admin.QuanLyView;
import View.Admin.UserEditPanel;
import Model.UserModel;

import javax.swing.*;

public class UserController {
    private QuanLyView view;
    private UserModel user;

    public UserController(QuanLyView view, UserModel model) {
        this.view = view;
        this.user = model;

        initEventHandlers();
    }

    private void initEventHandlers() {
        UserEditPanel editPanel = view.getUserEditPanel();

        editPanel.getBtnSave().addActionListener(e -> saveUserInfo());
        editPanel.getBtnCancel().addActionListener(e -> cancelEdit());
    }

    public void openUserEditPanel() {
        UserEditPanel editPanel = view.getUserEditPanel();
        editPanel.setUserData(user.getUsername(), user.getEmail());
        view.showUserEditPanel(true);
    }

    private void saveUserInfo() {
        UserEditPanel editPanel = view.getUserEditPanel();

        String newUsername = editPanel.getUsername();
        String newEmail = editPanel.getEmail();

        // Validate dữ liệu nếu cần
        if (newUsername.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên tài khoản không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cập nhật model
        user.setUsername(newUsername);
        user.setEmail(newEmail);

        // Cập nhật view
        view.updateUserInfoDisplay();

        // Ẩn panel edit
        view.showUserEditPanel(false);

        JOptionPane.showMessageDialog(view, "Cập nhật thông tin thành công!");
    }

    private void cancelEdit() {
        view.showUserEditPanel(false);
    }
}
