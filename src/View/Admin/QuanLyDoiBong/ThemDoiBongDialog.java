package View.Admin.QuanLyDoiBong;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.EmptyBorder;

public class ThemDoiBongDialog extends JDialog {
    private JTextField tfTenDoi;
    private JComboBox<String> cbQuocGia;
    private JComboBox<String> cbSVD;
    private JLabel lblLogo;
    private JButton btnUploadLogo;
    private byte[] logoBytes;  


    public ThemDoiBongDialog(Frame parent) {
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

        // Tên đội bóng
        JLabel lblTenDoi = new JLabel("Tên Đội Bóng:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTenDoi, gbc);

        tfTenDoi = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(tfTenDoi, gbc);

        // Quốc gia
        JLabel lblQuocGia = new JLabel("Chọn Quốc Gia:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblQuocGia, gbc);

        cbQuocGia = new JComboBox<>(); // Dữ liệu quốc gia sẽ được nạp vào sau
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(cbQuocGia, gbc);

        // Sân vận động
        JLabel lblSVD = new JLabel("Chọn Sân Vận Động:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblSVD, gbc);

        cbSVD = new JComboBox<>(); // Dữ liệu sân vận động sẽ được nạp vào sau
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(cbSVD, gbc);

        // Logo đội bóng
        JLabel lblLogoText = new JLabel("Chọn Logo Đội:");
        gbc.gridx = 0;
        gbc.gridy = 3;
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
        gbc.gridy = 3;
        panel.add(logoPanel, gbc);

        // Nút thêm đội bóng
        JButton btnAddDoiBong = new JButton("Thêm Đội Bóng");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(btnAddDoiBong, gbc);

        // Thêm panel vào dialog
        add(panel);

        setVisible(true);
    }
    
    public File chooseImageFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static void main(String[] args) {
        new ThemDoiBongDialog(null);
    }
}
