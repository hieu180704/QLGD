package View.Admin.QuanLyDoiBong;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import Model.DoiBong;
import Model.QuocGia;
import Model.SanVanDong;
import View.Admin.QuanLyDoiBong.ThemDoiBongDialog.SanVanDongItem;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;

public class SuaXoaDoiBongDialog extends JDialog {
    private JTextField txtTenDoi;
    private JComboBox<String> cbQuocGia; 
    private JComboBox<String> cbSanVanDong; 
    private JButton btnLuu, btnXoa, btnHuy;
    private JButton btnChonLogo;
    private JLabel lblLogo;
    private ImageIcon selectedLogo = null;
    private File selectedLogoFile = null;
    private boolean isUpdated = false;
    private boolean isDeleted = false;
    private int maDoiBong;
    private List<QuocGiaItem> dsQuocGia;
    private List<SanVanDongItem> dsSanVanDong;

    public SuaXoaDoiBongDialog(JFrame parent, DoiBong doiBong, List<QuocGiaItem> dsQuocGia, List<SanVanDongItem> dsSanVanDong) {
        this.maDoiBong = doiBong.getMaDoiBong();
        this.dsQuocGia = dsQuocGia;
        this.dsSanVanDong = dsSanVanDong;

        super(parent, "Sửa/Xóa đội bóng", true);

        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0 - Tên đội bóng
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        add(new JLabel("Tên đội bóng:"), gbc);
        txtTenDoi = new JTextField(doiBong.getTenDoi());
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtTenDoi, gbc);

        // Row 1 - Quốc gia
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Quốc gia:"), gbc);
        List<String> tenQuocGiaList = dsQuocGia.stream()
            .map(QuocGiaItem::getTenQuocGia)
            .collect(Collectors.toList());
        cbQuocGia = new JComboBox<>(tenQuocGiaList.toArray(new String[0]));
        cbQuocGia.setSelectedItem(doiBong.getTenQuocGia()); // Dựa vào tenQuocGia
        gbc.gridx = 1; gbc.weightx = 1;
        add(cbQuocGia, gbc);

        // Row 2 - Sân vận động
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Sân vận động:"), gbc);
        List<String> tenSanVanDongList = dsSanVanDong.stream()
            .map(SanVanDongItem::getTenSVD)
            .collect(Collectors.toList());
        cbSanVanDong = new JComboBox<>(tenSanVanDongList.toArray(new String[0]));
        cbSanVanDong.setSelectedItem(doiBong.getTenSanVanDong()); // Dựa vào tenSanVanDong
        gbc.gridx = 1; gbc.weightx = 1;
        add(cbSanVanDong, gbc);

        // Row 3 - Logo đội bóng
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Logo đội bóng:"), gbc);

        JPanel pnlLogo = new JPanel(new BorderLayout(5, 5));
        btnChonLogo = new JButton("Chọn logo");
        pnlLogo.add(btnChonLogo, BorderLayout.WEST);

        lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(100, 100));
        lblLogo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pnlLogo.add(lblLogo, BorderLayout.CENTER);

        gbc.gridx = 1; gbc.weightx = 1; gbc.anchor = GridBagConstraints.CENTER;
        add(pnlLogo, gbc);

        byte[] logoBytes = doiBong.getLogoDoi();
        if (logoBytes != null) {
            selectedLogo = new ImageIcon(logoBytes);
            lblLogo.setIcon(resizeImageIcon(selectedLogo, 100, 100));
        }

        // Row 4 - Nút bấm (nằm ngang)
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnLuu = new JButton("Lưu");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");
        pnlButtons.add(btnLuu);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnHuy);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        add(pnlButtons, gbc);

        // Xử lý chọn logo
        btnChonLogo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedLogoFile = fileChooser.getSelectedFile();
                selectedLogo = new ImageIcon(selectedLogoFile.getAbsolutePath());
                lblLogo.setIcon(resizeImageIcon(selectedLogo, 100, 100));
            }
        });

        btnLuu.addActionListener(e -> {
            if (txtTenDoi.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên đội bóng không được để trống!");
                return;
            }
            try {
                isUpdated = true;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ");
            }
        });

        btnXoa.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa đội bóng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                isDeleted = true;
                dispose();
            }
        });

        btnHuy.addActionListener(e -> dispose());
    }

    public DoiBong getDoiBongSuaXoa() {
        DoiBong db = new DoiBong();
        db.setMaDoiBong(this.maDoiBong);
        db.setTenDoi(txtTenDoi.getText().trim());

        // Lấy mã từ tên quốc gia
        String tenQuocGia = (String) cbQuocGia.getSelectedItem();
        for (QuocGiaItem qg : dsQuocGia) {
            if (qg.getTenQuocGia().equals(tenQuocGia)) {
                QuocGia quocGia = new QuocGia();
                quocGia.setMaQuocGia(qg.getMaQuocGia());
                quocGia.setTenQuocGia(qg.getTenQuocGia());
                db.setQuocGia(quocGia);
                break;
            }
        }

        // Lấy mã từ tên sân vận động
        String tenSVD = (String) cbSanVanDong.getSelectedItem();
        for (SanVanDongItem svd : dsSanVanDong) {
            if (svd.getTenSVD().equals(tenSVD)) {
                SanVanDong sanVanDong = new SanVanDong();
                sanVanDong.setMaSVD(svd.getMaSVD());
                sanVanDong.setTenSVD(svd.getTenSVD());
                db.setSanVanDong(sanVanDong);
                break;
            }
        }

        // Giữ logo cũ nếu không chọn logo mới
        if (selectedLogo != null) {
            db.setLogoDoi(imageIconToBytes(selectedLogo));
        } else {
            db.setLogoDoi(null); // Hoặc db.setLogoDoi(doiBong.getLogoDoi()) để giữ logo cũ
        }

        return db;
    }

    private byte[] imageIconToBytes(ImageIcon icon) {
        try {
            Image img = icon.getImage();
            BufferedImage buffered = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = buffered.createGraphics();
            g2.drawImage(img, 0, 0, null);
            g2.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffered, "png", baos);
            baos.flush();
            byte[] imageInBytes = baos.toByteArray();
            baos.close();
            return imageInBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public DoiBong getDoiBong() {
        return getDoiBongSuaXoa();
    }
}