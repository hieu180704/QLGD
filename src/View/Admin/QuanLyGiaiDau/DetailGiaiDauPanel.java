package View.Admin.QuanLyGiaiDau;

import Controller.DetailGiaiDauController;
import DAO.GiaiDauDAO;
import DAO.NhaTaiTroDAO;
import DAO.TheThucDAO;
import Model.GiaiDau;
import Model.NhaTaiTro;
import Model.TheThuc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.util.Date;
import java.util.List;

public class DetailGiaiDauPanel extends JPanel {
    private JTextField txtTenGiaiDau;
    private JComboBox<NhaTaiTro> cbNhaTaiTro;
    private JComboBox<TheThuc> cbTheThuc;
    private JSpinner spNgayTao, spNgayBatDau;
    private JLabel lblPreview;
    private byte[] anhGiaiDau;

    private final GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
    private JButton btnLuu, btnHuy, btnQuayLai, btnChonAnh, btnXoa;
    private GiaiDau currentGiaiDau;

    public DetailGiaiDauPanel() {
        designDetailGiaiDauPanel();
    }

    public void designDetailGiaiDauPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));
        setBorder(new EmptyBorder(20, 60, 20, 60));

        JLabel lblTitle = new JLabel("CHI TIẾT GIẢI ĐẤU");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(30, 30, 100));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setMaximumSize(new Dimension(500, 500));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Tên giải
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

        // Nút hành động
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        btnLuu = new JButton("Lưu");
        btnLuu.setPreferredSize(new Dimension(100, 40));
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setBackground(new Color(50, 160, 90));
        btnLuu.setForeground(Color.WHITE);
        bottomPanel.add(btnLuu);

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 40));
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.setBackground(new Color(220, 50, 50));
        btnXoa.setForeground(Color.WHITE);
        bottomPanel.add(btnXoa);

        btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setPreferredSize(new Dimension(100, 40));
        btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bottomPanel.add(btnQuayLai);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getter button để controller đăng ký listener
    public JButton getBtnLuu() {
        return btnLuu;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnQuayLai() {
        return btnQuayLai;
    }

    public JButton getBtnChonAnh() {
        return btnChonAnh;
    }

    // Getter/setter dữ liệu form
    public String getTenGiaiDau() {
        return txtTenGiaiDau.getText().trim();
    }

    public void setTenGiaiDau(String ten) {
        txtTenGiaiDau.setText(ten);
    }

    public NhaTaiTro getNhaTaiTro() {
        return (NhaTaiTro) cbNhaTaiTro.getSelectedItem();
    }

    public void setNhaTaiTro(NhaTaiTro ntt) {
        cbNhaTaiTro.setSelectedItem(ntt);
    }

    public TheThuc getTheThuc() {
        return (TheThuc) cbTheThuc.getSelectedItem();
    }

    public void setTheThuc(TheThuc tt) {
        cbTheThuc.setSelectedItem(tt);
    }

    public Date getNgayTao() {
        return (Date) spNgayTao.getValue();
    }

    public void setNgayTao(Date d) {
        spNgayTao.setValue(d);
    }

    public Date getNgayBatDau() {
        return (Date) spNgayBatDau.getValue();
    }

    public void setNgayBatDau(Date d) {
        spNgayBatDau.setValue(d);
    }

    public byte[] getAnhGiaiDau() {
        return anhGiaiDau;
    }

    public void setAnhGiaiDau(byte[] data) {
        anhGiaiDau = data;
        if (data != null && data.length > 0) {
            ImageIcon icon = new ImageIcon(data);
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblPreview.setIcon(new ImageIcon(img));
            lblPreview.setText("");
        } else {
            lblPreview.setIcon(null);
            lblPreview.setText("Chưa có ảnh");
        }
    }

    public GiaiDau getCurrentGiaiDau() {
        return currentGiaiDau;
    }

    public void setCurrentGiaiDau(GiaiDau gd) {
        this.currentGiaiDau = gd;
    }

    public void addController(DetailGiaiDauController controller) {
        btnLuu.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnQuayLai.addActionListener(controller);
        btnChonAnh.addActionListener(controller);
    }

    private void loadNhaTaiTro() {
        List<NhaTaiTro> list = new NhaTaiTroDAO().findAll();
        for (NhaTaiTro ntt : list) {
            cbNhaTaiTro.addItem(ntt);
        }
    }

    private void loadTheThuc() {
        List<TheThuc> list = new TheThucDAO().findAll();
        for (TheThuc tt : list) {
            cbTheThuc.addItem(tt);
        }
    }
}
