package View.Admin;

import javax.swing.*;
import java.awt.*;

public class UserEditPanel extends JPanel {
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JButton btnSave, btnCancel;

    public UserEditPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setMaximumSize(new Dimension(300, 150));
        setPreferredSize(new Dimension(300, 150));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Dùng 1 panel duy nhất với GridBagLayout quản lý 2 dòng
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        formPanel.setAlignmentX(LEFT_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label Tên tài khoản - cột 0, hàng 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblUsername = new JLabel("Tên tài khoản:");
        formPanel.add(lblUsername, gbc);

        // TextField Tên tài khoản - cột 1, hàng 0
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtUsername = new JTextField();
        formPanel.add(txtUsername, gbc);

        // Label Email - cột 0, hàng 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblEmail = new JLabel("Email:");
        formPanel.add(lblEmail, gbc);

        // TextField Email - cột 1, hàng 1
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEmail = new JTextField();
        formPanel.add(txtEmail, gbc);

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

    public void setUserData(String username, String email) {
        txtUsername.setText(username);
        txtEmail.setText(email);
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JButton getBtnCancel() {
        return btnCancel;
    }
}
