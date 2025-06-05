package View.Admin.QuanLyDoiBong;

import Model.DoiBong;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ThemDoiBongDialog extends JDialog {

    private JTextField tfTenDoi;
    private JComboBox<String> cbQuocGia;
    private JComboBox<String> cbSVD;
    private JLabel lblLogo;
    private JButton btnUploadLogo;
    private byte[] logoBytes;
    private JButton btnOk, btnCancel;
    private DoiBong doiBong;

    public ThemDoiBongDialog(Frame parent, List<QuocGiaItem> dsQuocGia, List<SanVanDongItem> dsSanVanDong) {
        super(parent, "Thêm Đội Bóng", true);
        setSize(450, 350);  // Đặt kích thước hợp lý cho cửa sổ
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());  // Dùng GridBagLayout để có thể tùy chỉnh kích thước
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);  // Khoảng cách giữa các thành phần

        int row = 0;

        // Tên đội bóng
        JLabel lblTenDoi = new JLabel("Tên Đội Bóng:");
        gbc.gridx = 0;
        gbc.gridy = row; 
        gbc.gridwidth = 1;
        panel.add(lblTenDoi, gbc);

        tfTenDoi = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = row; 
        gbc.gridwidth = 2;
        panel.add(tfTenDoi, gbc);

        // Quốc gia
        row++;
        JLabel lblQuocGia = new JLabel("Chọn Quốc Gia:");
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblQuocGia, gbc);

        cbQuocGia = new JComboBox<>();
        for (QuocGiaItem qg : dsQuocGia) {
            cbQuocGia.addItem(qg.getTenQuocGia());
        }
        gbc.gridx = 1;
        gbc.gridy = row; 
        gbc.gridwidth = 2;
        panel.add(cbQuocGia, gbc);

        // Sân vận động
        row++;
        JLabel lblSVD = new JLabel("Chọn Sân Vận Động:");
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(lblSVD, gbc);

        cbSVD = new JComboBox<>();
        for (SanVanDongItem svd : dsSanVanDong) {
            cbSVD.addItem(svd.getTenSVD());
        }
        gbc.gridx = 1;
        gbc.gridy = row; 
        gbc.gridwidth = 2;
        panel.add(cbSVD, gbc);

        // Logo đội bóng
        row++;
        JLabel lblLogoText = new JLabel("Chọn Logo Đội:");
        gbc.gridx = 0;
        gbc.gridy = row; 
        panel.add(lblLogoText, gbc);

        lblLogo = new JLabel("Chưa có logo");
        btnUploadLogo = new JButton("Tải Logo");
        btnUploadLogo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files (PNG, JPG, JPEG)", "jpg", "jpeg", "png"));
            int res = fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    logoBytes = Files.readAllBytes(file.toPath());  // Lưu trữ file ảnh vào mảng byte
                    ImageIcon icon = new ImageIcon(new ImageIcon(logoBytes).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));  // Hiển thị ảnh preview
                    lblLogo.setIcon(icon);  // Cập nhật icon cho JLabel
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi đọc file ảnh");
                }
            }
        });

        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        logoPanel.add(btnUploadLogo);
        logoPanel.add(lblLogo);

        gbc.gridx = 1;
        gbc.gridy = row; 
        gbc.gridwidth = 2;
        panel.add(logoPanel, gbc);

        // Nút thêm đội bóng
        row++;
        btnOk = new JButton("Thêm Đội Bóng");
        btnCancel = new JButton("Hủy");
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlButton.add(btnOk);
        pnlButton.add(btnCancel);
        gbc.gridx = 0;
        gbc.gridy = row; 
        gbc.gridwidth = 3;
        panel.add(pnlButton, gbc);

        // Xử lý sự kiện nút Thêm
        btnOk.addActionListener(e -> {
            String tenDoi = tfTenDoi.getText().trim();
            if (tenDoi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên đội bóng không được để trống");
                return;
            }

            QuocGiaItem qg = (QuocGiaItem) cbQuocGia.getSelectedItem();
            SanVanDongItem svd = (SanVanDongItem) cbSVD.getSelectedItem();

            try {
                doiBong = new DoiBong();
                doiBong.setTenDoi(tenDoi);
                doiBong.setMaQuocGia(qg.getMaQuocGia());
                doiBong.setMaSVD(svd.getMaSVD());
                doiBong.setLogoDoi(logoBytes);

                // Đóng cửa sổ
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ");
            }
        });

        btnCancel.addActionListener(e -> {
            doiBong = null;
            dispose();
        });

        add(panel);
        setVisible(true);
    }

    public DoiBong getDoiBong() {
        return doiBong;
    }

    public static class SanVanDongItem {
        private int maSVD;
        private String tenSVD;

        public SanVanDongItem(int maSVD, String tenSVD) {
            this.maSVD = maSVD;
            this.tenSVD = tenSVD;
        }

        public int getMaSVD() {
            return maSVD;
        }

        public String getTenSVD() {
            return tenSVD;
        }

        @Override
        public String toString() {
            return tenSVD; // Dùng tên sân vận động để hiển thị trong JComboBox
        }
    }
}
