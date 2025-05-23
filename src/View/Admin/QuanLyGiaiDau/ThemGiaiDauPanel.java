package View.Admin.QuanLyGiaiDau;

import Controller.ThemGiaiDauController;
import DAO.TheThucDAO;
import Model.TheThuc;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ThemGiaiDauPanel extends JPanel {

    private JTextField txtTenGiaiDau;
    private JComboBox<TheThuc> cbTheThuc;
    private DatePicker dpNgayBatDau;
    private DatePicker dpNgayKetThuc;
    private JLabel lblPreview;
    private byte[] anhGiaiDau;

    private JButton btnLuu, btnHuy, btnQuayLai, btnChonAnh;

    public ThemGiaiDauPanel() {
        designThemGiaiDauPanel();
    }

    private void designThemGiaiDauPanel() {
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

        // Tên giải đấu
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Tên giải đấu:"), gbc);
        txtTenGiaiDau = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(txtTenGiaiDau, gbc);

        // Thể thức
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Thể thức:"), gbc);
        cbTheThuc = new JComboBox<>();
        loadTheThuc();
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(cbTheThuc, gbc);

        // Ngày bắt đầu (DatePicker)
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Ngày bắt đầu:"), gbc);
        dpNgayBatDau = new DatePicker();
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(dpNgayBatDau, gbc);

        // Ngày kết thúc (DatePicker)
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Ngày kết thúc:"), gbc);
        dpNgayKetThuc = new DatePicker();
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(dpNgayKetThuc, gbc);

        // Chọn ảnh
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Logo giải đấu:"), gbc);
        btnChonAnh = new JButton("Chọn ảnh...");
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

        // Nút dưới
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        btnLuu = new JButton("Lưu");
        btnLuu.setPreferredSize(new Dimension(100, 40));
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setBackground(new Color(50, 160, 90));
        btnLuu.setForeground(Color.WHITE);
        bottomPanel.add(btnLuu);

        btnHuy = new JButton("Hủy");
        btnHuy.setPreferredSize(new Dimension(100, 40));
        btnHuy.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bottomPanel.add(btnHuy);

        btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setPreferredSize(new Dimension(100, 40));
        btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bottomPanel.add(btnQuayLai);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadTheThuc() {
        List<TheThuc> list = new TheThucDAO().findAll();
        cbTheThuc.removeAllItems();
        for (TheThuc tt : list) {
            cbTheThuc.addItem(tt);
        }
    }

    public void addController(ThemGiaiDauController controller) {
        btnLuu.addActionListener(controller);
        btnHuy.addActionListener(controller);
        btnQuayLai.addActionListener(controller);
        btnChonAnh.addActionListener(controller);
    }

    // Getter
    public String getTenGiaiDau() {
        return txtTenGiaiDau.getText().trim();
    }

    public TheThuc getTheThuc() {
        return (TheThuc) cbTheThuc.getSelectedItem();
    }

    public LocalDate getNgayBatDau() {
        return dpNgayBatDau.getDate();
    }

    public LocalDate getNgayKetThuc() {
        return dpNgayKetThuc.getDate();
    }

    public byte[] getAnhGiaiDau() {
        return anhGiaiDau;
    }

    // Setter
    public void setAnhGiaiDau(byte[] anh) {
        this.anhGiaiDau = anh;
    }

    public void setPreviewImage(Image image) {
        Image scaled = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        lblPreview.setIcon(new ImageIcon(scaled));
        lblPreview.setText("");
    }

    public JButton getBtnLuu() {
        return btnLuu;
    }

    public JButton getBtnHuy() {
        return btnHuy;
    }

    public JButton getBtnQuayLai() {
        return btnQuayLai;
    }

    public JButton getBtnChonAnh() {
        return btnChonAnh;
    }

    public void clearForm() {
        txtTenGiaiDau.setText("");
        if (cbTheThuc.getItemCount() > 0) {
            cbTheThuc.setSelectedIndex(0);
        }
        dpNgayBatDau.clear();
        dpNgayKetThuc.clear();
        anhGiaiDau = null;
        lblPreview.setIcon(null);
        lblPreview.setText("Chưa có ảnh");
    }
}
