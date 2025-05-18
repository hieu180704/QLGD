package View.Admin;

import Model.GiaiDauData;
import Model.GiaiDauModel;
import View.CustomPanel.ItemGiaiDau;
import java.util.List;
import javax.swing.BoxLayout;

public class QuanLyGiaiDauPanel extends javax.swing.JPanel {

    public QuanLyGiaiDauPanel() {
        initComponents();
        // gọi hàm load dữ liệu sau khi initComponents
                // Đảm bảo load data sau khi panel hoàn thiện, tránh lỗi layout
        javax.swing.SwingUtilities.invokeLater(() -> {
            loadData(GiaiDauData.getData());
        });
    }

    public void loadData(List<GiaiDauModel> ds) {
        this.removeAll();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // ví dụ xếp dọc
        for (GiaiDauModel giai : ds) {
            ItemGiaiDau item = new ItemGiaiDau(giai);
            this.add(item);
        }
        this.revalidate();
        this.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setText("Quản lý giải đấu");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(308, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(278, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
