package View.Admin.QuanLyHLV;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Calendar;
import java.util.stream.Collectors;
import Model.HLV;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.DoiBongItem;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SuaXoaHLVDialog extends JDialog {
    private JTextField txtTenHLV;
    private JComboBox<Integer> cbNgay, cbThang, cbNam;
    private JComboBox<String> cbQuocGia, cbDoiBong;
    private JButton btnLuu, btnXoa, btnHuy;
    private JButton btnChonAnh;
    private JLabel lblAnh;
    private ImageIcon selectedImage = null;
    private File selectedImageFile = null;
    private boolean isUpdated = false;
    private boolean isDeleted = false;
    private int maHLV;
    private List<QuocGiaItem> dsQuocGia;
    private List<DoiBongItem> dsDoiBong;
    private byte[] originalAnhHLV;

    public SuaXoaHLVDialog(JFrame parent, HLV hlv, List<QuocGiaItem> dsQuocGia, List<DoiBongItem> dsDoiBong) {
        super(parent, "Sửa/Xóa Huấn Luyện Viên", true);
        this.maHLV = hlv.getMaHLV();
        this.dsQuocGia = dsQuocGia;
        this.dsDoiBong = dsDoiBong;
        this.originalAnhHLV = hlv.getAnhHLV();

        List<String> tenQuocGiaList = dsQuocGia.stream()
            .map(QuocGiaItem::getTenQuocGia)
            .collect(Collectors.toList());
        Collections.sort(tenQuocGiaList);

        List<String> tenDoiBongList = dsDoiBong.stream()
            .map(DoiBongItem::getTenDoiBong)
            .collect(Collectors.toList());
        Collections.sort(tenDoiBongList);

        setSize(450, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        add(new JLabel("Tên huấn luyện viên:"), gbc);
        txtTenHLV = new JTextField(hlv.getTenHLV());
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtTenHLV, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Ngày sinh:"), gbc);
        JPanel pnlNgaySinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        cbNgay = new JComboBox<>();
        for (int i = 1; i <= 31; i++) cbNgay.addItem(i);
        cbThang = new JComboBox<>();
        for (int i = 1; i <= 12; i++) cbThang.addItem(i);
        cbNam = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) cbNam.addItem(i);
        if (hlv.getNgaySinh() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(hlv.getNgaySinh());
            cbNgay.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
            cbThang.setSelectedItem(cal.get(Calendar.MONTH) + 1);
            cbNam.setSelectedItem(cal.get(Calendar.YEAR));
        }
        pnlNgaySinh.add(cbNgay);
        pnlNgaySinh.add(new JLabel("/"));
        pnlNgaySinh.add(cbThang);
        pnlNgaySinh.add(new JLabel("/"));
        pnlNgaySinh.add(cbNam);
        gbc.gridx = 1; gbc.weightx = 1;
        add(pnlNgaySinh, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Quốc gia:"), gbc);
        cbQuocGia = new JComboBox<>(tenQuocGiaList.toArray(new String[0]));
        cbQuocGia.setSelectedItem(hlv.getTenQuocGia());
        gbc.gridx = 1; gbc.weightx = 1;
        add(cbQuocGia, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Đội bóng:"), gbc);
        cbDoiBong = new JComboBox<>(tenDoiBongList.toArray(new String[0]));
        cbDoiBong.setSelectedItem(hlv.getTenDoi());
        gbc.gridx = 1; gbc.weightx = 1;
        add(cbDoiBong, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(new JLabel("Ảnh huấn luyện viên:"), gbc);

        JPanel pnlAnh = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setPreferredSize(new Dimension(80, 30));
        pnlAnh.add(btnChonAnh);

        lblAnh = new JLabel();
        lblAnh.setPreferredSize(new Dimension(80, 80));
        lblAnh.setMaximumSize(new Dimension(80, 80));
        lblAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if (hlv.getAnhHLV() != null) {
            selectedImage = new ImageIcon(hlv.getAnhHLV());
            lblAnh.setIcon(resizeImageIcon(selectedImage, 80, 80));
        }
        pnlAnh.add(lblAnh);

        gbc.gridx = 1; gbc.weightx = 1; gbc.anchor = GridBagConstraints.CENTER;
        add(pnlAnh, gbc);

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
            if (txtTenHLV.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên huấn luyện viên không được để trống!");
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
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa huấn luyện viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                isDeleted = true;
                dispose();
            }
        });

        btnHuy.addActionListener(e -> dispose());
    }

    public HLV getHLV() {
        HLV hlv = new HLV();
        hlv.setMaHLV(this.maHLV);
        hlv.setTenHLV(txtTenHLV.getText().trim());
        hlv.setTenQuocGia((String) cbQuocGia.getSelectedItem());
        hlv.setTenDoi((String) cbDoiBong.getSelectedItem());

        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, (Integer) cbNam.getSelectedItem());
            cal.set(Calendar.MONTH, (Integer) cbThang.getSelectedItem() - 1);
            cal.set(Calendar.DAY_OF_MONTH, (Integer) cbNgay.getSelectedItem());
            hlv.setNgaySinh(cal.getTime());
        } catch (Exception e) {
        }

        if (selectedImage != null) {
            hlv.setAnhHLV(imageIconToBytes(selectedImage));
        } else {
            hlv.setAnhHLV(originalAnhHLV);
        }

        String tenQuocGia = (String) cbQuocGia.getSelectedItem();
        for (QuocGiaItem qg : dsQuocGia) {
            if (qg.getTenQuocGia().equals(tenQuocGia)) {
                hlv.setMaQuocGia(qg.getMaQuocGia());
                break;
            }
        }

        String tenDoi = (String) cbDoiBong.getSelectedItem();
        for (DoiBongItem db : dsDoiBong) {
            if (db.getTenDoiBong().equals(tenDoi)) {
                hlv.setMaDoi(db.getMaDoiBong());
                break;
            }
        }

        return hlv;
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
}