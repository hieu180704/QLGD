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
    private JComboBox<QuocGiaItem> cbQuocGia;
    private JComboBox<SanVanDongItem> cbSVD;
    private JLabel lblLogo;
    private JButton btnUploadLogo;
    private byte[] logoBytes;
    private JButton btnOk, btnCancel;
    private DoiBong doiBong;

    public ThemDoiBongDialog(Frame parent, List<QuocGiaItem> dsQuocGia, List<SanVanDongItem> dsSanVanDong) {
        super(parent, "Thêm Đội Bóng", true);
        setSize(420, 320);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                doiBong = null;
                dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

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
        gbc.weightx = 1.0;
        panel.add(tfTenDoi, gbc);

        // Quốc gia
        row++;
        JLabel lblQuocGia = new JLabel("Chọn Quốc Gia:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(lblQuocGia, gbc);

        cbQuocGia = new JComboBox<>();
        for (QuocGiaItem qg : dsQuocGia) {
            cbQuocGia.addItem(qg);
        }
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(cbQuocGia, gbc);

        // Sân vận động
        row++;
        JLabel lblSVD = new JLabel("Chọn Sân Vận Động:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(lblSVD, gbc);

        cbSVD = new JComboBox<>();
        for (SanVanDongItem svd : dsSanVanDong) {
            cbSVD.addItem(svd);
        }
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(cbSVD, gbc);

        // Logo đội bóng
        row++;
        JLabel lblLogoText = new JLabel("Chọn Logo Đội:");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(lblLogoText, gbc);

        lblLogo = new JLabel("Chưa có logo"); // Khởi tạo với text
        btnUploadLogo = new JButton("Tải Logo");
        btnUploadLogo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files (PNG, JPG, JPEG)", "jpg", "jpeg", "png"));
            int res = fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    logoBytes = Files.readAllBytes(file.toPath());
                    ImageIcon icon = new ImageIcon(new ImageIcon(logoBytes).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
                    lblLogo.setIcon(icon); 
                    lblLogo.setText(null); 
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi đọc file ảnh");
                    lblLogo.setIcon(null); 
                    lblLogo.setText("Chưa có logo");
                }
            }
        });

        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        logoPanel.add(btnUploadLogo);
        logoPanel.add(lblLogo);

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(logoPanel, gbc);

        // Nút thêm đội bóng
        row++;
        btnOk = new JButton("Thêm Đội Bóng");
        btnCancel = new JButton("Hủy");
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButton.add(btnOk);
        pnlButton.add(btnCancel);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 3;
        gbc.weightx = 0;
        panel.add(pnlButton, gbc);

        // Xử lý sự kiện nút Thêm
        btnOk.addActionListener(e -> {
            String tenDoi = tfTenDoi.getText().trim();
            if (tenDoi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên đội bóng không được để trống");
                return;
            }

            Object selectedQuocGia = cbQuocGia.getSelectedItem();
            if (!(selectedQuocGia instanceof QuocGiaItem)) {
                JOptionPane.showMessageDialog(this, "Quốc gia không hợp lệ");
                return;
            }
            QuocGiaItem qg = (QuocGiaItem) selectedQuocGia;

            Object selectedSVD = cbSVD.getSelectedItem();
            if (!(selectedSVD instanceof SanVanDongItem)) {
                JOptionPane.showMessageDialog(this, "Sân vận động không hợp lệ");
                return;
            }
            SanVanDongItem svd = (SanVanDongItem) selectedSVD;

            try {
                doiBong = new DoiBong();
                doiBong.setTenDoi(tenDoi);
                doiBong.setMaQuocGia(qg.getMaQuocGia());
                doiBong.setMaSVD(svd.getMaSVD());
                doiBong.setLogoDoi(logoBytes);
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
            return tenSVD;
        }
    }
}