package View.Admin.QuanLyTaiKhoan;

import javax.swing.*;
import java.awt.*;

public class FormThemSuaTaiKhoan extends JPanel {
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnSave;
    private JButton btnCancel;

    public FormThemSuaTaiKhoan() {
        setLayout(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridLayout(3,2,10,10));

        form.add(new JLabel("Tên đăng nhập:"));
        txtUsername = new JTextField();
        form.add(txtUsername);

        form.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        form.add(txtEmail);

        form.add(new JLabel("Mật khẩu:"));
        txtPassword = new JPasswordField();
        form.add(txtPassword);

        add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);
    }

    // Các hàm getter dữ liệu nhập
    public String getUsername() {
        return txtUsername.getText().trim();
    }
    public String getEmail() {
        return txtEmail.getText().trim();
    }
    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    // Cho phép set dữ liệu khi sửa
    public void setUserData(String username, String email, String password) {
        txtUsername.setText(username);
        txtEmail.setText(email);
        txtPassword.setText(password);
    }

    // Cho phép khóa hoặc mở trường username (khi sửa thì khóa)
    public void setUsernameEditable(boolean editable) {
        txtUsername.setEditable(editable);
    }

    // Getters cho nút xử lý
    public JButton getBtnSave() { return btnSave; }
    public JButton getBtnCancel() { return btnCancel; }
}
