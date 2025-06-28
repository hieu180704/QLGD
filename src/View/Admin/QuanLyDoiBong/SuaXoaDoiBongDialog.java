package View.Admin.QuanLyDoiBong;

import Model.DoiBong;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import View.Admin.QuanLyDoiBong.ThemDoiBongDialog.SanVanDongItem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;

public class SuaXoaDoiBongDialog extends JDialog {
    private JTextField txtTenDoi;
    private JComboBox<QuocGiaItem> cbQuocGia;
    private JComboBox<SanVanDongItem> cbSanVanDong;
    private JButton btnChonAnh;
    private JLabel lblAnh;
    private ImageIcon selectedImage = null;
    private File selectedImageFile = null;
    private boolean isUpdated = false;
    private boolean isDeleted = false;
    private int maDoiBong;
    private byte[] originalLogoDoi;

    public SuaXoaDoiBongDialog(JFrame parent, DoiBong db, List<QuocGiaItem> dsQuocGiaItems, List<SanVanDongItem> dsSanVanDong) {
        super(parent, "Sửa/Xóa Đội Bóng", true);
        this.maDoiBong = db.getMaDoiBong();
        this.originalLogoDoi = db.getLogoDoi();

        // Sắp xếp danh sách quốc gia và sân vận động theo thứ tự A-Z
        Collections.sort(dsQuocGiaItems, Comparator.comparing(QuocGiaItem::getTenQuocGia));
        Collections.sort(dsSanVanDong, Comparator.comparing(SanVanDongItem::getTenSVD));

        setSize(450, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        add(new JLabel("Tên đội bóng:"), gbc);
        txtTenDoi = new JTextField(db.getTenDoi() != null ? db.getTenDoi() : "");
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtTenDoi, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Quốc gia:"), gbc);
        cbQuocGia = new JComboBox<>(dsQuocGiaItems.toArray(new QuocGiaItem[0]));
        cbQuocGia.setSelectedItem(dsQuocGiaItems.stream().filter(qg -> qg.getMaQuocGia() == db.getMaQuocGia()).findFirst().orElse(null));
        gbc.gridx = 1; gbc.weightx = 1;
        add(cbQuocGia, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Sân vận động:"), gbc);
        cbSanVanDong = new JComboBox<>(dsSanVanDong.toArray(new SanVanDongItem[0]));
        cbSanVanDong.setSelectedItem(dsSanVanDong.stream().filter(svd -> svd.getMaSVD() == db.getMaSVD()).findFirst().orElse(null));
        gbc.gridx = 1; gbc.weightx = 1;
        add(cbSanVanDong, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(new JLabel("Logo đội bóng:"), gbc);

        JPanel pnlAnh = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setPreferredSize(new Dimension(80, 30));
        pnlAnh.add(btnChonAnh);

        lblAnh = new JLabel();
        lblAnh.setPreferredSize(new Dimension(80, 80));
        lblAnh.setMaximumSize(new Dimension(80, 80));
        lblAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if (db.getLogoDoi() != null) {
            selectedImage = new ImageIcon(db.getLogoDoi());
            lblAnh.setIcon(resizeImageIcon(selectedImage, 80, 80));
        }
        pnlAnh.add(lblAnh);

        gbc.gridx = 1; gbc.weightx = 1; gbc.anchor = GridBagConstraints.CENTER;
        add(pnlAnh, gbc);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JButton btnLuu = new JButton("Lưu");
        JButton btnXoa = new JButton("Xóa");
        JButton btnHuy = new JButton("Hủy");
        pnlButtons.add(btnLuu);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnHuy);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        add(pnlButtons, gbc);

        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                selectedImage = new ImageIcon(selectedImageFile.getAbsolutePath());
                lblAnh.setIcon(resizeImageIcon(selectedImage, 80, 80));
            }
        });

        btnLuu.addActionListener(e -> {
            if (txtTenDoi.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên đội bóng không được để trống!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                isUpdated = true;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        db.setMaDoiBong(maDoiBong);
        db.setTenDoi(txtTenDoi.getText().trim());

        QuocGiaItem qg = (QuocGiaItem) cbQuocGia.getSelectedItem();
        if (qg != null) db.setMaQuocGia(qg.getMaQuocGia());

        SanVanDongItem svd = (SanVanDongItem) cbSanVanDong.getSelectedItem();
        if (svd != null) db.setMaSVD(svd.getMaSVD());

        if (selectedImage != null) {
            db.setLogoDoi(imageIconToBytes(selectedImage));
        } else {
            db.setLogoDoi(originalLogoDoi);
        }

        return db;
    }

    private byte[] imageIconToBytes(ImageIcon icon) {
        try {
            Image img = icon.getImage();
            BufferedImage buffered = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
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
}