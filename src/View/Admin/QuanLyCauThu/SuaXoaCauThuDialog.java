package View.Admin.QuanLyCauThu;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Calendar;
import java.util.stream.Collectors;
import Model.CauThu;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.DoiBongItem;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SuaXoaCauThuDialog extends JDialog {
    private JTextField txtTen, txtChieuCao, txtCanNang, txtSoAo;
    private JComboBox<Integer> cbNgay, cbThang, cbNam;
    private JComboBox<String> cbQuocGia, cbDoiBong;
    private JButton btnLuu, btnXoa, btnHuy;
    private JButton btnChonAnh;
    private JLabel lblAnh;
    private ImageIcon selectedImage = null;
    private File selectedImageFile = null;
    private boolean isUpdated = false;
    private boolean isDeleted = false;
    private int maCauThu;
    private List<QuocGiaItem> dsQuocGia; 
    private List<DoiBongItem> dsDoiBong; 

    public SuaXoaCauThuDialog(JFrame parent, CauThu cauThu, List<QuocGiaItem> dsQuocGia, List<DoiBongItem> dsDoiBong) {
        this.maCauThu = cauThu.getMaCauThu();
        this.dsQuocGia = dsQuocGia;
        this.dsDoiBong = dsDoiBong;
        
        super(parent, "Sửa/Xóa cầu thủ", true);

        List<String> tenQuocGiaList = dsQuocGia.stream()
            .map(QuocGiaItem::getTenQuocGia)
            .collect(Collectors.toList());

        List<String> tenDoiBongList = dsDoiBong.stream()
            .map(db -> db.getTenDoiBong())
            .collect(Collectors.toList());

        setSize(450, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0 - Tên cầu thủ
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        add(new JLabel("Tên cầu thủ:"), gbc);
        txtTen = new JTextField(cauThu.getTenCauThu());
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtTen, gbc);

        // Row 1 - Ngày sinh
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
        if (cauThu.getNgaySinh() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(cauThu.getNgaySinh());
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

        // Row 2 - Quốc gia
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Quốc gia:"), gbc);
        cbQuocGia = new JComboBox<>(tenQuocGiaList.toArray(new String[0]));
        cbQuocGia.setSelectedItem(cauThu.getTenQuocGia());
        gbc.gridx = 1; gbc.weightx = 1;
        add(cbQuocGia, gbc);

        // Row 3 - Đội bóng
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Đội bóng:"), gbc);
        cbDoiBong = new JComboBox<>(tenDoiBongList.toArray(new String[0]));
        cbDoiBong.setSelectedItem(cauThu.getTenDoi());
        gbc.gridx = 1; gbc.weightx = 1;
        add(cbDoiBong, gbc);

        // Row 4 - Chiều cao
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Chiều cao (cm):"), gbc);
        txtChieuCao = new JTextField(String.valueOf(cauThu.getChieuCao()));
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtChieuCao, gbc);

        // Row 5 - Cân nặng
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Cân nặng (kg):"), gbc);
        txtCanNang = new JTextField(String.valueOf(cauThu.getCanNang()));
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtCanNang, gbc);

        // Row 6 - Số áo
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        add(new JLabel("Số áo:"), gbc);
        txtSoAo = new JTextField(String.valueOf(cauThu.getSoAo()));
        gbc.gridx = 1; gbc.weightx = 1;
        add(txtSoAo, gbc);

        // Row 7 - Ảnh cầu thủ
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(new JLabel("Ảnh cầu thủ:"), gbc);

        JPanel pnlAnh = new JPanel(new BorderLayout(5, 5));
        btnChonAnh = new JButton("Chọn ảnh");
        pnlAnh.add(btnChonAnh, BorderLayout.WEST);

        lblAnh = new JLabel();
        lblAnh.setPreferredSize(new Dimension(100, 100));
        lblAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pnlAnh.add(lblAnh, BorderLayout.CENTER);

        gbc.gridx = 1; gbc.weightx = 1; gbc.anchor = GridBagConstraints.CENTER;
        add(pnlAnh, gbc);

        byte[] anhBytes = cauThu.getAnhCauThu();
        if (anhBytes != null) {
            selectedImage = new ImageIcon(anhBytes);
            lblAnh.setIcon(resizeImageIcon(selectedImage, 100, 100));
        }

        // Row 8 - Nút bấm (nằm ngang)
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

        // Xử lý chọn ảnh
        btnChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                selectedImage = new ImageIcon(selectedImageFile.getAbsolutePath());
                lblAnh.setIcon(resizeImageIcon(selectedImage, 100, 100));
            }
        });
        btnLuu.addActionListener(e -> {
            if (txtTen.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên cầu thủ không được để trống!");
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
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa cầu thủ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                isDeleted = true;
                dispose();
            }
        });

        btnHuy.addActionListener(e -> dispose());
    }

    public CauThu getCauThuSuaXoa() {
        CauThu ct = new CauThu();
        ct.setMaCauThu(this.maCauThu);
        ct.setTenCauThu(txtTen.getText().trim());
        ct.setTenQuocGia((String) cbQuocGia.getSelectedItem());
        ct.setTenDoi((String) cbDoiBong.getSelectedItem());
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, (Integer) cbNam.getSelectedItem());
            cal.set(Calendar.MONTH, (Integer) cbThang.getSelectedItem() - 1);
            cal.set(Calendar.DAY_OF_MONTH, (Integer) cbNgay.getSelectedItem());
            ct.setNgaySinh(cal.getTime());
            ct.setChieuCao(Integer.parseInt(txtChieuCao.getText().trim()));
            ct.setCanNang(Integer.parseInt(txtCanNang.getText().trim()));
            ct.setSoAo(Integer.parseInt(txtSoAo.getText().trim()));
        } catch (Exception e) {
        }

        if (selectedImage != null) {
            ct.setAnhCauThu(imageIconToBytes(selectedImage));
        } else {
            ct.setAnhCauThu(null);
        }

        // Thêm logic để gán maQuocGia
        String tenQuocGia = (String) cbQuocGia.getSelectedItem();
        for (QuocGiaItem qg : dsQuocGia) {
            if (qg.getTenQuocGia().equals(tenQuocGia)) {
                ct.setMaQuocGia(qg.getMaQuocGia()); 
                break;
            }
        }
        
        String tenDoi = (String) cbDoiBong.getSelectedItem();
        for (DoiBongItem db : dsDoiBong) {
            if (db.getTenDoiBong().equals(tenDoi)) {
                ct.setMaDoi(db.getMaDoiBong()); // Giả sử CauThu có setMaDoi()
                break;
            }
        }
        return ct;
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

    public CauThu getCauThu() {
        return getCauThuSuaXoa();
    }
}