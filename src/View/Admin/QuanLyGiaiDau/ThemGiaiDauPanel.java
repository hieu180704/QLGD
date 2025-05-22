package View.Admin.QuanLyGiaiDau;

import Controller.ThemGiaiDauController;
import DAO.NhaTaiTroDAO;
import DAO.TheThucDAO;
import Model.NhaTaiTro;
import Model.TheThuc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class ThemGiaiDauPanel extends JPanel {

    private JTextField txtTenGiaiDau;
    private JComboBox<NhaTaiTro> cbNhaTaiTro;
    private JComboBox<TheThuc> cbTheThuc;
    private JSpinner spNgayTao, spNgayBatDau;
    private JLabel lblPreview;
    private byte[] anhGiaiDau;
    private ThemGiaiDauController controller;  // Bỏ tạo cứng ở đây

    private JButton btnLuu, btnHuy, btnQuayLai, btnChonAnh;

    // Thêm constructor nhận QuanLyGiaiDauPanel để truyền vào controller
    public ThemGiaiDauPanel(View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel quanLyGiaiDauPanel) {
        designThemGiaiDauPanel();
        // Tạo controller, truyền this + quanLyGiaiDauPanel
        controller = new ThemGiaiDauController(this, quanLyGiaiDauPanel);
        registerListeners();
    }

    // Nếu cần có constructor mặc định (không dùng refresh), thì tạo riêng
    public ThemGiaiDauPanel() {
        designThemGiaiDauPanel();
        // Tạo controller mặc định, không có quanLyGiaiDauPanel (có thể truyền null)
        controller = new ThemGiaiDauController(this, null);
        registerListeners();
    }

    private void registerListeners() {
        btnHuy.addActionListener(controller);
        btnChonAnh.addActionListener(controller);
        btnLuu.addActionListener(controller);
        btnQuayLai.addActionListener(controller);
    }

    public void designThemGiaiDauPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));
        setBorder(new EmptyBorder(20, 60, 20, 60));

        // Tiêu đề
        JLabel lblTitle = new JLabel("THÊM GIẢI ĐẤU MỚI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(40, 40, 100));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        // Form chính
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Tên giải đấu
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Tên giải đấu:"), gbc);
        txtTenGiaiDau = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(txtTenGiaiDau, gbc);

        // Nhà tài trợ
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Nhà tài trợ:"), gbc);
        cbNhaTaiTro = new JComboBox<>();
        loadNhaTaiTro();
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(cbNhaTaiTro, gbc);

        // Thể thức
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Thể thức:"), gbc);
        cbTheThuc = new JComboBox<>();
        loadTheThuc();
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(cbTheThuc, gbc);

        // Ngày tạo
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Ngày tạo:"), gbc);
        spNgayTao = new JSpinner(new SpinnerDateModel());
        spNgayTao.setEditor(new JSpinner.DateEditor(spNgayTao, "dd/MM/yyyy"));
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(spNgayTao, gbc);

        // Ngày bắt đầu
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Ngày bắt đầu:"), gbc);
        spNgayBatDau = new JSpinner(new SpinnerDateModel());
        spNgayBatDau.setEditor(new JSpinner.DateEditor(spNgayBatDau, "dd/MM/yyyy"));
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(spNgayBatDau, gbc);

        // Chọn ảnh
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Logo giải đấu:"), gbc);
        btnChonAnh = new JButton("Chọn ảnh...");
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(btnChonAnh, gbc);

        // Preview ảnh
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        lblPreview = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblPreview.setPreferredSize(new Dimension(150, 150));
        lblPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formPanel.add(lblPreview, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Panel nút dưới
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        btnLuu = new JButton("Lưu");
        btnLuu.setPreferredSize(new Dimension(100, 40));
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setBackground(new Color(50, 160, 90));
        btnLuu.setForeground(Color.WHITE);
        bottomPanel.add(btnLuu);

        btnHuy = new JButton("Hủy");
        btnHuy.setPreferredSize(new Dimension(100, 40));
        btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bottomPanel.add(btnHuy);

        btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setPreferredSize(new Dimension(100, 40));
        btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bottomPanel.add(btnQuayLai);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Load danh sách Nhà tài trợ vào combobox
    private void loadNhaTaiTro() {
        List<NhaTaiTro> list = new NhaTaiTroDAO().findAll();
        cbNhaTaiTro.removeAllItems();
        for (NhaTaiTro ntt : list) {
            cbNhaTaiTro.addItem(ntt);
        }
    }

    // Load danh sách Thể thức vào combobox
    private void loadTheThuc() {
        List<TheThuc> list = new TheThucDAO().findAll();
        cbTheThuc.removeAllItems();
        for (TheThuc tt : list) {
            cbTheThuc.addItem(tt);
        }
    }

    // Getter cho các button để controller đăng ký sự kiện
    public JButton getBtnLuu() {
        return btnLuu;
    }

    public JButton getBtnHuy() {
        return btnHuy;
    }

    public JButton getBtnQuayLai() {
        return btnQuayLai;
    }

    public JButton getBtnChonAnh() {
        return btnChonAnh;
    }

    // Getter dữ liệu form cho controller dùng
    public String getTenGiaiDau() {
        return txtTenGiaiDau.getText().trim();
    }

    public NhaTaiTro getNhaTaiTro() {
        return (NhaTaiTro) cbNhaTaiTro.getSelectedItem();
    }

    public TheThuc getTheThuc() {
        return (TheThuc) cbTheThuc.getSelectedItem();
    }

    public Date getNgayTao() {
        return (Date) spNgayTao.getValue();
    }

    public Date getNgayBatDau() {
        return (Date) spNgayBatDau.getValue();
    }

    public byte[] getAnhGiaiDau() {
        return anhGiaiDau;
    }

    // Setter ảnh logo
    public void setAnhGiaiDau(byte[] anh) {
        this.anhGiaiDau = anh;
    }

    // Hiển thị ảnh preview
    public void setPreviewImage(Image image) {
        Image scaled = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        lblPreview.setIcon(new ImageIcon(scaled));
        lblPreview.setText("");
    }

    // Xóa form
    public void clearForm() {
        txtTenGiaiDau.setText("");
        if (cbNhaTaiTro.getItemCount() > 0) {
            cbNhaTaiTro.setSelectedIndex(0);
        }
        if (cbTheThuc.getItemCount() > 0) {
            cbTheThuc.setSelectedIndex(0);
        }
        anhGiaiDau = null;
        lblPreview.setIcon(null);
        lblPreview.setText("Chưa có ảnh");
    }
}
