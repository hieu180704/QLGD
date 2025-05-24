package View.Admin.QuanLyCauThu;

import javax.swing.*;
import Model.CauThu;
import java.awt.GridLayout;

public class ThemCauThuDialog extends JDialog {
    private JTextField txtTen;
    private JTextField txtQuocGia; // đổi tên cho đúng model
    private JTextField txtDoi;
    private JButton btnOk, btnCancel;
    private CauThu cauThu;

    public ThemCauThuDialog(JFrame parent) {
        super(parent, "Thêm cầu thủ", true);
        setSize(300, 220);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 5, 5));

        add(new JLabel("Tên cầu thủ:"));
        txtTen = new JTextField();
        add(txtTen);

        add(new JLabel("Mã quốc gia (số):"));  // đổi cho phù hợp
        txtQuocGia = new JTextField();
        add(txtQuocGia);

        add(new JLabel("Mã đội bóng (số):"));  // đổi cho phù hợp
        txtDoi = new JTextField();
        add(txtDoi);

        btnOk = new JButton("Thêm");
        btnCancel = new JButton("Hủy");
        add(btnOk);
        add(btnCancel);

        btnOk.addActionListener(e -> {
            String ten = txtTen.getText().trim();
            String maQuocGiaStr = txtQuocGia.getText().trim();
            String maDoiStr = txtDoi.getText().trim();

            if (ten.isEmpty() || maQuocGiaStr.isEmpty() || maDoiStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
                return;
            }

            try {
                int maQuocGia = Integer.parseInt(maQuocGiaStr);
                int maDoi = Integer.parseInt(maDoiStr);

                // Khởi tạo CauThu với các trường cần thiết, các trường khác đặt mặc định
                cauThu = new CauThu();
                cauThu.setTenCauThu(ten);
                cauThu.setMaQuocGia(maQuocGia);
                cauThu.setMaDoi(maDoi);

                // Nếu có thể cho nhập thêm các trường khác như ngày sinh, chiều cao, cân nặng thì thêm vào dialog

                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mã quốc gia và mã đội bóng phải là số nguyên");
            }
        });

        btnCancel.addActionListener(e -> {
            cauThu = null;
            dispose();
        });
    }

    public CauThu getCauThu() {
        return cauThu;
    }
}
