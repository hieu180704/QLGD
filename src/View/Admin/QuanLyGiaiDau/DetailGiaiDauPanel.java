package View.Admin.QuanLyGiaiDau;

import Controller.DetailGiaiDauController;
import Model.GiaiDau;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DetailGiaiDauPanel extends JPanel {

    private JTextField txtTenGiaiDau;
    private DatePicker dpNgayTao;
    private DatePicker dpNgayBatDau;
    private DatePicker dpNgayKetThuc;

    private JLabel lblPreview;
    private byte[] anhGiaiDau;
    private GiaiDau currentGiaiDau;

    private JButton btnLuu, btnXoa, btnQuayLai, btnChonAnh;

    public DetailGiaiDauPanel() {
        designDetailGiaiDauPanel();
    }

    private void designDetailGiaiDauPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));
        setBorder(new EmptyBorder(20, 60, 20, 60));

        JLabel lblTitle = new JLabel("CHI TIẾT GIẢI ĐẤU");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(30, 30, 100));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
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

        // Ngày tạo
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Ngày tạo:"), gbc);
        dpNgayTao = new DatePicker();
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(dpNgayTao, gbc);

        // Ngày bắt đầu
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Ngày bắt đầu:"), gbc);
        dpNgayBatDau = new DatePicker();
        gbc.gridx = 1;
        gbc.gridy = row++;
        formPanel.add(dpNgayBatDau, gbc);

        // Ngày kết thúc
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
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));

        btnLuu = new JButton("Lưu");
        btnLuu.setPreferredSize(new Dimension(100, 40));
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLuu.setBackground(new Color(50, 160, 90));
        btnLuu.setForeground(Color.WHITE);
        bottomPanel.add(btnLuu);

        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 40));
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa.setBackground(new Color(220, 50, 50));
        btnXoa.setForeground(Color.WHITE);
        bottomPanel.add(btnXoa);

        btnQuayLai = new JButton("Quay lại");
        btnQuayLai.setPreferredSize(new Dimension(100, 40));
        btnQuayLai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bottomPanel.add(btnQuayLai);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void addController(DetailGiaiDauController controller) {
        btnLuu.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnQuayLai.addActionListener(controller);
        btnChonAnh.addActionListener(controller);
    }

    // Getter & Setter
    public String getTenGiaiDau() {
        return txtTenGiaiDau.getText().trim();
    }

    public void setTenGiaiDau(String ten) {
        txtTenGiaiDau.setText(ten);
    }

    public LocalDate getNgayTao() {
        return dpNgayTao.getDate();
    }

    public void setNgayTao(Date date) {
        if (date == null) {
            dpNgayTao.clear();
            return;
        }
        if (date instanceof java.sql.Date) {
            dpNgayTao.setDate(((java.sql.Date) date).toLocalDate());
        } else {
            dpNgayTao.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    public LocalDate getNgayBatDau() {
        return dpNgayBatDau.getDate();
    }

    public void setNgayBatDau(Date date) {
        if (date == null) {
            dpNgayBatDau.clear();
            return;
        }
        if (date instanceof java.sql.Date) {
            dpNgayBatDau.setDate(((java.sql.Date) date).toLocalDate());
        } else {
            dpNgayBatDau.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    public LocalDate getNgayKetThuc() {
        return dpNgayKetThuc.getDate();
    }

    public void setNgayKetThuc(Date date) {
        if (date == null) {
            dpNgayKetThuc.clear();
            return;
        }
        if (date instanceof java.sql.Date) {
            dpNgayKetThuc.setDate(((java.sql.Date) date).toLocalDate());
        } else {
            dpNgayKetThuc.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    public byte[] getAnhGiaiDau() {
        return anhGiaiDau;
    }

    public void setAnhGiaiDau(byte[] data) {
        anhGiaiDau = data;
        if (data != null && data.length > 0) {
            ImageIcon icon = new ImageIcon(data);
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblPreview.setIcon(new ImageIcon(img));
            lblPreview.setText("");
        } else {
            lblPreview.setIcon(null);
            lblPreview.setText("Chưa có ảnh");
        }
    }

    public GiaiDau getCurrentGiaiDau() {
        return currentGiaiDau;
    }

    public void setCurrentGiaiDau(GiaiDau gd) {
        this.currentGiaiDau = gd;
        if (gd != null) {
            setTenGiaiDau(gd.getTenGiaiDau());
            setNgayTao(gd.getNgayTaoGiai());
            setNgayBatDau(gd.getNgayBatDau());
            setNgayKetThuc(gd.getNgayKetThuc());
            setAnhGiaiDau(gd.getAnhGiaiDau());
        }
    }

    // Các nút
    public JButton getBtnLuu() {
        return btnLuu;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnQuayLai() {
        return btnQuayLai;
    }

    public JButton getBtnChonAnh() {
        return btnChonAnh;
    }

}
