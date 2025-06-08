/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View.Admin.NhaTaiTroPanel;

import Controller.NhaTaiTroController;
import DAO.NhaTaiTroDAO;
import Model.NhaTaiTro;
import Service.NhaTaiTroService;
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

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTen, txtEmail, txtSDT, txtDiaChi, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTim, btnChonAnh;
    private byte[] logoBytes = null;

    public NhaTaiTroPanel() {
        initMyComponents();
        new NhaTaiTroController(this);
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
    }

    // Getters
    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTextField getTxtTen() {
        return txtTen;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtSDT() {
        return txtSDT;
    }

    public JTextField getTxtDiaChi() {
        return txtDiaChi;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public JButton getBtnThem() {
        return btnThem;
    }

    public JButton getBtnSua() {
        return btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnTim() {
        return btnTim;
    }

    public JButton getBtnChonAnh() {
        return btnChonAnh;
    }

    public byte[] getLogoBytes() {
        return logoBytes;
    }

    public void setLogoBytes(byte[] logoBytes) {
        this.logoBytes = logoBytes;
    }

    // Hiển thị thông báo
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Load dữ liệu vào bảng
    public void loadData() {
        List<NhaTaiTro> list = new NhaTaiTroService().getAllNhaTaiTro();
        displayData(list);
    }

    // Hiển thị dữ liệu vào bảng
    public void displayData(List<NhaTaiTro> list) {
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

    // Xóa dữ liệu nhập
    public void clearInput() {
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
