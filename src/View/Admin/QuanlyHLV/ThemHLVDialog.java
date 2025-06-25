package View.Admin.QuanLyHLV;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Model.HLV;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.DoiBongItem;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Calendar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ThemHLVDialog extends JDialog {

    private JTextField txtTenHLV;
    private JComboBox<Integer> cbNgay;
    private JComboBox<Integer> cbThang;
    private JComboBox<Integer> cbNam;
    private JComboBox<QuocGiaItem> cbQuocGia;
    private JComboBox<DoiBongItem> cbDoiBong;
    private JButton btnChonAnh;
    private JLabel lblAnhPreview;
    private byte[] anhHLVBytes;
    private JButton btnOk, btnCancel;
    private HLV hlv;

    public ThemHLVDialog(JFrame parent, List<QuocGiaItem> dsQuocGia, List<DoiBongItem> dsDoiBong) {
        super(parent, "Thêm huấn luyện viên", true);
        setSize(450, 350);
        setLocationRelativeTo(parent);

        // Sắp xếp danh sách theo thứ tự A-Z
        Collections.sort(dsQuocGia, Comparator.comparing(QuocGiaItem::getTenQuocGia));
        Collections.sort(dsDoiBong, Comparator.comparing(DoiBongItem::getTenDoiBong));

        // Set margin toàn bộ dialog
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Tên huấn luyện viên
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Tên huấn luyện viên:"), gbc);
        txtTenHLV = new JTextField();
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        contentPane.add(txtTenHLV, gbc);

        // Ngày sinh
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Ngày sinh:"), gbc);
        JPanel pnlNgaySinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        cbNgay = new JComboBox<>();
        cbThang = new JComboBox<>();
        cbNam = new JComboBox<>();
        for (int i = 1; i <= 31; i++) cbNgay.addItem(i);
        for (int i = 1; i <= 12; i++) cbThang.addItem(i);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1950; i--) cbNam.addItem(i);
        pnlNgaySinh.add(cbNgay);
        pnlNgaySinh.add(new JLabel("/"));
        pnlNgaySinh.add(cbThang);
        pnlNgaySinh.add(new JLabel("/"));
        pnlNgaySinh.add(cbNam);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        contentPane.add(pnlNgaySinh, gbc);

        // Quốc gia
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Quốc gia:"), gbc);
        cbQuocGia = new JComboBox<>();
        for (QuocGiaItem qg : dsQuocGia) cbQuocGia.addItem(qg);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        contentPane.add(cbQuocGia, gbc);

        // Đội bóng
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Đội bóng:"), gbc);
        cbDoiBong = new JComboBox<>();
        for (DoiBongItem db : dsDoiBong) cbDoiBong.addItem(db);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        contentPane.add(cbDoiBong, gbc);

        // Chọn ảnh
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Ảnh huấn luyện viên:"), gbc);
        JPanel pnlAnh = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnChonAnh = new JButton("Chọn ảnh");
        pnlAnh.add(btnChonAnh);
        lblAnhPreview = new JLabel();
        lblAnhPreview.setPreferredSize(new Dimension(90, 90));
        lblAnhPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pnlAnh.add(lblAnhPreview);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        contentPane.add(pnlAnh, gbc);

        // Button OK / Cancel
        row++;
        btnOk = new JButton("Thêm");
        btnCancel = new JButton("Hủy");
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlButton.add(btnOk);
        pnlButton.add(btnCancel);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 3;
        contentPane.add(pnlButton, gbc);

        // Xử lý sự kiện chọn ảnh
        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files (PNG, JPG, JPEG)", "jpg", "jpeg", "png"));
            int res = fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    anhHLVBytes = Files.readAllBytes(file.toPath());
                    ImageIcon icon = new ImageIcon(new ImageIcon(anhHLVBytes).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
                    lblAnhPreview.setIcon(icon);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi đọc file ảnh");
                }
            }
        });

        // Xử lý nút Thêm
        btnOk.addActionListener(e -> {
            String ten = txtTenHLV.getText().trim();
            if (ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên huấn luyện viên không được để trống");
                return;
            }

            int ngay = (int) cbNgay.getSelectedItem();
            int thang = (int) cbThang.getSelectedItem();
            int nam = (int) cbNam.getSelectedItem();

            if (!isValidDate(nam, thang, ngay)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ");
                return;
            }

            QuocGiaItem qg = (QuocGiaItem) cbQuocGia.getSelectedItem();
            DoiBongItem db = (DoiBongItem) cbDoiBong.getSelectedItem();

            try {
                hlv = new HLV();
                hlv.setTenHLV(ten);
                hlv.setNgaySinh(java.sql.Date.valueOf(String.format("%04d-%02d-%02d", nam, thang, ngay)));
                hlv.setMaQuocGia(qg.getMaQuocGia());
                hlv.setMaDoi(db.getMaDoiBong());
                hlv.setAnhHLV(anhHLVBytes);

                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ");
            }
        });

        // Xử lý nút Hủy
        btnCancel.addActionListener(e -> {
            hlv = null;
            dispose();
        });
    }

    private boolean isValidDate(int year, int month, int day) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.set(year, month - 1, day);
            cal.getTime(); // Kiểm tra ngày hợp lệ
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public HLV getHLV() {
        return hlv;
    }
}