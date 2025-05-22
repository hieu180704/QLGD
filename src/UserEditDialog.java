import javax.swing.*;
import java.awt.*;

public class UserEditDialog extends JDialog {
    private JTextField txtUsername;
    private JTextField txtEmail;
    private boolean saved = false;

    public UserEditDialog(Frame owner, String currentUsername, String currentEmail) {
        super(owner, "Chỉnh sửa thông tin người dùng", true);
        setSize(350, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label & Textfield Tên tài khoản
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tên tài khoản:"), gbc);

        txtUsername = new JTextField(currentUsername);
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(txtUsername, gbc);

        // Label & Textfield Email
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);

        txtEmail = new JTextField(currentEmail);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(txtEmail, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Nút Lưu và Hủy
        JPanel btnPanel = new JPanel();
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            String newUsername = txtUsername.getText().trim();
            String newEmail = txtEmail.getText().trim();

            if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên tài khoản không được để trống!");
                return;
            }
            if (!isValidEmail(newEmail)) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ!");
                return;
            }
            saved = true;
            setVisible(false);
        });

        btnCancel.addActionListener(e -> {
            saved = false;
            setVisible(false);
        });
    }

    private boolean isValidEmail(String email) {
        // Check đơn giản: có dấu @ và .
        return email.contains("@") && email.contains(".");
    }

    public boolean isSaved() {
        return saved;
    }

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }
}
