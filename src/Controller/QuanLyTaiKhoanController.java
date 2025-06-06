package Controller;

import DAO.TaiKhoanDAO;
import Model.TaiKhoan;
import View.Admin.QuanLyTaiKhoan.QuanLyTaiKhoanPanel;
//import View.Admin.QuanLyTaiKhoanPanel;
import View.Admin.QuanLyTaiKhoan.FormThemSuaTaiKhoan;

import javax.swing.*;
import java.util.List;

public class QuanLyTaiKhoanController {

    private QuanLyTaiKhoanPanel view;
    private TaiKhoanDAO dao;

    public QuanLyTaiKhoanController(QuanLyTaiKhoanPanel view) {
        this.view = view;
        this.dao = new TaiKhoanDAO();
        initController();
        loadTableData();
    }

    private void initController() {
        view.getBtnThem().addActionListener(e -> openUserEditDialog(null));
        view.getBtnSua().addActionListener(e -> {
            Integer id = view.getSelectedTaiKhoanId();
            if (id == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn tài khoản để sửa.");
                return;
            }
            TaiKhoan tk = dao.findById(id);
            openUserEditDialog(tk);
        });
        view.getBtnXoa().addActionListener(e -> {
            Integer id = view.getSelectedTaiKhoanId();
            if (id == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn tài khoản để xóa.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view,
                "Bạn có chắc muốn xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.delete(id)) {
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại!");
                }
            }
        });
        view.getBtnTimKiem().addActionListener(e -> {
            String keyword = view.getTxtTimKiem().getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                loadTableData();
            } else {
                List<TaiKhoan> all = dao.findAll();
                all.removeIf(tk -> !tk.getTendangnhap().toLowerCase().contains(keyword) &&
                        !tk.getEmail().toLowerCase().contains(keyword));
                view.loadData(all);
            }
        });
    }

    public void loadTableData() {
        List<TaiKhoan> list = dao.findAll();
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

        panel.getBtnSave().addActionListener(e -> {
            String username = panel.getUsername();
            String email = panel.getEmail();
            String password = panel.getPassword();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            if (tk == null) {
                if (dao.kiemTraTenDangNhap(username)) {
                    JOptionPane.showMessageDialog(dialog, "Tên đăng nhập đã tồn tại!");
                    return;
                }
                TaiKhoan newTk = new TaiKhoan();
                newTk.setTendangnhap(username);
                newTk.setEmail(email);
                newTk.setMatkhau(password);
                newTk.setLoaitaikhoan(1);
                if (dao.insert(newTk)) {
                    JOptionPane.showMessageDialog(dialog, "Thêm tài khoản thành công!");
                    loadTableData();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm thất bại!");
                }
            } else {
                tk.setEmail(email);
                tk.setMatkhau(password);
                if (dao.update(tk)) {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật thành công!");
                    loadTableData();
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại!");
                }
            }
        });

        panel.getBtnCancel().addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}
