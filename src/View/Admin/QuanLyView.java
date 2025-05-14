package View.Admin;

import Controller.QuanLyController;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class QuanLyView extends javax.swing.JFrame {

    private TrangChuPanel trangChuPanel = new TrangChuPanel();
    private QuanLyGiaiDauPanel quanLyGiaiDauPanel = new QuanLyGiaiDauPanel();

    public QuanLyView() {
        initComponents();
        this.setSize(1000, 600);
        QuanLyController quanLyController = new QuanLyController(this);
        btnTrangChu.addActionListener(quanLyController);
        btnQuanLyGiaiDau.addActionListener(quanLyController);

        LayerPanel.add(trangChuPanel);
        LayerPanel.add(quanLyGiaiDauPanel);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        btnTrangChu1 = new javax.swing.JButton();
        btnQuanLyGiaiDau = new javax.swing.JButton();
        btnTrangChu3 = new javax.swing.JButton();
        btnTrangChu4 = new javax.swing.JButton();
        btnTrangChu5 = new javax.swing.JButton();
        btnTrangChu6 = new javax.swing.JButton();
        btnTrangChu7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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

        btnTrangChu1.setBackground(new java.awt.Color(21, 78, 128));
        btnTrangChu1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnTrangChu1.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu1.setText("Danh");
        btnTrangChu1.setBorder(null);
        btnTrangChu1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrangChu1.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnTrangChu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 230, 57));

        btnQuanLyGiaiDau.setBackground(new java.awt.Color(21, 78, 128));
        btnQuanLyGiaiDau.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnQuanLyGiaiDau.setForeground(new java.awt.Color(255, 255, 255));
        btnQuanLyGiaiDau.setText("Quản Lý Giải Đấu");
        btnQuanLyGiaiDau.setBorder(null);
        btnQuanLyGiaiDau.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQuanLyGiaiDau.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnQuanLyGiaiDau, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 230, 57));

        btnTrangChu3.setBackground(new java.awt.Color(21, 78, 128));
        btnTrangChu3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnTrangChu3.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu3.setText("Danh Sách Đội Bóng");
        btnTrangChu3.setBorder(null);
        btnTrangChu3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrangChu3.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnTrangChu3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 230, 57));

        btnTrangChu4.setBackground(new java.awt.Color(21, 78, 128));
        btnTrangChu4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnTrangChu4.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu4.setText("Danh Sách Cầu Thủ");
        btnTrangChu4.setBorder(null);
        btnTrangChu4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrangChu4.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnTrangChu4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 230, 57));

        btnTrangChu5.setBackground(new java.awt.Color(21, 78, 128));
        btnTrangChu5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnTrangChu5.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu5.setText("Danh");
        btnTrangChu5.setBorder(null);
        btnTrangChu5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrangChu5.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnTrangChu5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 230, 57));

        btnTrangChu6.setBackground(new java.awt.Color(21, 78, 128));
        btnTrangChu6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnTrangChu6.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu6.setText("Danh");
        btnTrangChu6.setBorder(null);
        btnTrangChu6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrangChu6.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnTrangChu6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 230, 57));

        btnTrangChu7.setBackground(new java.awt.Color(21, 78, 128));
        btnTrangChu7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btnTrangChu7.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangChu7.setText("Danh");
        btnTrangChu7.setBorder(null);
        btnTrangChu7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrangChu7.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel1.add(btnTrangChu7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 230, 57));

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel2.setBackground(new java.awt.Color(34, 91, 144));
        jPanel2.setPreferredSize(new java.awt.Dimension(770, 80));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản Lý Giải Đấu Bóng Đá");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 642, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        LayerPanel.setLayout(new java.awt.CardLayout());
        getContentPane().add(LayerPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    public void openTrangChu() {
        trangChuPanel.setVisible(true);
        quanLyGiaiDauPanel.setVisible(false);
    }

    public void openQuanLyGiaiDau() {
        trangChuPanel.setVisible(false);
        quanLyGiaiDauPanel.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane LayerPanel;
    private javax.swing.JButton btnQuanLyGiaiDau;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnTrangChu1;
    private javax.swing.JButton btnTrangChu3;
    private javax.swing.JButton btnTrangChu4;
    private javax.swing.JButton btnTrangChu5;
    private javax.swing.JButton btnTrangChu6;
    private javax.swing.JButton btnTrangChu7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
