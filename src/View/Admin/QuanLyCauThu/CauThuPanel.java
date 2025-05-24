package View.Admin.QuanLyCauThu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import Model.CauThu;

public class CauThuPanel extends JPanel {
    public CauThuPanel(CauThu ct) {
        setPreferredSize(new Dimension(140, 160));
        setLayout(new BorderLayout());

        setBackground(new Color(217, 229, 243)); // hoặc dùng màu bạn thích, ví dụ (30, 30, 30) xanh đậm

        // Custom JLabel vẽ ảnh tròn
        ImageCircleLabel lblAnh = new ImageCircleLabel();

        byte[] anhBytes = ct.getAnhCauThu();
        if (anhBytes != null && anhBytes.length > 0) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(anhBytes));
                if (bufferedImage != null) {
                    Image resizedImg = bufferedImage.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                    lblAnh.setImage(resizedImg);
                } else {
                    lblAnh.setImage(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                lblAnh.setImage(null);
            }
        } else {
            lblAnh.setImage(null);
        }

        JPanel pnlAnhWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlAnhWrapper.setBorder(new EmptyBorder(5, 0, 0, 0)); // cách mép trên 5px
        pnlAnhWrapper.setOpaque(false); // cho panel ảnh trong suốt để thấy nền ngoài
        pnlAnhWrapper.add(lblAnh);

        add(pnlAnhWrapper, BorderLayout.NORTH);

        JPanel pnlThongTin = new JPanel(new GridLayout(3, 1));
        pnlThongTin.setOpaque(false);  // trong suốt để nền ngoài nổi bật
        pnlThongTin.add(new JLabel(ct.getTenCauThu(), JLabel.CENTER));
        pnlThongTin.add(new JLabel(ct.getTenQuocGia(), JLabel.CENTER));
        pnlThongTin.add(new JLabel(ct.getTenDoi(), JLabel.CENTER));
        add(pnlThongTin, BorderLayout.CENTER);
    }
}
