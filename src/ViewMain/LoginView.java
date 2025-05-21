package ViewMain;

import Model.TaiKhoan;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class LoginView extends javax.swing.JFrame {

    private RegisterView registerView;

    public LoginView() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoginTop = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LoginBot = new javax.swing.JPanel();
        Username = new javax.swing.JPanel();
        Under = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Password = new javax.swing.JPanel();
        lbPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        btnDangnhap = new javax.swing.JButton();
        lbRegister = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lbUsername = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Phần Mềm Quản Lý Giải Đấu Bóng Đá");

        LoginTop.setPreferredSize(new java.awt.Dimension(0, 70));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Đăng Nhập ");

        javax.swing.GroupLayout LoginTopLayout = new javax.swing.GroupLayout(LoginTop);
        LoginTop.setLayout(LoginTopLayout);
        LoginTopLayout.setHorizontalGroup(
            LoginTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginTopLayout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(jLabel1)
                .addContainerGap(344, Short.MAX_VALUE))
        );
        LoginTopLayout.setVerticalGroup(
            LoginTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginTopLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        getContentPane().add(LoginTop, java.awt.BorderLayout.PAGE_START);

        LoginBot.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Username.setPreferredSize(new java.awt.Dimension(250, 100));

        javax.swing.GroupLayout UsernameLayout = new javax.swing.GroupLayout(Username);
        Username.setLayout(UsernameLayout);
        UsernameLayout.setHorizontalGroup(
            UsernameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );
        UsernameLayout.setVerticalGroup(
            UsernameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        LoginBot.add(Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(-70, 20, 230, 60));

        Under.setPreferredSize(new java.awt.Dimension(150, 30));
        Under.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 102));
        jLabel2.setText("Quên Mật Khẩu?");
        Under.add(jLabel2, java.awt.BorderLayout.EAST);

        LoginBot.add(Under, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 230, 30));

        Password.setPreferredSize(new java.awt.Dimension(250, 100));

        lbPassword.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbPassword.setText("Password:");

        javax.swing.GroupLayout PasswordLayout = new javax.swing.GroupLayout(Password);
        Password.setLayout(PasswordLayout);
        PasswordLayout.setHorizontalGroup(
            PasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PasswordLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PasswordLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbPassword)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PasswordLayout.setVerticalGroup(
            PasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PasswordLayout.createSequentialGroup()
                .addComponent(lbPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        LoginBot.add(Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 230, 60));

        btnDangnhap.setText("Đăng Nhập");
        btnDangnhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangnhapActionPerformed(evt);
            }
        });
        LoginBot.add(btnDangnhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 210, 30));

        lbRegister.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lbRegister.setText("Chưa có tài khoản? Đăng ký");
        lbRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbRegisterMouseClicked(evt);
            }
        });
        LoginBot.add(lbRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 230, -1, 20));
        LoginBot.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 210, 25));

        lbUsername.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbUsername.setText("Usernames");
        LoginBot.add(lbUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, -1));

        jCheckBox1.setText("Ghi nhớ đăng nhập");
        LoginBot.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, -1, 30));

        getContentPane().add(LoginBot, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangnhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangnhapActionPerformed
        String username = txtUsername.getText();
        String password = txtPassword.getText();
    }//GEN-LAST:event_btnDangnhapActionPerformed

    private void lbRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbRegisterMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new RegisterView().setVisible(true);
        dispose();
    }//GEN-LAST:event_lbRegisterMouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LoginBot;
    private javax.swing.JPanel LoginTop;
    private javax.swing.JPanel Password;
    private javax.swing.JPanel Under;
    private javax.swing.JPanel Username;
    private javax.swing.JButton btnDangnhap;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbRegister;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
