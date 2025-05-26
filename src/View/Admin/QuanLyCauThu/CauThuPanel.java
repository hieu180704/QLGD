package View.Admin.QuanLyCauThu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import Model.CauThu;
import java.io.IOException;

public class CauThuPanel extends JPanel {
    public CauThuPanel(CauThu ct) throws IOException {
        setPreferredSize(new Dimension(140, 160));
        setLayout(new BorderLayout());

        setBackground(new Color(217, 229, 243)); 

        // Custom JLabel vẽ ảnh tròn
        ImageCircleLabel lblAnh = new ImageCircleLabel();

        byte[] anhBytes = ct.getAnhCauThu();
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(anhBytes));
            if (bufferedImage != null) {
                Image resizedImg = bufferedImage.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                lblAnh.setImage(resizedImg);
            } else {
                lblAnh.setImage(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    
    private Image resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = resizedImage.createGraphics();

    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
    g2d.dispose();

    return resizedImage;
}

}
