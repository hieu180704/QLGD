package ViewMain;

import Controller.RegisterController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class RegisterView extends JFrame {

    private JTextField tfName;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JPasswordField pfRePassword;
    private JButton btnSignUp;
    private JLabel lblLogin;

    public RegisterView() {
        setTitle("Register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);

        // Background gradient panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color color1 = new Color(95, 99, 204);
                Color color2 = new Color(79, 205, 193);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        // White rounded form panel
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        formPanel.setOpaque(false);
        formPanel.setPreferredSize(new Dimension(500, 350));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Title
        JLabel titleLabel = new JLabel("CREATE ACCOUNT");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        formPanel.add(titleLabel);

        // Text fields
        tfName = createStyledTextField("Username");
        formPanel.add(tfName);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        tfEmail = createStyledTextField("Your Email");
        formPanel.add(tfEmail);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        pfPassword = createStyledPasswordField("Password");
        formPanel.add(pfPassword);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        pfRePassword = createStyledPasswordField("Repeat your password");
        formPanel.add(pfRePassword);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Sign Up button
        btnSignUp = new JButton("SIGN UP") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, new Color(123, 130, 230), 0, getHeight(), new Color(79, 205, 193));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        btnSignUp.setFocusPainted(false);
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setFont(new Font("Arial Black", Font.BOLD, 16));
        btnSignUp.setBorderPainted(false);
        btnSignUp.setContentAreaFilled(false);
        btnSignUp.setOpaque(false);
        btnSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSignUp.setPreferredSize(new Dimension(300, 40));
        btnSignUp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        formPanel.add(btnSignUp);

        // Login link label
        lblLogin = new JLabel("Have already an account? Login here");
        lblLogin.setFont(new Font("Arial", Font.PLAIN, 13));
        lblLogin.setForeground(Color.BLACK);
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogin.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        lblLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        formPanel.add(lblLogin);

        // Add hover effect for login label
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblLogin.setForeground(new Color(50, 100, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblLogin.setForeground(Color.BLACK);
            }
        });

        backgroundPanel.add(formPanel);

        // Rounded JFrame shape
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            }
        });

        // Khởi tạo controller
        new RegisterController(this);
    }

    // Getters
    public JTextField getTfName() {
        return tfName;
    }

    public JTextField getTfEmail() {
        return tfEmail;
    }

    public JPasswordField getPfPassword() {
        return pfPassword;
    }

    public JPasswordField getPfRePassword() {
        return pfRePassword;
    }

    public JButton getBtnSignUp() {
        return btnSignUp;
    }

    public JLabel getLblLogin() {
        return lblLogin;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setForeground(Color.GRAY);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        addPlaceholder(tf, placeholder);
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(79, 205, 193), 2, true),
                        BorderFactory.createEmptyBorder(7, 9, 7, 9)));
                if (tf.getText().equals(placeholder)) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (tf.getText().isEmpty()) {
                    tf.setForeground(Color.GRAY);
                    tf.setText(placeholder);
                }
                tf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)));
            }
        });
        return tf;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField pf = new JPasswordField();
        pf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pf.setForeground(Color.GRAY);
        pf.setEchoChar((char) 0);
        pf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        addPlaceholder(pf, placeholder);
        pf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                pf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(79, 205, 193), 2, true),
                        BorderFactory.createEmptyBorder(7, 9, 7, 9)));
                String pass = new String(pf.getPassword());
                if (pass.equals(placeholder)) {
                    pf.setText("");
                    pf.setForeground(Color.BLACK);
                    pf.setEchoChar('●');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String pass = new String(pf.getPassword());
                if (pass.isEmpty()) {
                    pf.setEchoChar((char) 0);
                    pf.setForeground(Color.GRAY);
                    pf.setText(placeholder);
                }
                pf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)));
            }
        });
        return pf;
    }

    private void addPlaceholder(JTextComponent field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder) && field.getForeground() == Color.GRAY) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('●');
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterView view = new RegisterView();
            view.setVisible(true);
        });
    }
}