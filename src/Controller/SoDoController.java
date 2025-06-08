package Controller;

import Model.SoDo;
import Model.DoiBong;
import Service.SoDoService;
import View.Admin.QuanLySoDo.QuanLySoDoPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SoDoController {
    private QuanLySoDoPanel view;
    private SoDoService service;

    public SoDoController(QuanLySoDoPanel view) {
        this.view = view;
        this.service = new SoDoService();
        initComponents();
        loadData();
    }

    private void initComponents() {
        // Gán sự kiện cho các nút
        view.getBtnAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSoDoRaSan();
            }
        });
        view.getBtnUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSoDoRaSan();
            }
        });
        view.getBtnDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSoDoRaSan();
            }
        });
        view.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSoDoRaSan();
            }
        });
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0); // Xóa tất cả các hàng hiện tại
        for (SoDo soDo : service.getAllSoDoRaSan()) {
            model.addRow(new Object[]{soDo.getMaSoDo(), soDo.getTenSoDo(), soDo.getLoaiSoDo(), soDo.getChienThuat(), soDo.getMaDoiBong()});
        }
    }

    private void addSoDoRaSan() {
        try {
            String tenSoDo = view.getTxtTenSoDo().getText().trim();
            String loaiSoDo = view.getTxtLoaiSoDo().getText().trim();
            String chienThuat = view.getTxtChienThuat().getText().trim();
            DoiBong selectedDoiBong = (DoiBong) view.getCboMaDoiBong().getSelectedItem();
            if (selectedDoiBong == null || tenSoDo.isEmpty() || loaiSoDo.isEmpty() || chienThuat.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin hợp lệ!");
                return;
            }
            int maDoiBong = selectedDoiBong.getMaDoiBong();
            SoDo soDo = new SoDo();
            soDo.setTenSoDo(tenSoDo);
            soDo.setLoaiSoDo(loaiSoDo);
            soDo.setChienThuat(chienThuat);
            soDo.setMaDoiBong(maDoiBong);
            if (service.addSoDoRaSan(soDo)) {
                JOptionPane.showMessageDialog(view, "Thêm sơ đồ ra sân thành công!");
            } else {
                JOptionPane.showMessageDialog(view, "Thêm sơ đồ ra sân thất bại! (Trùng tên, độ dài > 50 ký tự, hoặc maDoiBong không hợp lệ)");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm sơ đồ: " + e.getMessage());
        }
        loadData();
    }

    private void updateSoDoRaSan() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int maSoDo = (int) view.getTable().getValueAt(selectedRow, 0);
                String tenSoDo = view.getTxtTenSoDo().getText().trim();
                String loaiSoDo = view.getTxtLoaiSoDo().getText().trim();
                String chienThuat = view.getTxtChienThuat().getText().trim();
                DoiBong selectedDoiBong = (DoiBong) view.getCboMaDoiBong().getSelectedItem();
                if (selectedDoiBong == null || tenSoDo.isEmpty() || loaiSoDo.isEmpty() || chienThuat.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin hợp lệ!");
                    return;
                }
                int maDoiBong = selectedDoiBong.getMaDoiBong();
                SoDo soDo = new SoDo(maSoDo, tenSoDo, loaiSoDo, chienThuat, maDoiBong);
                if (service.updateSoDoRaSan(soDo)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật sơ đồ ra sân thành công!");
                } else {
                    JOptionPane.showMessageDialog(view, "Cập nhật sơ đồ ra sân thất bại! Vui lòng kiểm tra dữ liệu.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật sơ đồ: " + e.getMessage());
            }
            loadData();
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sơ đồ để cập nhật!");
        }
    }

    private void deleteSoDoRaSan() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow != -1) {
            int maSoDo = (int) view.getTable().getValueAt(selectedRow, 0);
            int result = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa sơ đồ này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (service.deleteSoDoRaSan(maSoDo)) {
                    JOptionPane.showMessageDialog(view, "Xóa sơ đồ ra sân thành công!");
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa sơ đồ ra sân thất bại!");
                }
                loadData();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sơ đồ để xóa!");
        }
    }
    private void searchSoDoRaSan() {
        String searchText = view.getTxtSearch().getText().trim();
        if (!searchText.isEmpty()) {
            List<SoDo> searchResults = service.findSoDoRaSanByName(searchText);
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            model.setRowCount(0);
            for (SoDo soDo : searchResults) {
                model.addRow(new Object[]{
                    soDo.getMaSoDo(),
                    soDo.getTenSoDo(),
                    soDo.getLoaiSoDo(),
                    soDo.getChienThuat(),
                    soDo.getMaDoiBong()
                });
            }
            view.showMessage("Đã tìm thấy " + searchResults.size() + " kết quả.");
        } else {
            loadData();
            view.showMessage("Vui lòng nhập từ khóa tìm kiếm!");
        }
    }
    
}