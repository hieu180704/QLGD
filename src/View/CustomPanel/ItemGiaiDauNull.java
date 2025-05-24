package View.CustomPanel;

import Model.GiaiDau;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemGiaiDauNull extends JPanel {

    private GiaiDau giaiDau;
    private JLabel lblAnh;
    private JLabel lblTen;

    public ItemGiaiDauNull(GiaiDau gd) {
        this.giaiDau = gd;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(5,5));
        setPreferredSize(new Dimension(200, 80));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setBackground(Color.WHITE);

        // Ảnh giải đấu
        lblAnh = new JLabel();
        lblAnh.setPreferredSize(new Dimension(80, 80));
        if (giaiDau.getAnhGiaiDau() != null && giaiDau.getAnhGiaiDau().length > 0) {
            ImageIcon icon = new ImageIcon(giaiDau.getAnhGiaiDau());
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lblAnh.setIcon(new ImageIcon(img));
        } else {
            lblAnh.setText("No Image");
            lblAnh.setHorizontalAlignment(SwingConstants.CENTER);
            lblAnh.setVerticalAlignment(SwingConstants.CENTER);
        }
        add(lblAnh, BorderLayout.WEST);

        // Tên giải đấu
        lblTen = new JLabel(giaiDau.getTenGiaiDau());
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTen.setVerticalAlignment(SwingConstants.CENTER);
        add(lblTen, BorderLayout.CENTER);

        // Mouse click event
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(ItemGiaiDauNull.this,
                        "Xếp lịch thi đấu cho giải này?",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(220, 240, 255));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public GiaiDau getGiaiDau() {
        return giaiDau;
    }
}
