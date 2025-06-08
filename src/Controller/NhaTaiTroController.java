package Controller;

import Model.NhaTaiTro;
import Service.NhaTaiTroService;
import View.Admin.NhaTaiTroPanel.NhaTaiTroPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class NhaTaiTroController {
    private NhaTaiTroPanel view;
    private NhaTaiTroService service;

    public NhaTaiTroController(NhaTaiTroPanel view) {
        this.view = view;
        this.service = new NhaTaiTroService();

        // Thêm sự kiện
        addEventListeners();
    }

    private void addEventListeners() {
        // Sự kiện chọn dòng trên bảng
        view.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    handleTableSelection();
                }
            }
        });

        // Sự kiện chọn ảnh
        view.getBtnChonAnh().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChooseImage();
            }
        });

        // Sự kiện thêm
        view.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAdd();
            }
        });

        // Sự kiện sửa
        view.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdate();
            }
        });

        // Sự kiện xóa
        view.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDelete();
            }
        });

        // Sự kiện tìm kiếm
        view.getBtnTim().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });
    }

    private void handleTableSelection() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            view.getTxtTen().setText(view.getModel().getValueAt(selectedRow, 1).toString());
            view.getTxtEmail().setText(view.getModel().getValueAt(selectedRow, 2).toString());
            view.getTxtSDT().setText(view.getModel().getValueAt(selectedRow, 3).toString());
            view.getTxtDiaChi().setText(view.getModel().getValueAt(selectedRow, 4).toString());
        }
    }

    private void handleChooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showOpenDialog(view);
        if (res == JFileChooser.APPROVE_OPTION) {
            try {
                view.setLogoBytes(java.nio.file.Files.readAllBytes(fileChooser.getSelectedFile().toPath()));
                view.showMessage("Đã chọn ảnh logo.");
            } catch (Exception ex) {
                view.showMessage("Lỗi đọc file ảnh.");
            }
        }
    }

    private void handleAdd() {
        NhaTaiTro ntt = new NhaTaiTro(
                view.getTxtTen().getText(),
                view.getLogoBytes(),
                view.getTxtEmail().getText(),
                view.getTxtSDT().getText(),
                view.getTxtDiaChi().getText()
        );
        String result = service.addNhaTaiTro(ntt);
        view.showMessage(result);
        if (result.contains("thành công")) {
            view.loadData();
            view.clearInput();
        }
    }

    private void handleUpdate() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow < 0) {
            view.showMessage("Chọn nhà tài trợ cần sửa!");
            return;
        }
        int maNTT = (int) view.getModel().getValueAt(selectedRow, 0);
        NhaTaiTro ntt = new NhaTaiTro(
                maNTT,
                view.getTxtTen().getText(),
                view.getLogoBytes(),
                view.getTxtEmail().getText(),
                view.getTxtSDT().getText(),
                view.getTxtDiaChi().getText()
        );
        String result = service.updateNhaTaiTro(ntt);
        view.showMessage(result);
        if (result.contains("thành công")) {
            view.loadData();
            view.clearInput();
        }
    }

    private void handleDelete() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow < 0) {
            view.showMessage("Chọn nhà tài trợ cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa nhà tài trợ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int maNTT = (int) view.getModel().getValueAt(selectedRow, 0);
            String result = service.deleteNhaTaiTro(maNTT);
            view.showMessage(result);
            if (result.contains("thành công")) {
                view.loadData();
                view.clearInput();
            }
        }
    }

    private void handleSearch() {
        String keyword = view.getTxtTimKiem().getText().trim();
        List<NhaTaiTro> list = service.searchNhaTaiTro(keyword);
        view.displayData(list);
    }
}