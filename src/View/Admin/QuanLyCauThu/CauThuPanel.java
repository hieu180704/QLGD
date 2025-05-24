package View.Admin.QuanLyCauThu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import View.Admin.QuanLyCauThu.ImageCircleLabel;
import Model.CauThu;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;

public class CauThuPanel extends JPanel {
    public CauThuPanel(CauThu ct) {
        setPreferredSize(new Dimension(140, 160));
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.blue));

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
                    lblAnh.setImage(null); // Ảnh không hợp lệ
                }
            } catch (Exception e) {
                e.printStackTrace();
                lblAnh.setImage(null);
            }
        } else {
            lblAnh.setImage(null);
        }

        add(lblAnh, BorderLayout.NORTH);

        JPanel pnlThongTin = new JPanel(new GridLayout(3, 1));
        pnlThongTin.add(new JLabel(ct.getTenCauThu(), JLabel.CENTER));
        pnlThongTin.add(new JLabel(ct.getTenQuocGia(), JLabel.CENTER));
        pnlThongTin.add(new JLabel(ct.getTenDoi(), JLabel.CENTER));
        add(pnlThongTin, BorderLayout.CENTER);
    }
}
