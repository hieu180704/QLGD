package View.Admin.QuanLyCauThu;

import javax.swing.*;
import java.awt.*;
import Model.CauThu;

public class SuaXoaCauThuDialog extends JDialog {
    private JTextField txtTen;
    private JTextField txtQuocTich;
    private JTextField txtDoi;
    private JButton btnLuu, btnXoa, btnHuy;

    private CauThu cauThu;

    private boolean isUpdated = false;
    private boolean isDeleted = false;

    public SuaXoaCauThuDialog(JFrame parent, CauThu ct) {
        super(parent, "Sửa/Xóa cầu thủ", true);
        this.cauThu = ct;

        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 5, 5));

        add(new JLabel("Tên cầu thủ:"));
        txtTen = new JTextField(ct.getTenCauThu());
        add(txtTen);

        add(new JLabel("Quốc tịch:"));
        txtQuocTich = new JTextField(ct.getTenQuocGia());
        add(txtQuocTich);

        add(new JLabel("Đội bóng:"));
        txtDoi = new JTextField(ct.getTenDoi());
        add(txtDoi);

        btnLuu = new JButton("Lưu");
        btnXoa = new JButton("Xóa");
        btnHuy = new JButton("Hủy");

        add(btnLuu);
        add(btnXoa);
        add(btnHuy);

        // Xử lý sự kiện nút Lưu
        btnLuu.addActionListener(e -> {
            String ten = txtTen.getText().trim();
            String quocTich = txtQuocTich.getText().trim();
            String doi = txtDoi.getText().trim();

            if (ten.isEmpty() || quocTich.isEmpty() || doi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
                return;
            }

            // Cập nhật thông tin cầu thủ
            cauThu.setTenCauThu(ten);
            cauThu.setTenQuocGia(quocTich);
            cauThu.setTenDoi(doi);

            isUpdated = true;
            dispose();
        });

        // Xử lý nút Xóa
        btnXoa.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa cầu thủ này?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                isDeleted = true;
                dispose();
            }
        });

        // Nút Hủy
        btnHuy.addActionListener(e -> dispose());
    }

    public CauThu getCauThu() {
        return cauThu;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
