package View.Admin.QuanLyGiaiDau;

import Controller.QuanLyGiaiDauController;
import DAO.GiaiDauDAO;
import Model.GiaiDau;
import View.Admin.QuanLyView;
import View.CustomPanel.ItemGiaiDau;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class QuanLyGiaiDauPanel extends JPanel {

    private List<GiaiDau> danhSachGiaiDau;
    private JPanel panelItems;
    private JTextField txtTimKiem;
    private QuanLyGiaiDauController quanLyGiaiDauController = new QuanLyGiaiDauController(this);

    public QuanLyGiaiDauPanel() {
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
        JButton btnThem = new JButton("Thêm giải đấu");
        btnThem.setPreferredSize(new Dimension(140, 38));
        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnThem.setForeground(Color.WHITE);
        btnThem.setBackground(new Color(70, 130, 180));
        btnThem.setFocusPainted(false);
        btnThem.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnThem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnThem.setOpaque(true);
        btnThem.addActionListener(quanLyGiaiDauController);

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.CENTER);
        headerPanel.add(btnThem, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ========== Danh sách Item ==========
        panelItems = new JPanel();
        panelItems.setBackground(new Color(18, 30, 50));
        panelItems.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 16));

        add(panelItems, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1000, 700));

        // Load data ngay khi khởi tạo
        loadData();
    }

    public void loadData() {
        GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
        danhSachGiaiDau = giaiDauDAO.findAll();
        hienThiDanhSach(danhSachGiaiDau);
    }

    private void hienThiDanhSach(List<GiaiDau> danhSach) {
        panelItems.removeAll();

        if (danhSach != null && !danhSach.isEmpty()) {
            for (GiaiDau gd : danhSach) {
                ItemGiaiDau item = new ItemGiaiDau(gd);
                // Thêm listener hoặc callback cho item, nếu cần, ví dụ:
                item.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Component parent = SwingUtilities.getWindowAncestor(QuanLyGiaiDauPanel.this);
                        if (parent instanceof QuanLyView) {
                            ((QuanLyView) parent).openDetailGiaiDauPanel(gd);
                        }
                    }
                });
                item.setPreferredSize(new Dimension(320, 180));
                panelItems.add(item);
            }
        } else {
            JLabel lblNoData = new JLabel("Không có dữ liệu giải đấu");
            lblNoData.setForeground(Color.GRAY);
            panelItems.add(lblNoData);
        }
        panelItems.revalidate();
        panelItems.repaint();
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
