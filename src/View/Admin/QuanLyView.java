package View.Admin;

import Controller.QuanLyController;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

public class QuanLyView extends javax.swing.JFrame {

    private TrangChuPanel trangChuPanel = new TrangChuPanel();
    private QuanLyGiaiDauPanel quanLyGiaiDauPanel = new QuanLyGiaiDauPanel();
    private DanhSachDoiBongPanel danhSachDoiBongPanel = new DanhSachDoiBongPanel();
    private DanhSachCauThuPanel danhSachCauThuPanel = new DanhSachCauThuPanel();
    private DanhSachHLVPanel danhSachHLVPanel = new DanhSachHLVPanel();
    private DanhSachTrongTaiPanel danhSachTrongTaiPanel = new DanhSachTrongTaiPanel();
    private QuanLyTaiKhoanPanel quanLyTaiKhoanPanel = new QuanLyTaiKhoanPanel();
  

    public QuanLyView() {
        initComponents();
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        QuanLyController quanLyController = new QuanLyController(this);
        btnTrangChu.addActionListener(quanLyController);
        btnQuanLyGiaiDau.addActionListener(quanLyController);
        btnDanhSachCauThu.addActionListener(quanLyController);
        btnDanhSachDoiBong.addActionListener(quanLyController);
        btnDanhSachHLV.addActionListener(quanLyController);
        btnDanhSachTrongTai.addActionListener(quanLyController);
        btnQuanLyTaiKhoan.addActionListener(quanLyController);
        btnDangXuat.addActionListener(quanLyController);



        // Sử dụng CardLayout để quản lý các panel
        CardLayout cardLayout = (CardLayout) LayerPanel.getLayout();
        
        LayerPanel.add(trangChuPanel, "TrangChuPanel");
        LayerPanel.add(quanLyGiaiDauPanel, "QuanLyGiaiDauPanel");
        LayerPanel.add(danhSachDoiBongPanel, "DanhSachDoiBongPanel");
        LayerPanel.add(danhSachCauThuPanel, "DanhSachCauThuPanel");
        LayerPanel.add(danhSachHLVPanel, "DanhSachHLVPanel");
        LayerPanel.add(danhSachTrongTaiPanel, "DanhSachTrongTaiPanel");
        LayerPanel.add(quanLyTaiKhoanPanel, "QuanLyTaiKhoanPanel");

        // Hiển thị trang chủ mặc định
        cardLayout.show(LayerPanel, "TrangChuPanel");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        btnQuanLyGiaiDau = new javax.swing.JButton();
        btnDanhSachDoiBong = new javax.swing.JButton();
        btnDanhSachCauThu = new javax.swing.JButton();
        btnDanhSachTrongTai = new javax.swing.JButton();
        btnDanhSachHLV = new javax.swing.JButton();
        btnQuanLyTaiKhoan = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        LayerPanel = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(21, 78, 128));
        jPanel1.setPreferredSize(new java.awt.Dimension(230, 600));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTrangChu.setBackground(new java.awt.Color(21, 78, 128));
        btnTrangChu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnTrangChu.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu.setText("Trang Chủ");
        btnTrangChu.setBorder(null);
        btnTrangChu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTrangChu.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 57));

        btnDangXuat.setBackground(new java.awt.Color(255, 51, 51));
        btnDangXuat.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setText("Đăng Xuất");
        btnDangXuat.setBorder(null);
        btnDangXuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDangXuat.setPreferredSize(new java.awt.Dimension(100, 20));
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        jPanel1.add(btnDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 230, 57));

        btnQuanLyGiaiDau.setBackground(new java.awt.Color(21, 78, 128));
        btnQuanLyGiaiDau.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnQuanLyGiaiDau.setForeground(new java.awt.Color(255, 255, 255));
        btnQuanLyGiaiDau.setText("Quản Lý Giải Đấu");
        btnQuanLyGiaiDau.setBorder(null);
        btnQuanLyGiaiDau.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQuanLyGiaiDau.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnQuanLyGiaiDau, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 230, 57));

        btnDanhSachDoiBong.setBackground(new java.awt.Color(21, 78, 128));
        btnDanhSachDoiBong.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnDanhSachDoiBong.setForeground(new java.awt.Color(255, 255, 255));
        btnDanhSachDoiBong.setText("Danh Sách Đội Bóng");
        btnDanhSachDoiBong.setBorder(null);
        btnDanhSachDoiBong.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDanhSachDoiBong.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnDanhSachDoiBong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 230, 57));

        btnDanhSachCauThu.setBackground(new java.awt.Color(21, 78, 128));
        btnDanhSachCauThu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnDanhSachCauThu.setForeground(new java.awt.Color(255, 255, 255));
        btnDanhSachCauThu.setText("Danh Sách Cầu Thủ");
        btnDanhSachCauThu.setBorder(null);
        btnDanhSachCauThu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDanhSachCauThu.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnDanhSachCauThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 230, 57));

        btnDanhSachTrongTai.setBackground(new java.awt.Color(21, 78, 128));
        btnDanhSachTrongTai.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnDanhSachTrongTai.setForeground(new java.awt.Color(255, 255, 255));
        btnDanhSachTrongTai.setText("Danh Sách Trọng Tài");
        btnDanhSachTrongTai.setBorder(null);
        btnDanhSachTrongTai.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDanhSachTrongTai.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnDanhSachTrongTai, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 230, 57));

        btnDanhSachHLV.setBackground(new java.awt.Color(21, 78, 128));
        btnDanhSachHLV.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnDanhSachHLV.setForeground(new java.awt.Color(255, 255, 255));
        btnDanhSachHLV.setText("Danh Sách HLV");
        btnDanhSachHLV.setBorder(null);
        btnDanhSachHLV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDanhSachHLV.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnDanhSachHLV, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 230, 57));

        btnQuanLyTaiKhoan.setBackground(new java.awt.Color(21, 78, 128));
        btnQuanLyTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnQuanLyTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        btnQuanLyTaiKhoan.setText("Quản Lý Tài Khoản");
        btnQuanLyTaiKhoan.setBorder(null);
        btnQuanLyTaiKhoan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQuanLyTaiKhoan.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnQuanLyTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 230, 57));

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(34, 91, 144));
        jPanel2.setPreferredSize(new java.awt.Dimension(770, 80));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("    Quản Lý Giải Đấu Bóng Đá");
        jPanel2.add(jLabel1, java.awt.BorderLayout.WEST);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Username          ");
        jPanel2.add(jLabel2, java.awt.BorderLayout.EAST);
        jLabel2.getAccessibleContext().setAccessibleName("lblUsername");

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        LayerPanel.setLayout(new java.awt.CardLayout());
        getContentPane().add(LayerPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    
    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:   
    }//GEN-LAST:event_btnDangXuatActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyView().setVisible(true);
            }
        });
    }

    // Các hàm mở panel
    public void openTrangChu() {
        showPanel("TrangChuPanel");
    }

    public void openQuanLyGiaiDauPanel() {
        showPanel("QuanLyGiaiDauPanel");
    }

    public void openDanhSachDoiBong() {
        showPanel("DanhSachDoiBongPanel");
    }

    public void openDanhSachCauThu() {
        showPanel("DanhSachCauThuPanel");
    }

    public void openDanhSachHLV() {
        showPanel("DanhSachHLVPanel");
    }

    public void openDanhSachTrongTai() {
        showPanel("DanhSachTrongTaiPanel");
    }

    public void openQuanLyTaiKhoan() {
        showPanel("QuanLyTaiKhoanPanel");
    }

    // Hàm chung để hiển thị panel
    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) LayerPanel.getLayout();
        cardLayout.show(LayerPanel, panelName);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane LayerPanel;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDanhSachCauThu;
    private javax.swing.JButton btnDanhSachDoiBong;
    private javax.swing.JButton btnDanhSachHLV;
    private javax.swing.JButton btnDanhSachTrongTai;
    private javax.swing.JButton btnQuanLyGiaiDau;
    private javax.swing.JButton btnQuanLyTaiKhoan;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
