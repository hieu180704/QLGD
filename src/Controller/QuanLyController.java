package Controller;

import View.Admin.QuanLyView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyController implements ActionListener {

    private QuanLyView QuanLyView;

    public QuanLyController(QuanLyView QuanLyView) {
        this.QuanLyView = QuanLyView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        if (button.equals("Trang Chủ")) {
            QuanLyView.openTrangChu();

        } else if (button.equals("Quản Lý Giải Đấu")) {
            QuanLyView.openQuanLyGiaiDauPanel();
        } else if (button.equals("Danh Sách Cầu Thủ")) {
            QuanLyView.openDanhSachCauThu();
        } else if (button.equals("Danh Sách Đội Bóng")) {
            QuanLyView.openDanhSachDoiBong();
        } else if (button.equals("Danh Sách HLV")) {
            QuanLyView.openDanhSachHLV();
        } else if (button.equals("Quản Lý Tài Khoản")) {
            QuanLyView.openQuanLyTaiKhoan();
        } else if (button.equals("Danh Sách Trọng Tài")) {
            QuanLyView.openDanhSachTrongTai();
        }
    }

}
