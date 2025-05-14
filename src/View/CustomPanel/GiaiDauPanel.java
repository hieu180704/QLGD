package View.CustomPanel;

import Model.GiaiDauModel;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GiaiDauPanel extends JPanel {

    public GiaiDauPanel(List<GiaiDauModel> dataList) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        setBackground(new Color(20, 20, 30));
        this.setPreferredSize(new Dimension(1400,600));

        for (GiaiDauModel giaiDau : dataList) {
            add(createCard(giaiDau));
        }
    }

    private JPanel createCard(GiaiDauModel giaiDau) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(320, 140));  // tăng chiều cao thêm chút cho thể thức
        card.setBackground(new Color(35, 39, 50));
        card.setLayout(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo bên trái
        ImageIcon icon = new ImageIcon(giaiDau.getLogoGiai());
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(img));
        card.add(logoLabel, BorderLayout.WEST);

        // Phần chính bên phải
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BorderLayout(5, 5));

        // Tên giải (dòng trên)
        JLabel nameLabel = new JLabel(giaiDau.getTenGiai());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);

        // Thể thức (dòng dưới tên giải, font nhỏ, màu sáng hơn)
        JLabel formatLabel = new JLabel(giaiDau.getTheThuc());
        formatLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        formatLabel.setForeground(Color.LIGHT_GRAY);

        // Panel chứa tên và thể thức
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(nameLabel);
        titlePanel.add(formatLabel);

        rightPanel.add(titlePanel, BorderLayout.NORTH);

        // Icon khu vực + label vùng (giữ nguyên)
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        topRightPanel.setOpaque(false);
        JLabel regionIcon = new JLabel(new ImageIcon(getClass().getResource("/Resources/fb.png")));
        topRightPanel.add(regionIcon);
        JLabel regionLabel = new JLabel("Europe");
        regionLabel.setForeground(Color.LIGHT_GRAY);
        regionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        topRightPanel.add(regionLabel);
        rightPanel.add(topRightPanel, BorderLayout.CENTER);

        // Ngày bắt đầu - kết thúc
        JLabel dateLabel = new JLabel("Mùa giải "+giaiDau.getMuaGiai());
        dateLabel.setForeground(Color.GRAY);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        datePanel.setOpaque(false);
        datePanel.add(dateLabel);
        rightPanel.add(datePanel, BorderLayout.PAGE_END);

        card.add(rightPanel, BorderLayout.CENTER);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 90)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(50, 55, 70));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(35, 39, 50));
            }
        });

        return card;
    }
}
