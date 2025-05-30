/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View.Admin.NhaTaiTroPanel;

import Controller.nhataitrocontroller.NhaTaiTroController;
import DAO.NhaTaiTroDAO;
import Model.NhaTaiTro;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class NhaTaiTroPanel extends javax.swing.JPanel {

    private NhaTaiTroController controller;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTen, txtEmail, txtSDT, txtDiaChi, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTim, btnChonAnh;
    private byte[] logoBytes = null;

    public NhaTaiTroPanel() {
        controller = new NhaTaiTroController();
        initMyComponents();
    }

    private void initMyComponents() {
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.add(new JLabel("Tên Nhà Tài Trợ:"));
        txtTen = new JTextField();
        inputPanel.add(txtTen);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Số Điện Thoại:"));
        txtSDT = new JTextField();
        inputPanel.add(txtSDT);

        inputPanel.add(new JLabel("Địa Chỉ:"));
        txtDiaChi = new JTextField();
        inputPanel.add(txtDiaChi);

        inputPanel.add(new JLabel("Logo:"));
        btnChonAnh = new JButton("Chọn ảnh");
        inputPanel.add(btnChonAnh);

        // Panel nút thao tác
        JPanel buttonPanel = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        txtTimKiem = new JTextField(20);
        btnTim = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Tìm kiếm (Tên hoặc Email):"));
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTim);

        // Model và bảng
        model = new DefaultTableModel(new String[]{"Mã NTT", "Tên Nhà Tài Trợ", "Email", "SĐT", "Địa Chỉ", "Logo"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 5) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(column);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(60);

        JScrollPane scrollPane = new JScrollPane(table);

        // Thêm các panel vào layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        // Sự kiện chọn dòng trên bảng
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtTen.setText(model.getValueAt(selectedRow, 1).toString());
                    txtEmail.setText(model.getValueAt(selectedRow, 2).toString());
                    txtSDT.setText(model.getValueAt(selectedRow, 3).toString());
                    txtDiaChi.setText(model.getValueAt(selectedRow, 4).toString());
                }
            }
        });

        // Sự kiện chọn ảnh
        btnChonAnh.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();
            int res = fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                try {
                    logoBytes = java.nio.file.Files.readAllBytes(fileChooser.getSelectedFile().toPath());
                    JOptionPane.showMessageDialog(this, "Đã chọn ảnh logo.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi đọc file ảnh.");
                }
            }
        });

        // Sự kiện thêm
        btnThem.addActionListener(event -> {
            if (validateInput()) {
                NhaTaiTro ntt = new NhaTaiTro(
                        txtTen.getText(),
                        logoBytes,
                        txtEmail.getText(),
                        txtSDT.getText(),
                        txtDiaChi.getText()
                );
                if (controller.addNhaTaiTro(ntt)) {
                    JOptionPane.showMessageDialog(this, "Thêm nhà tài trợ thành công!");
                    loadData();
                    clearInput();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm nhà tài trợ thất bại.");
                }
            }
        });

        // Sự kiện sửa
        btnSua.addActionListener(event -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Chọn nhà tài trợ cần sửa!");
                return;
            }
            if (validateInput()) {
                int maNTT = (int) model.getValueAt(row, 0);
                NhaTaiTro ntt = new NhaTaiTro(
                        maNTT,
                        txtTen.getText(),
                        logoBytes,
                        txtEmail.getText(),
                        txtSDT.getText(),
                        txtDiaChi.getText()
                );
                if (controller.updateNhaTaiTro(ntt)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật nhà tài trợ thành công!");
                    loadData();
                    clearInput();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại.");
                }
            }
        });

        // Sự kiện xóa
        btnXoa.addActionListener(event -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Chọn nhà tài trợ cần xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhà tài trợ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int maNTT = (int) model.getValueAt(row, 0);
                if (controller.deleteNhaTaiTro(maNTT)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadData();
                    clearInput();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại.");
                }
            }
        });

        // Sự kiện tìm kiếm
        btnTim.addActionListener(event -> {
            String keyword = txtTimKiem.getText().trim();
            if (keyword.isEmpty()) {
                loadData();
            } else {
                search(keyword);
            }
        });

        // Load dữ liệu lần đầu
        loadData();
    }

    // Load dữ liệu
    public void loadData() {
        List<NhaTaiTro> list = controller.getAll();
        model.setRowCount(0);
        for (NhaTaiTro ntt : list) {
            ImageIcon logoIcon = null;
            if (ntt.getLogoNTT() != null) {
                ImageIcon icon = new ImageIcon(ntt.getLogoNTT());
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                logoIcon = new ImageIcon(img);
            }
            model.addRow(new Object[]{
                ntt.getMaNTT(),
                ntt.getTenNTT(),
                ntt.getEmail(),
                ntt.getSoDienThoai(),
                ntt.getDiaChi(),
                logoIcon
            });
        }
    }

    // Tìm kiếm dữ liệu
    private void search(String keyword) {
        List<NhaTaiTro> list = controller.getAll();
        model.setRowCount(0);
        for (NhaTaiTro ntt : list) {
            if (ntt.getTenNTT().toLowerCase().contains(keyword.toLowerCase())
                    || ntt.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
                ImageIcon logoIcon = null;
                if (ntt.getLogoNTT() != null) {
                    ImageIcon icon = new ImageIcon(ntt.getLogoNTT());
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    logoIcon = new ImageIcon(img);
                }
                model.addRow(new Object[]{
                    ntt.getMaNTT(),
                    ntt.getTenNTT(),
                    ntt.getEmail(),
                    ntt.getSoDienThoai(),
                    ntt.getDiaChi(),
                    logoIcon
                });
            }
        }
    }

    // Kiểm tra dữ liệu nhập
    private boolean validateInput() {
        if (txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên nhà tài trợ không được để trống!");
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email không được để trống!");
            return false;
        }
        return true;
    }

    // Xóa dữ liệu nhập
    private void clearInput() {
        txtTen.setText("");
        txtEmail.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtTimKiem.setText("");
        logoBytes = null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
