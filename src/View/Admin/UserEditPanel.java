package View.Admin;

import javax.swing.*;
import java.awt.*;

public class UserEditPanel extends JPanel {
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField pfPassword;  // Thêm field mật khẩu
    private JButton btnSave, btnCancel;

    public UserEditPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setMaximumSize(new Dimension(300, 180)); // tăng chiều cao để chứa mật khẩu
        setPreferredSize(new Dimension(300, 180));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90)); // tăng chiều cao
        formPanel.setAlignmentX(LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label và TextField Tên tài khoản (khóa không sửa)
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Tên tài khoản:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtUsername = new JTextField();
        txtUsername.setEditable(false); // KHÓA tên tài khoản
        formPanel.add(txtUsername, gbc);

        // Label và TextField Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtEmail = new JTextField();
        formPanel.add(txtEmail, gbc);

        // Label và PasswordField Mật khẩu
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        pfPassword = new JPasswordField();
        formPanel.add(pfPassword, gbc);

        // Panel nút Lưu Hủy
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        btnPanel.setAlignmentX(LEFT_ALIGNMENT);

        add(formPanel);
        add(Box.createVerticalStrut(10));
        add(btnPanel);
    }

    public void setUserData(String username, String email, String password) {
        txtUsername.setText(username);
        txtEmail.setText(email);
        pfPassword.setText(password);
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getPassword() {
        return new String(pfPassword.getPassword());
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }
}
