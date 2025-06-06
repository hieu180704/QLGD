package View.Admin.QuanLyCauThu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Model.CauThu;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

public class ThemCauThuDialog extends JDialog {

    private JTextField txtTen;
    private JComboBox<Integer> cbNgay;
    private JComboBox<Integer> cbThang;
    private JComboBox<Integer> cbNam;
    private JComboBox<QuocGiaItem> cbQuocGia;
    private JComboBox<DoiBongItem> cbDoiBong;
    private JTextField txtChieuCao;
    private JTextField txtCanNang;
    private JTextField txtSoAo;

    private JButton btnChonAnh;
    private JLabel lblAnhPreview;
    private byte[] anhCauThuBytes;

    private JButton btnOk, btnCancel;
    private CauThu cauThu;

    // Các lớp inner để giữ id và tên cho combobox
    public static class QuocGiaItem {
        public final int maQuocGia;
        public final String tenQuocGia;

        public QuocGiaItem(int maQuocGia, String tenQuocGia) {
            this.maQuocGia = maQuocGia;
            this.tenQuocGia = tenQuocGia;
        }

        @Override
        public String toString() {
            return tenQuocGia;
        }

        public String getTenQuocGia() {
            return this.tenQuocGia; 
        }

        public int getMaQuocGia() {
            return this.maQuocGia; 
        }
    }

    public static class DoiBongItem {
        public final int maDoi;
        public final String tenDoi;

        public DoiBongItem(int maDoi, String tenDoi) {
            this.maDoi = maDoi;
            this.tenDoi = tenDoi;
        }

        @Override
        public String toString() {
            return tenDoi;
        }

        String getTenDoiBong() {
            return this.tenDoi;
        }

        int getMaDoiBong() {
            return this.maDoi;
        }
    }

    public ThemCauThuDialog(JFrame parent, List<QuocGiaItem> dsQuocGia, List<DoiBongItem> dsDoiBong) {
        super(parent, "Thêm cầu thủ", true);
        setSize(450, 480);
        setLocationRelativeTo(parent);

        // Set margin toàn bộ dialog
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Tên cầu thủ
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Tên cầu thủ:"), gbc);
        txtTen = new JTextField();
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        contentPane.add(txtTen, gbc);

        // Ngày sinh (3 combobox ngang hàng)
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Ngày sinh:"), gbc);

        JPanel pnlNgaySinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        cbNgay = new JComboBox<>();
        cbThang = new JComboBox<>();
        cbNam = new JComboBox<>();

        for (int i = 1; i <= 31; i++) cbNgay.addItem(i);
        for (int i = 1; i <= 12; i++) cbThang.addItem(i);
        int currentYear = LocalDate.now().getYear();
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

        // Chiều cao
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Chiều cao (cm):"), gbc);
        txtChieuCao = new JTextField();
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        contentPane.add(txtChieuCao, gbc);

        // Cân nặng
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Cân nặng (kg):"), gbc);
        txtCanNang = new JTextField();
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        contentPane.add(txtCanNang, gbc);

        // Số áo
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Số áo:"), gbc);
        txtSoAo = new JTextField();
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        contentPane.add(txtSoAo, gbc);

        // Chọn ảnh
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        contentPane.add(new JLabel("Ảnh cầu thủ:"), gbc);
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
            int res = fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    anhCauThuBytes = Files.readAllBytes(file.toPath());
                    ImageIcon icon = new ImageIcon(new ImageIcon(anhCauThuBytes).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
                    lblAnhPreview.setIcon(icon);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi đọc file ảnh");
                }
            }
        });

        // Xử lý nút Thêm
        btnOk.addActionListener(e -> {
            String ten = txtTen.getText().trim();
            if (ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên cầu thủ không được để trống");
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
                int chieuCao = Integer.parseInt(txtChieuCao.getText().trim());
                int canNang = Integer.parseInt(txtCanNang.getText().trim());
                int soAo = Integer.parseInt(txtSoAo.getText().trim());

                cauThu = new CauThu();
                cauThu.setTenCauThu(ten);
                cauThu.setNgaySinh(java.sql.Date.valueOf(String.format("%04d-%02d-%02d", nam, thang, ngay)));
                cauThu.setMaQuocGia(qg.maQuocGia);
                cauThu.setMaDoi(db.maDoi);
                cauThu.setChieuCao(chieuCao);
                cauThu.setCanNang(canNang);
                cauThu.setSoAo(soAo);
                cauThu.setAnhCauThu(anhCauThuBytes); // set ảnh

                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Chiều cao, cân nặng và số áo phải là số nguyên");
            }
        });

        btnCancel.addActionListener(e -> {
            cauThu = null;
            dispose();
        });
    }

    private boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public CauThu getCauThu() {
        return cauThu;
    }
}
