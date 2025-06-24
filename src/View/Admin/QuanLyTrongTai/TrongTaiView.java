/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin.QuanLyTrongTai;
import Controller.TrongTaiController;
import Model.QuocGia;
import Model.TrongTai;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
/**
 *
 * @author ntnfa
 */
public class TrongTaiView extends JPanel {
   private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch;
    private TrongTaiController controller;

    public TrongTaiView() {
        controller = new TrongTaiController(this);
        initComponents();
        controller.loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Panel tìm kiếm (căn giữa)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(245, 245, 245));
        JLabel lblSearch = new JLabel("Tìm kiếm:");
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm");
        btnSearch.setBackground(new Color(100, 149, 237));
        btnSearch.setForeground(Color.WHITE);
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        // Bảng dữ liệu
        String[] columns = {"Mã TT", "Tên Trọng Tài", "Ngày Sinh", "Tên Quốc Gia"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnAdd.setBackground(new Color(50, 205, 50));
        btnEdit.setBackground(new Color(255, 165, 0));
        btnDelete.setBackground(new Color(220, 20, 60));
        btnAdd.setForeground(Color.WHITE);
        btnEdit.setForeground(Color.WHITE);
        btnDelete.setForeground(Color.WHITE);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        // Thêm vào panel chính
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện nút
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addTrongTai();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    TrongTai tt = new TrongTai();
                    tt.setMaTrongTai((int) tableModel.getValueAt(selectedRow, 0));
                    tt.setTenTrongTai((String) tableModel.getValueAt(selectedRow, 1));
                    try {
                        tt.setNgaySinh(new SimpleDateFormat("dd/MM/yyyy").parse((String) tableModel.getValueAt(selectedRow, 2)));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(TrongTaiView.this, "Lỗi định dạng ngày sinh!");
                    }
                    List<TrongTai> trongTais = controller.getAllTrongTai();
                    for (TrongTai t : trongTais) {
                        if (t.getMaTrongTai() == tt.getMaTrongTai()) {
                            tt.setQuocGia(t.getQuocGia());
                            break;
                        }
                    }
                    controller.editTrongTai(tt);
                } else {
                    JOptionPane.showMessageDialog(TrongTaiView.this, "Vui lòng chọn trọng tài để sửa!");
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int maTrongTai = (int) tableModel.getValueAt(selectedRow, 0);
                    int confirm = JOptionPane.showConfirmDialog(TrongTaiView.this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        controller.deleteTrongTai(maTrongTai);
                    }
                } else {
                    JOptionPane.showMessageDialog(TrongTaiView.this, "Vui lòng chọn trọng tài để xóa!");
                }
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.searchTrongTai(txtSearch.getText());
            }
        });
    }

    public void displayTrongTai(List<TrongTai> trongTais) {
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (TrongTai tt : trongTais) {
            Object[] row = {
                tt.getMaTrongTai(),
                tt.getTenTrongTai(),
                tt.getNgaySinh() != null ? sdf.format(tt.getNgaySinh()) : "",
                tt.getQuocGia() != null ? tt.getQuocGia().getTenQuocGia() : ""
            };
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản Lý Trọng Tài");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new TrongTaiView());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}