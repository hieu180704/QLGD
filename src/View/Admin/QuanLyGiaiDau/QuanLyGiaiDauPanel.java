package View.Admin.QuanLyGiaiDau;

import Controller.QuanLyGiaiDauController;
import DAO.GiaiDauDAO;
import DAO.NhaTaiTroDAO;
import Model.GiaiDau;
import View.CustomPanel.ItemGiaiDau;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class QuanLyGiaiDauPanel extends JPanel {

    private List<GiaiDau> danhSachGiaiDau;
    private QuanLyGiaiDauController controller;
    
    private GiaiDauDAO giaiDauDAO;
    private NhaTaiTroDAO nhaTaiTroDAO;

    // Component
    private JPanel panelItems;
    private JTextField txtTimKiem;
    private JButton btnThem;

    public QuanLyGiaiDauPanel() {
        giaiDauDAO = new GiaiDauDAO();
        nhaTaiTroDAO = new NhaTaiTroDAO();
        controller = new QuanLyGiaiDauController(this);

        designPanel();
        loadData();
        timKiem();
    }

    public void designPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(18, 30, 50));

        // ========== Header ==========
        JPanel headerPanel = new JPanel(new BorderLayout(15, 10));
        headerPanel.setBackground(new Color(18, 30, 50));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitle = new JLabel("Quản lý giải đấu");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));

        // ======= Search Field ========
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(300, 35));
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setToolTipText("Tìm kiếm theo tên giải đấu...");
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 90, 110), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtTimKiem.setBackground(new Color(35, 45, 65));
        txtTimKiem.setForeground(Color.WHITE);
        txtTimKiem.setCaretColor(Color.WHITE);
        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tìm kiếm giải đấu...");

        searchPanel.add(txtTimKiem, BorderLayout.CENTER);

        // ======= Thêm mới =========
        btnThem = new JButton("Thêm giải đấu");
        btnThem.setPreferredSize(new Dimension(140, 38));
        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThem.setForeground(Color.WHITE);
        btnThem.setBackground(new Color(70, 130, 180));
        btnThem.setFocusPainted(false);
        btnThem.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnThem.setOpaque(true);
        btnThem.addActionListener(new Controller.QuanLyGiaiDauController(this));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.CENTER);
        headerPanel.add(btnThem, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ========== Danh sách Item ==========
        panelItems = new JPanel();
        panelItems.setBackground(new Color(18, 30, 50));
        panelItems.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        add(panelItems, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1000, 700));
    }

    public void loadData() {
        panelItems.removeAll();
        giaiDauDAO = new GiaiDauDAO();
        danhSachGiaiDau = giaiDauDAO.findAll();
        if (danhSachGiaiDau != null && !danhSachGiaiDau.isEmpty()) {
            for (GiaiDau gd : danhSachGiaiDau) {
                addItemGiaiDau(gd);
            }
        } else {
            JLabel lblNoData = new JLabel("Không có dữ liệu giải đấu");
            lblNoData.setForeground(Color.RED);
            panelItems.add(lblNoData);
        }
        panelItems.revalidate();
        panelItems.repaint();
    }

    private void addItemGiaiDau(GiaiDau gd) {
        ItemGiaiDau item = new ItemGiaiDau(gd, g -> {
            controller.openDetailGiaiDauDialog(g);
        });
        item.setPreferredSize(new Dimension(320, 180));
        panelItems.add(item);
    }

    public JButton getBtnThem() {
        return btnThem;
    }

    public void timKiem() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterList();
            }

            private void filterList() {
                String keyword = txtTimKiem.getText().trim().toLowerCase();
                panelItems.removeAll();
                giaiDauDAO = new GiaiDauDAO();

                List<GiaiDau> filteredList;
                if (keyword.isEmpty()) {
                    filteredList = giaiDauDAO.findAll();
                } else {
                    filteredList = giaiDauDAO.findByKeyword(keyword);
                }

                for (GiaiDau gd : filteredList) {
                    addItemGiaiDau(gd);
                }

                panelItems.revalidate();
                panelItems.repaint();
            }

        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setForeground(new java.awt.Color(255, 255, 255));

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
