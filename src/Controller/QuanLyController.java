package Controller;

import View.Admin.QuanLyView;
import ViewMain.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

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
            case "Quản Lý Trận Đấu":
                quanLyView.openTranDauPanel();
                break;
            case "Xếp Lịch Thi Đấu":
                quanLyView.openXepLichThiDau();
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
            case "Quản Lý Nhà Tài Trợ":
                quanLyView.openNhaTaiTroPanel();
                break;
            case "Quản Lý Sân Đấu":
                quanLyView.openQuanLySanDau();
                break;
            case "Đăng Xuất":
                int choice = JOptionPane.showConfirmDialog(quanLyView,
                        "Bạn có chắc chắn muốn đăng xuất?",
                        "Xác nhận đăng xuất",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    quanLyView.dispose();  // Đóng cửa sổ hiện tại
                    // Nếu bạn có màn hình đăng nhập, gọi nó ở đây, ví dụ:
                    new LoginView().setVisible(true);

                }
                break;
            default:
                // Có thể log hoặc xử lý nút không nhận diện được
                break;
        }
    }
}
