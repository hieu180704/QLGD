package View.Admin.QuanLyGiaiDau;

import Model.GiaiDau;
import Model.DoiBong;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class DetailGiaiDauDialog extends JDialog {

    private DefaultListModel<String> modelDoiChuaThamGia;
    private DefaultListModel<String> modelDoiDaThamGia;
    private Map<String, Integer> mapChuaThamGia = new HashMap<>();
    private Map<String, Integer> mapDaThamGia = new HashMap<>();

    private JTextField txtTenGiaiDau;
    private DatePicker dpNgayBatDau;
    private DatePicker dpNgayKetThuc;
    private JLabel lblPreview;
    
    private byte[] anhGiaiDau;
    private GiaiDau currentGiaiDau;

    private JButton btnLuu, btnXoa, btnQuayLai, btnChonAnh;
    private JList<String> listDoiChuaThamGia;
    private JList<String> listDoiDaThamGia;
    private JButton btnThemDoi, btnBoDoi;

    public DetailGiaiDauDialog(Window owner) {
        super(owner, "CHI TIẾT GIẢI ĐẤU", ModalityType.APPLICATION_MODAL);
        designDetailGiaiDauDialog();
        pack();
        setLocationRelativeTo(owner);
    }

    private void designDetailGiaiDauDialog() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(20, 60, 20, 60));

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

        add(formPanel, BorderLayout.NORTH);

        // Danh sách đội bóng và nút thêm/bỏ
        JPanel teamsPanel = new JPanel(new GridBagLayout());
        teamsPanel.setOpaque(false);
        GridBagConstraints gbcTeams = new GridBagConstraints();
        gbcTeams.insets = new Insets(5, 5, 5, 5);
        gbcTeams.fill = GridBagConstraints.BOTH;
        gbcTeams.weightx = 0.4;
        gbcTeams.weighty = 1.0;

        // Đội chưa tham gia
        modelDoiChuaThamGia = new DefaultListModel<>();
        listDoiChuaThamGia = new JList<>(modelDoiChuaThamGia);
        listDoiChuaThamGia.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollChuaThamGia = new JScrollPane(listDoiChuaThamGia);
        scrollChuaThamGia.setPreferredSize(new Dimension(200, 300));

        gbcTeams.gridx = 0;
        gbcTeams.gridy = 0;
        teamsPanel.add(new JLabel("Đội bóng chưa tham gia"), gbcTeams);
        gbcTeams.gridy = 1;
        teamsPanel.add(scrollChuaThamGia, gbcTeams);

        // Nút thêm/bỏ
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        buttonPanel.setOpaque(false);
        btnThemDoi = new JButton(">>");
        btnBoDoi = new JButton("<<");
        buttonPanel.add(btnThemDoi);
        buttonPanel.add(btnBoDoi);

        gbcTeams.gridx = 1;
        gbcTeams.gridy = 1;
        gbcTeams.weightx = 0.1;
        gbcTeams.fill = GridBagConstraints.NONE;
        teamsPanel.add(buttonPanel, gbcTeams);

        // Đội đã tham gia
        modelDoiDaThamGia = new DefaultListModel<>();
        listDoiDaThamGia = new JList<>(modelDoiDaThamGia);
        listDoiDaThamGia.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollDaThamGia = new JScrollPane(listDoiDaThamGia);
        scrollDaThamGia.setPreferredSize(new Dimension(200, 300));

        gbcTeams.gridx = 2;
        gbcTeams.gridy = 0;
        gbcTeams.weightx = 0.4;
        gbcTeams.fill = GridBagConstraints.BOTH;
        teamsPanel.add(new JLabel("Đội bóng đã tham gia"), gbcTeams);
        gbcTeams.gridy = 1;
        teamsPanel.add(scrollDaThamGia, gbcTeams);

        add(teamsPanel, BorderLayout.CENTER);

        // Nút dưới cùng
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setOpaque(false);

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

    // Thêm controller cho các nút
    public void addController(ActionListener controller) {
        btnLuu.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnQuayLai.addActionListener(controller);
        btnChonAnh.addActionListener(controller);
        btnThemDoi.addActionListener(controller);
        btnBoDoi.addActionListener(controller);
    }

    // Getter & Setter và các phương thức quản lý dữ liệu
    public String getTenGiaiDau() {
        return txtTenGiaiDau.getText().trim();
    }

    public void setTenGiaiDau(String ten) {
        txtTenGiaiDau.setText(ten);
    }

    public LocalDate getNgayBatDau() {
        return dpNgayBatDau.getDate();
    }

    public void setNgayBatDau(LocalDate date) {
        if (date == null) {
            dpNgayBatDau.clear();
            return;
        }
        dpNgayBatDau.setDate(date);
    }



    public LocalDate getNgayKetThuc() {
        return dpNgayKetThuc.getDate();
    }

    public void setNgayKetThuc(LocalDate date) {
        if (date == null) {
            dpNgayKetThuc.clear();
            return;
        }
        dpNgayKetThuc.setDate(date);
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
            setNgayBatDau(gd.getNgayBatDau());
            setNgayKetThuc(gd.getNgayKetThuc());
            setAnhGiaiDau(gd.getAnhGiaiDau());
        }
    }

    // Các nút getter
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

    public JButton getBtnThemDoi() {
        return btnThemDoi;
    }

    public JButton getBtnBoDoi() {
        return btnBoDoi;
    }

    // Quản lý danh sách đội
    public void setDoiChuaThamGia(List<DoiBong> doiList) {
        modelDoiChuaThamGia.clear();
        mapChuaThamGia.clear();
        for (DoiBong d : doiList) {
            String display = d.getTenDoi();
            modelDoiChuaThamGia.addElement(display);
            mapChuaThamGia.put(display, d.getMaDoiBong());
        }
    }

    public void setDoiDaThamGia(List<DoiBong> doiList) {
        modelDoiDaThamGia.clear();
        mapDaThamGia.clear();
        for (DoiBong d : doiList) {
            String display = d.getTenDoi();
            modelDoiDaThamGia.addElement(display);
            mapDaThamGia.put(display, d.getMaDoiBong());
        }
    }

    public List<Integer> getSelectedMaDoiChuaThamGia() {
        List<Integer> list = new ArrayList<>();
        for (String s : listDoiChuaThamGia.getSelectedValuesList()) {
            Integer id = mapChuaThamGia.get(s);
            if (id != null) {
                list.add(id);
            }
        }
        return list;
    }

    public List<Integer> getSelectedMaDoiDaThamGia() {
        List<Integer> list = new ArrayList<>();
        for (String s : listDoiDaThamGia.getSelectedValuesList()) {
            Integer id = mapDaThamGia.get(s);
            if (id != null) {
                list.add(id);
            }
        }
        return list;
    }
}
