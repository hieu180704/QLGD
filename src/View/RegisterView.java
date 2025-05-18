package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterView extends JFrame {
    private JPanel leftPanel, rightPanel;
    private JLabel titleLabel, lbUsername, lbEmail, lbPassword, lbConfirmPassword, lbForgotPass, lbLogin;
    private JTextField txtUsername, txtEmail;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JCheckBox chkRemember;
    private JButton btnRegister;

    public RegisterView() {
        setTitle("Phần Mềm Quản Lý Giải Đấu Bóng Đá - Đăng Ký");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        leftPanel = new JPanel(null);
        leftPanel.setPreferredSize(new Dimension(350, 420));
        leftPanel.setBackground(new Color(245, 245, 245));

        titleLabel = new JLabel("Đăng Ký");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(120, 20, 200, 30);
        leftPanel.add(titleLabel);

        lbUsername = new JLabel("Username");
        lbUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lbUsername.setBounds(40, 70, 100, 25);
        leftPanel.add(lbUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(40, 95, 270, 35);
        leftPanel.add(txtUsername);

        lbEmail = new JLabel("Email");
        lbEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lbEmail.setBounds(40, 140, 100, 25);
        leftPanel.add(lbEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(40, 165, 270, 35);
        leftPanel.add(txtEmail);

        lbPassword = new JLabel("Password");
        lbPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lbPassword.setBounds(40, 210, 100, 25);
        leftPanel.add(lbPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 235, 270, 35);
        leftPanel.add(txtPassword);

        lbConfirmPassword = new JLabel("Confirm Password");
        lbConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lbConfirmPassword.setBounds(40, 280, 130, 25);
        leftPanel.add(lbConfirmPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(40, 305, 270, 35);
        leftPanel.add(txtConfirmPassword);

        btnRegister = new JButton("Đăng Ký");
        btnRegister.setBounds(40, 385, 270, 40);
        btnRegister.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(btnRegister);

        lbLogin = new JLabel("Đã có tài khoản? Đăng nhập");
        lbLogin.setForeground(Color.BLACK);
        lbLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lbLogin.setHorizontalAlignment(SwingConstants.CENTER);
        lbLogin.setBounds(40, 430, 270, 25);
        leftPanel.add(lbLogin);

        BorderLayout borderLayout = new BorderLayout();
        rightPanel = new JPanel(); // căn giữa ngang và dọc
        rightPanel.setPreferredSize(new Dimension(350, 450));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(borderLayout);
        
        JLabel imgLabel = new JLabel(new ImageIcon(getClass().getResource("/Resources/Icon_Ball.png")));
        rightPanel.add(imgLabel , BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        lbLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginView().setVisible(true);
            }
        });

     btnRegister.addMouseListener(new MouseAdapter() {
        private final int animationSteps = 10; // nhiều bước hơn, mượt hơn
        private final int delay = 50;          // 50ms mỗi bước (chậm hơn)
        private Font originalFont = btnRegister.getFont();
        private float originalSize = originalFont.getSize2D();
        private Timer enlargeTimer;
        private Timer shrinkTimer;
        private int step = 0;

         private void animateFontSize(boolean enlarge) {
             if (enlargeTimer != null && enlargeTimer.isRunning()) enlargeTimer.stop();
             if (shrinkTimer != null && shrinkTimer.isRunning()) shrinkTimer.stop();

            step = 0;
            Timer timer = new Timer(delay, null);
             timer.addActionListener(e -> {
            if (step >= animationSteps) {
                ((Timer) e.getSource()).stop();
                return;
            }
            float sizeChange = 4f / animationSteps; // tổng tăng 4pt chia đều
            float newSize = originalSize + (enlarge ? sizeChange * step : 4f - sizeChange * step);
            btnRegister.setFont(originalFont.deriveFont(newSize));
            step++;
        });
        timer.start();

        if (enlarge) {
            enlargeTimer = timer;
        } else {
            shrinkTimer = timer;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        animateFontSize(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        animateFontSize(false);
    }
});

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegisterView().setVisible(true);
        });
    }
}
