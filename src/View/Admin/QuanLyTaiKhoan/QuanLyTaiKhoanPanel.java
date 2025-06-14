/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View.Admin.QuanLyTaiKhoan;

import Controller.QuanLyTaiKhoanController;
import Model.TaiKhoan;
import Service.TaiKhoanService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyTaiKhoanPanel extends javax.swing.JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTenDangNhap, txtMatKhau, txtEmail, txtSearch;
    private JComboBox<String> cboLoaiTaiKhoan;
    private JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnExport, btnImport;

    public QuanLyTaiKhoanPanel() {
        initMyComponents();
        new QuanLyTaiKhoanController(this);
    }

    private void initMyComponents() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTenDangNhap = new JLabel("Tên Đăng Nhập:");
        lblTenDangNhap.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblTenDangNhap);
        txtTenDangNhap = new JTextField();
        txtTenDangNhap.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(txtTenDangNhap);

        JLabel lblMatKhau = new JLabel("Mật Khẩu:");
        lblMatKhau.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblMatKhau);
        txtMatKhau = new JTextField();
        txtMatKhau.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(txtMatKhau);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(txtEmail);

        JLabel lblLoaiTaiKhoan = new JLabel("Loại Tài Khoản:");
        lblLoaiTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(lblLoaiTaiKhoan);
        cboLoaiTaiKhoan = new JComboBox<>(new String[]{"1 - Admin"});
        cboLoaiTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(cboLoaiTaiKhoan);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        btnAdd = new JButton("Thêm");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnUpdate = new JButton("Cập nhật");
        btnUpdate.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete = new JButton("Xóa");
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnExport = new JButton("Xuất Excel");
        btnExport.setFont(new Font("Arial", Font.BOLD, 14));
        btnImport = new JButton("Nhập Excel");
        btnImport.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnExport);
        buttonPanel.add(btnImport);

        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lblSearch = new JLabel("Tìm kiếm (Tên Đăng Nhập):");
        lblSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        model = new DefaultTableModel(new String[]{"Mã Tài Khoản", "Tên Đăng Nhập", "Mật Khẩu", "Email", "Loại Tài Khoản"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Cho phép chọn một dòng
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        loadData();
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTextField getTxtTenDangNhap() {
        return txtTenDangNhap;
    }

    public JTextField getTxtMatKhau() {
        return txtMatKhau;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public JComboBox<String> getCboLoaiTaiKhoan() {
        return cboLoaiTaiKhoan;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public JButton getBtnExport() {
        return btnExport;
    }

    public JButton getBtnImport() {
        return btnImport;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void loadData() {
        List<TaiKhoan> list = new TaiKhoanService().getAllTaiKhoan();
        displayData(list);
    }

    public void displayData(List<TaiKhoan> list) {
        model.setRowCount(0);
        for (TaiKhoan tk : list) {
            model.addRow(new Object[]{
                tk.getMataikhoan(),
                tk.getTendangnhap(),
                tk.getMatkhau(),
                tk.getEmail(),
                tk.getLoaitaikhoan() == 1 ? "Admin" : "User"
            });
        }
    }

    public void clearInput() {
        txtTenDangNhap.setText("");
        txtTenDangNhap.setEnabled(true);
        txtMatKhau.setText("");
        txtEmail.setText("");
        txtSearch.setText("");
        cboLoaiTaiKhoan.setSelectedIndex(0);
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
