package View.CustomPanel;

import Model.GiaiDauModel;

public class ItemGiaiDau extends javax.swing.JPanel {

    public ItemGiaiDau() {
        initComponents();
    }

    public ItemGiaiDau(GiaiDauModel giaiDau) {
        initComponents();
        setData(giaiDau);
    }

    public void setData(GiaiDauModel giaiDau) {
        lblMaGiaiDau.setText("Mã giải: " + giaiDau.getMaGiai());
        lblTenGiaiDau.setText("Tên giải: " + giaiDau.getTenGiai());
        lblTheThuc.setText("Thể thức: " + giaiDau.getTheThuc());
        lblMuaGiai.setText("Mùa giải: " + giaiDau.getMuaGiai());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMaGiaiDau = new javax.swing.JLabel();
        lblTenGiaiDau = new javax.swing.JLabel();
        lblTheThuc = new javax.swing.JLabel();
        lblMuaGiai = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));

        lblMaGiaiDau.setForeground(new java.awt.Color(51, 0, 255));
        lblMaGiaiDau.setText("jLabel1");

        lblTenGiaiDau.setForeground(new java.awt.Color(0, 204, 0));
        lblTenGiaiDau.setText("jLabel2");

        lblTheThuc.setForeground(new java.awt.Color(255, 0, 51));
        lblTheThuc.setText("jLabel3");

        lblMuaGiai.setForeground(new java.awt.Color(255, 153, 0));
        lblMuaGiai.setText("jLabel4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMaGiaiDau)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTheThuc))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMuaGiai)
                        .addGap(91, 91, 91)
                        .addComponent(lblTenGiaiDau)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaGiaiDau)
                    .addComponent(lblTheThuc))
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMuaGiai)
                    .addComponent(lblTenGiaiDau))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblMaGiaiDau;
    private javax.swing.JLabel lblMuaGiai;
    private javax.swing.JLabel lblTenGiaiDau;
    private javax.swing.JLabel lblTheThuc;
    // End of variables declaration//GEN-END:variables
}
