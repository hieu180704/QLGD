package View.Admin.QuanLySoDo;

import Controller.SoDoController;
import DAO.DoiBongDAO;
import Model.DoiBong;
import Model.SoDo;
import Service.SoDoService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author OS
 */
public class QuanLySoDoPanel extends javax.swing.JPanel {
private JTable table;
    private DefaultTableModel model;
    private JTextField txtTenSoDo, txtLoaiSoDo, txtChienThuat, txtSearch;
    private JComboBox<DoiBong> cboMaDoiBong;
    private JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnExport, btnImport;

    public QuanLySoDoPanel() {
        initMyComponents();
        new SoDoController(this);
    }

    private void initMyComponents() {
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.add(new JLabel("Tên Sơ Đồ:"));
        txtTenSoDo = new JTextField();
        inputPanel.add(txtTenSoDo);

        inputPanel.add(new JLabel("Loại Sơ Đồ:"));
        txtLoaiSoDo = new JTextField();
        inputPanel.add(txtLoaiSoDo);

        inputPanel.add(new JLabel("Chiến Thuật:"));
        txtChienThuat = new JTextField();
        inputPanel.add(txtChienThuat);

        inputPanel.add(new JLabel("Mã Đội Bóng:"));
        cboMaDoiBong = new JComboBox<>();
        loadDoiBongList();
        inputPanel.add(cboMaDoiBong);

        // Panel nút thao tác
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa");
        btnExport = new JButton("Xuất Excel");
        btnImport = new JButton("Nhập Excel");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnExport);
        buttonPanel.add(btnImport);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel();
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Tìm kiếm (Tên Sơ Đồ):"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        // Model và bảng
        model = new DefaultTableModel(new String[]{"Mã Sơ Đồ", "Tên Sơ Đồ", "Loại Sơ Đồ", "Chiến Thuật", "Mã Đội Bóng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trên bảng
            }
        };
        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        // Thêm các panel vào layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        loadData();
    }

    // Getters
    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTextField getTxtTenSoDo() {
        return txtTenSoDo;
    }

    public JTextField getTxtLoaiSoDo() {
        return txtLoaiSoDo;
    }

    public JTextField getTxtChienThuat() {
        return txtChienThuat;
    }

    public JTextField getTxtSearch() {
        return txtSearch;
    }

    public JComboBox<DoiBong> getCboMaDoiBong() {
        return cboMaDoiBong;
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

    // Hiển thị thông báo
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Load dữ liệu vào bảng
    public void loadData() {
        List<SoDo> list = new SoDoService().getAllSoDoRaSan();
        displayData(list);
    }

    // Hiển thị dữ liệu vào bảng
    public void displayData(List<SoDo> list) {
        model.setRowCount(0);
        for (SoDo soDo : list) {
            model.addRow(new Object[]{
                soDo.getMaSoDo(),
                soDo.getTenSoDo(),
                soDo.getLoaiSoDo(),
                soDo.getChienThuat(),
                soDo.getMaDoiBong()
            });
        }
    }

    // Xóa dữ liệu nhập
    public void clearInput() {
        txtTenSoDo.setText("");
        txtLoaiSoDo.setText("");
        txtChienThuat.setText("");
        txtSearch.setText("");
        if (cboMaDoiBong.getItemCount() > 0) {
            cboMaDoiBong.setSelectedIndex(0);
        }
    }

    // Phương thức nạp danh sách đội bóng từ bảng doibong
    private void loadDoiBongList() {
        DoiBongDAO doiBongDAO = new DoiBongDAO();
        List<DoiBong> doiBongList = doiBongDAO.findAll();
        for (DoiBong doiBong : doiBongList) {
            cboMaDoiBong.addItem(doiBong);
        }
        if (!doiBongList.isEmpty()) {
            cboMaDoiBong.setSelectedIndex(0);
        } else {
            cboMaDoiBong.addItem(new DoiBong(0, "Chưa có đội bóng", null, null, null, null));
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
