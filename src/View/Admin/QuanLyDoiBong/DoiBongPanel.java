package View.Admin.QuanLyDoiBong;

import View.Admin.QuanLyCauThu.ImageCircleLabel;
import javax.swing.*;
import java.awt.*;

public class DoiBongPanel extends JPanel {
    private ImageCircleLabel lblImage;
    private JLabel lblTenDoi;
    private JLabel lblTenGiaiDau;

    public DoiBongPanel(Image logoDoi, String tenDoi, String tenGiaiDau) {
        setPreferredSize(new Dimension(150, 200));
        setBackground(new Color(173, 216, 230)); // nền xanh nhạt
        setLayout(new BorderLayout());

        lblImage = new ImageCircleLabel();
        lblImage.setPreferredSize(new Dimension(90, 90));
        lblImage.setImage(logoDoi);
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblImage, BorderLayout.CENTER);

        JPanel panelText = new JPanel();
        panelText.setBackground(new Color(173, 216, 230));
        panelText.setLayout(new BoxLayout(panelText, BoxLayout.Y_AXIS));
        panelText.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        lblTenDoi = new JLabel(tenDoi);
        lblTenDoi.setFont(new Font("Arial", Font.BOLD, 14));
        lblTenDoi.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTenGiaiDau = new JLabel(tenGiaiDau);
        lblTenGiaiDau.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTenGiaiDau.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelText.add(lblTenDoi);
        panelText.add(lblTenGiaiDau);

        add(panelText, BorderLayout.SOUTH);
    }
}
