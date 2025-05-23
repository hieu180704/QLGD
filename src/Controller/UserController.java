package Controller;

import DAO.UserDAO;
import View.Admin.QuanLyView;
import View.Admin.UserEditPanel;
import Model.UserModel;

import javax.swing.*;

public class UserController {

    private QuanLyView view;
    private UserModel user;
    private UserDAO userDAO = new UserDAO();

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
    String oldUsername = user.getUsername();

    if (newUsername.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Tên tài khoản không được để trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Cập nhật DB trước
    boolean success = userDAO.updateUserInfo(oldUsername, newEmail, newUsername);

    if (success) {
        // Cập nhật model
        user.setUsername(newUsername);
        user.setEmail(newEmail);

        // Cập nhật UI
        view.updateUserInfoDisplay(user);

        JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
        view.showUserEditPanel(false);
    } else {
        JOptionPane.showMessageDialog(view, "Cập nhật thất bại, vui lòng kiểm tra lại!");
    }
}


    private void cancelEdit() {
        view.showUserEditPanel(false);
    }

}
