package View.Admin.QuanLyGiaiDau;

import DAO.GiaiDauDAO;
import DAO.NhaTaiTroDAO;
import DAO.TheThucDAO;
import Model.GiaiDau;
import Model.NhaTaiTro;
import Model.TheThuc;
import View.Admin.QuanLyView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

public class ThemGiaiDauPanel extends JPanel {

    private JTextField txtTenGiaiDau;
    private JComboBox<NhaTaiTro> cbNhaTaiTro;
    private JComboBox<TheThuc> cbTheThuc;
    private JSpinner spNgayTao, spNgayBatDau;
    private JLabel lblPreview;
    private byte[] anhGiaiDau;

    public ThemGiaiDauPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));
        setBorder(new EmptyBorder(20, 60, 20, 60));

        JLabel lblTitle = new JLabel("THÊM GIẢI ĐẤU MỚI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(40, 40, 100));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
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
        JButton btnChonAnh = new JButton("Chọn ảnh...");
        btnChonAnh.addActionListener(this::chonAnh);
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

        // Nút
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        JButton btnLuu = new JButton("Lưu");
        btnLuu.setPreferredSize(new Dimension(100, 40));
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setBackground(new Color(50, 160, 90));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.addActionListener(e -> themGiaiDau());
        bottomPanel.add(btnLuu);

        JButton btnHuy = new JButton("Hủy");
        btnHuy.setPreferredSize(new Dimension(100, 40));
        btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnHuy.addActionListener(e -> clearForm());
        bottomPanel.add(btnHuy);

        JButton btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setPreferredSize(new Dimension(100, 40));
        btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnQuayLai.addActionListener(e -> {
            Component parent = SwingUtilities.getWindowAncestor(this);
            if (parent instanceof QuanLyView) {
                QuanLyView view = (QuanLyView) parent;
                view.openQuanLyGiaiDauPanel();
                view.openQuanLyGiaiDauPanel();
                view.getQuanLyGiaiDauPanel().loadData();
            }
        });
        bottomPanel.add(btnQuayLai);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void chonAnh(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                anhGiaiDau = Files.readAllBytes(file.toPath());
                BufferedImage img = ImageIO.read(file);
                Image scaled = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblPreview.setIcon(new ImageIcon(scaled));
                lblPreview.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Không thể đọc ảnh!");
            }
        }
    }

    private void themGiaiDau() {
        String ten = txtTenGiaiDau.getText().trim();
        NhaTaiTro ntt = (NhaTaiTro) cbNhaTaiTro.getSelectedItem();
        TheThuc tt = (TheThuc) cbTheThuc.getSelectedItem();
        Date ngayTao = (Date) spNgayTao.getValue();
        Date ngayBatDau = (Date) spNgayBatDau.getValue();

        if (ten.isEmpty() || ntt == null || tt == null || anhGiaiDau == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        GiaiDau gd = new GiaiDau();
        gd.setTenGiaiDau(ten);
        gd.setAnhGiaiDau(anhGiaiDau);
        gd.setMaNTT(ntt.getMaNTT());
        gd.setMaTheThuc(tt.getMaTheThuc());
        gd.setNgayTaoGiai(ngayTao);
        gd.setNgayBatDau(ngayBatDau);

        GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
        boolean ok = giaiDauDAO.insert(gd);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Thêm giải đấu thành công!");
            clearForm();

        } else {
            JOptionPane.showMessageDialog(this, "Thêm giải đấu thất bại!");
        }
    }

    private void clearForm() {
        txtTenGiaiDau.setText("");
        cbNhaTaiTro.setSelectedIndex(0);
        cbTheThuc.setSelectedIndex(0);
        anhGiaiDau = null;
        lblPreview.setIcon(null);
        lblPreview.setText("Chưa có ảnh");
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
