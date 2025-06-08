package Controller;

import Model.TaiKhoan;
import Service.TaiKhoanService;
import View.Admin.QuanLyTaiKhoan.QuanLyTaiKhoanPanel;
import View.Admin.QuanLyTaiKhoan.FormThemSuaTaiKhoan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class QuanLyTaiKhoanController {
    private QuanLyTaiKhoanPanel view;
    private TaiKhoanService service;

    public QuanLyTaiKhoanController(QuanLyTaiKhoanPanel view) {
        this.view = view;
        this.service = new TaiKhoanService();
        initController();
        loadTableData();
    }

    private void initController() {
        // Sự kiện nút Thêm
        view.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserEditDialog(null);
            }
        });

        // Sự kiện nút Sửa
        view.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id = view.getSelectedTaiKhoanId();
                if (id == null) {
                    view.showMessage("Vui lòng chọn tài khoản để sửa.");
                    return;
                }
                TaiKhoan tk = service.findById(id);
                openUserEditDialog(tk);
            }
        });

        // Sự kiện nút Xóa
        view.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id = view.getSelectedTaiKhoanId();
                if (id == null) {
                    view.showMessage("Vui lòng chọn tài khoản để xóa.");
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(view,
                        "Bạn có chắc muốn xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String result = service.deleteTaiKhoan(id);
                    view.showMessage(result);
                    if (result.contains("thành công")) {
                        loadTableData();
                    }
                }
            }
        });

        // Sự kiện nút Tìm kiếm
        view.getBtnTimKiem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });
    }

    public void loadTableData() {
        List<TaiKhoan> list = service.getAllTaiKhoan();
        view.loadData(list);
    }

    private void handleSearch() {
        String keyword = view.getTxtTimKiem().getText().trim();
        List<TaiKhoan> list = service.searchTaiKhoan(keyword);
        view.loadData(list);
    }

    private void openUserEditDialog(TaiKhoan tk) {
        FormThemSuaTaiKhoan panel = new FormThemSuaTaiKhoan();
        if (tk != null) {
            panel.setUserData(tk.getTendangnhap(), tk.getEmail(), tk.getMatkhau());
            panel.setUsernameEditable(false);
        }

        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(view),
                tk == null ? "Thêm tài khoản" : "Sửa tài khoản", true);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(view);

        // Sự kiện nút Lưu
        panel.getBtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = panel.getUsername();
                String email = panel.getEmail();
                String password = panel.getPassword();

                if (tk == null) {
                    // Thêm tài khoản mới
                    TaiKhoan newTk = new TaiKhoan();
                    newTk.setTendangnhap(username);
                    newTk.setEmail(email);
                    newTk.setMatkhau(password);
                    newTk.setLoaitaikhoan(1); // Admin
                    String result = service.addTaiKhoan(newTk);
                    panel.showMessage(result);
                    if (result.contains("thành công")) {
                        loadTableData();
                        dialog.dispose();
                    }
                } else {
                    // Sửa tài khoản
                    tk.setEmail(email);
                    tk.setMatkhau(password);
                    String result = service.updateTaiKhoan(tk);
                    panel.showMessage(result);
                    if (result.contains("thành công")) {
                        loadTableData();
                        dialog.dispose();
                    }
                }
            }
        });

        // Sự kiện nút Hủy
        panel.getBtnCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }
}