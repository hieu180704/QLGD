package View.Admin;

import Model.GiaiDauData;
import Model.GiaiDauModel;
import View.CustomPanel.GiaiDauPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class TrangChuPanel extends javax.swing.JPanel {

    public TrangChuPanel() {
        initComponents();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Danh Sách Mùa Giải Hiện Tại", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Lấy dữ liệu
        List<GiaiDauModel> giaiDauList = GiaiDauData.getData();

        // Tạo panel hiển thị bảng dữ liệu
        GiaiDauPanel giaiDauPanel = new GiaiDauPanel(giaiDauList);
        // Cho scroll pane để nếu dữ liệu dài sẽ có thanh cuộn
        JScrollPane scrollPane = new JScrollPane(giaiDauPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelGiaiDau = new javax.swing.JPanel();

        setName(""); // NOI18N
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        panelGiaiDau.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Các Giải Đấu Hiện Tại", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        panelGiaiDau.setPreferredSize(new java.awt.Dimension(300, 150));
        panelGiaiDau.setRequestFocusEnabled(false);
        panelGiaiDau.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        add(panelGiaiDau);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelGiaiDau;
    // End of variables declaration//GEN-END:variables
}
