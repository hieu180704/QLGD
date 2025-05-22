package View.Admin.QuanLyGiaiDau;

import Controller.DetailGiaiDauController;
import Controller.QuanLyGiaiDauController;
import DAO.GiaiDauDAO;
import Model.GiaiDau;
import View.CustomPanel.ItemGiaiDau;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class QuanLyGiaiDauPanel extends JPanel {

    private List<GiaiDau> danhSachGiaiDau;
    private JPanel panelItems;
    private JTextField txtTimKiem;
    private QuanLyGiaiDauController quanLyGiaiDauController = new QuanLyGiaiDauController(this);
    private GiaiDauDAO giaiDauDAO;

    public QuanLyGiaiDauPanel() {
        designPanel();
        loadData();
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
        panelItems.setLayout(new GridLayout(0, 4, 16, 16));

        add(panelItems, BorderLayout.CENTER);

        setPreferredSize(new Dimension(1000, 700));

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
                if (keyword.isEmpty()) {
                    // Hiển thị toàn bộ danh sách gốc
                    for (GiaiDau gd : danhSachGiaiDau) {
                        addItemGiaiDau(gd);
                    }
                } else {
                    // Lọc theo tên giải đấu
                    for (GiaiDau gd : danhSachGiaiDau) {
                        if (gd.getTenGiaiDau().toLowerCase().contains(keyword)) {
                            addItemGiaiDau(gd);
                        }
                    }
                }
                panelItems.revalidate();
                panelItems.repaint();
            }
        });

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
        ItemGiaiDau item = new ItemGiaiDau(gd);
        item.setPreferredSize(new Dimension(320, 180));
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DetailGiaiDauPanel detailGiaiDauPanel = new DetailGiaiDauPanel();

                // Thiết lập dữ liệu hiện tại
                detailGiaiDauPanel.setCurrentGiaiDau(gd);
                detailGiaiDauPanel.setTenGiaiDau(gd.getTenGiaiDau());
                detailGiaiDauPanel.setNhaTaiTro(gd.getNhaTaiTro());
                detailGiaiDauPanel.setTheThuc(gd.getTheThuc());
                detailGiaiDauPanel.setNgayTao(gd.getNgayTaoGiai());
                detailGiaiDauPanel.setNgayBatDau(gd.getNgayBatDau());
                detailGiaiDauPanel.setAnhGiaiDau(gd.getAnhGiaiDau());

                // Tạo controller, đăng ký sự kiện
                DetailGiaiDauController controller = new DetailGiaiDauController(detailGiaiDauPanel, QuanLyGiaiDauPanel.this);

                JFrame frame = new JFrame("Chi tiết Giải Đấu");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(detailGiaiDauPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        panelItems.add(item);
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
