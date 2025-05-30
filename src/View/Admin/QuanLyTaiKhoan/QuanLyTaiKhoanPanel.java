/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View.Admin.QuanLyTaiKhoan;

import Model.TaiKhoan;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyTaiKhoanPanel extends javax.swing.JPanel {

    private JTable tableTaiKhoan;
    private DefaultTableModel tableModel;
    private JTextField txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem;

    public QuanLyTaiKhoanPanel() {
        setLayout(new BorderLayout());

        // Panel trên cùng: tìm kiếm
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(20);
        btnTimKiem = new JButton("Tìm kiếm");
        panelTop.add(txtTimKiem);
        panelTop.add(btnTimKiem);
        add(panelTop, BorderLayout.NORTH);

        // Bảng tài khoản
        String[] columns = {"Mã TK", "Tên đăng nhập", "Email", "Loại TK"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tableTaiKhoan = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableTaiKhoan);
        add(scrollPane, BorderLayout.CENTER);

        // Panel nút chức năng dưới
        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        panelBottom.add(btnThem);
        panelBottom.add(btnSua);
        panelBottom.add(btnXoa);
        add(panelBottom, BorderLayout.SOUTH);
    }

    // Đổ dữ liệu lên bảng
    public void loadData(List<TaiKhoan> list) {
        tableModel.setRowCount(0);
        for (TaiKhoan tk : list) {
            tableModel.addRow(new Object[]{
                tk.getMataikhoan(),
                tk.getTendangnhap(),
                tk.getEmail(),
                tk.getLoaitaikhoan()
            });
        }
    }

    // Lấy ID tài khoản được chọn, trả về null nếu không chọn
    public Integer getSelectedTaiKhoanId() {
        int row = tableTaiKhoan.getSelectedRow();
        if (row == -1) {
            return null;
        }
        Object val = tableModel.getValueAt(row, 0);
        if (val instanceof Integer) {
            return (Integer) val;
        }
        if (val instanceof Long) {
            return ((Long) val).intValue();
        }
        if (val instanceof String) {
            return Integer.parseInt((String) val);
        }
        return null;
    }

    // Getters cho controller đăng ký sự kiện
    public JButton getBtnThem() {
        return btnThem;
    }

    public JButton getBtnSua() {
        return btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnTimKiem() {
        return btnTimKiem;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
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
