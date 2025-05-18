package Controller;

import View.Admin.QuanLyView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyController implements ActionListener {

    // Tên biến nên viết thường theo chuẩn Java
    private QuanLyView quanLyView;

    // Constructor
    public QuanLyController(QuanLyView quanLyView) {
        this.quanLyView = quanLyView;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();

        switch (button) {
            case "Trang Chủ":
                quanLyView.openTrangChu();
                break;
            case "Quản Lý Giải Đấu":
                quanLyView.openQuanLyGiaiDauPanel();
                break;
            case "Danh Sách Cầu Thủ":
                quanLyView.openDanhSachCauThu();
                break;
            case "Danh Sách Đội Bóng":
                quanLyView.openDanhSachDoiBong();
                break;
            case "Danh Sách HLV":
                quanLyView.openDanhSachHLV();
                break;
            case "Quản Lý Tài Khoản":
                quanLyView.openQuanLyTaiKhoan();
                break;
            case "Danh Sách Trọng Tài":
                quanLyView.openDanhSachTrongTai();
                break;
            default:
                // Có thể log hoặc xử lý nút không nhận diện được
                break;
        }
    }
}
