package View.Admin.QuanLyGiaiDau;

import DAO.GiaiDauDAO;
import DAO.NhaTaiTroDAO;
import DAO.TheThucDAO;
import Model.GiaiDau;
import Model.NhaTaiTro;
import Model.TheThuc;
import View.Admin.QuanLyView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
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

    private GiaiDau currentGiaiDau;

    public DetailGiaiDauPanel() {
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
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Tên giải đấu:"), gbc);
        txtTenGiaiDau = new JTextField();
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(txtTenGiaiDau, gbc);

        // Nhà tài trợ
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Nhà tài trợ:"), gbc);
        cbNhaTaiTro = new JComboBox<>();
        loadNhaTaiTro();
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(cbNhaTaiTro, gbc);

        // Thể thức
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Thể thức:"), gbc);
        cbTheThuc = new JComboBox<>();
        loadTheThuc();
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(cbTheThuc, gbc);

        // Ngày tạo
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Ngày tạo:"), gbc);
        spNgayTao = new JSpinner(new SpinnerDateModel());
        spNgayTao.setEditor(new JSpinner.DateEditor(spNgayTao, "dd/MM/yyyy"));
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(spNgayTao, gbc);

        // Ngày bắt đầu
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Ngày bắt đầu:"), gbc);
        spNgayBatDau = new JSpinner(new SpinnerDateModel());
        spNgayBatDau.setEditor(new JSpinner.DateEditor(spNgayBatDau, "dd/MM/yyyy"));
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(spNgayBatDau, gbc);

        // Chọn ảnh
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Logo giải đấu:"), gbc);
        JButton btnChonAnh = new JButton("Chọn ảnh...");
        btnChonAnh.addActionListener(this::chonAnh);
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(btnChonAnh, gbc);

        // Preview ảnh
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        lblPreview = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblPreview.setPreferredSize(new Dimension(150, 150));
        lblPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formPanel.add(lblPreview, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Nút hành động
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setPreferredSize(new Dimension(100, 40));
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setBackground(new Color(50, 160, 90));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.addActionListener(e -> luuGiaiDau());
        bottomPanel.add(btnLuu);

        JButton btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 40));
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.setBackground(new Color(220, 50, 50));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.addActionListener(e -> xoaGiaiDau());
        bottomPanel.add(btnXoa);

        JButton btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setPreferredSize(new Dimension(100, 40));
        btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnQuayLai.addActionListener(e -> {
            Component parent = SwingUtilities.getWindowAncestor(this);
            if (parent instanceof QuanLyView) {
                ((QuanLyView) parent).openQuanLyGiaiDauPanel();
            }
        });
        bottomPanel.add(btnQuayLai);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setGiaiDau(GiaiDau gd) {
        if (gd == null) return;
        this.currentGiaiDau = gd;

        txtTenGiaiDau.setText(gd.getTenGiaiDau());

        // Chọn nhà tài trợ tương ứng
        for (int i = 0; i < cbNhaTaiTro.getItemCount(); i++) {
            if (cbNhaTaiTro.getItemAt(i).getMaNTT() == gd.getMaNTT()) {
                cbNhaTaiTro.setSelectedIndex(i);
                break;
            }
        }

        // Chọn thể thức tương ứng
        for (int i = 0; i < cbTheThuc.getItemCount(); i++) {
            if (cbTheThuc.getItemAt(i).getMaTheThuc() == gd.getMaTheThuc()) {
                cbTheThuc.setSelectedIndex(i);
                break;
            }
        }

        spNgayTao.setValue(gd.getNgayTaoGiai());
        spNgayBatDau.setValue(gd.getNgayBatDau());

        anhGiaiDau = gd.getAnhGiaiDau();
        if (anhGiaiDau != null && anhGiaiDau.length > 0) {
            ImageIcon icon = new ImageIcon(anhGiaiDau);
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblPreview.setIcon(new ImageIcon(img));
            lblPreview.setText("");
        } else {
            lblPreview.setIcon(null);
            lblPreview.setText("Chưa có ảnh");
        }
    }

    private void chonAnh(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                anhGiaiDau = Files.readAllBytes(file.toPath());
                ImageIcon icon = new ImageIcon(anhGiaiDau);
                Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblPreview.setIcon(new ImageIcon(img));
                lblPreview.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Không thể đọc ảnh!");
            }
        }
    }

    private void luuGiaiDau() {
        if (currentGiaiDau == null) return;

        String ten = txtTenGiaiDau.getText().trim();
        NhaTaiTro ntt = (NhaTaiTro) cbNhaTaiTro.getSelectedItem();
        TheThuc tt = (TheThuc) cbTheThuc.getSelectedItem();
        Date ngayTao = (Date) spNgayTao.getValue();
        Date ngayBatDau = (Date) spNgayBatDau.getValue();

        if (ten.isEmpty() || ntt == null || tt == null || anhGiaiDau == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        currentGiaiDau.setTenGiaiDau(ten);
        currentGiaiDau.setMaNTT(ntt.getMaNTT());
        currentGiaiDau.setMaTheThuc(tt.getMaTheThuc());
        currentGiaiDau.setNgayTaoGiai(ngayTao);
        currentGiaiDau.setNgayBatDau(ngayBatDau);
        currentGiaiDau.setAnhGiaiDau(anhGiaiDau);

        boolean ok = giaiDauDAO.update(currentGiaiDau);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Cập nhật giải đấu thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }

    private void xoaGiaiDau() {
        if (currentGiaiDau == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa giải đấu này không?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = giaiDauDAO.delete(currentGiaiDau.getMaGiaiDau());
            if (ok) {
                JOptionPane.showMessageDialog(this, "Xóa giải đấu thành công!");
                // Quay về panel quản lý
                Component parent = SwingUtilities.getWindowAncestor(this);
                if (parent instanceof QuanLyView) {
                    ((QuanLyView) parent).openQuanLyGiaiDauPanel();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
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
