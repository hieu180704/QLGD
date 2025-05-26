package View.Admin.QuanLyCauThu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class QuanLyCauThuView extends JPanel {
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JButton btnThemCauThu;
    private JPanel panelDanhSachCauThu;

    public QuanLyCauThuView() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        txtTimKiem = new JTextField();
        txtTimKiem.setToolTipText("Tìm kiếm theo tên, quốc tịch hoặc đội bóng");
        txtTimKiem.setPreferredSize(new Dimension(300, 35));  
        txtTimKiem.setBorder(new RoundedBorder(10));

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 35));
        btnTimKiem.setBorder(new RoundedBorder(10));
        
        

        btnThemCauThu = new JButton("Thêm cầu thủ");
        btnThemCauThu.setPreferredSize(new Dimension(120, 35));
        btnThemCauThu.setBorder(new RoundedBorder(10));

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelButtons.add(btnTimKiem);
        panelButtons.add(btnThemCauThu);

        panelTop.add(txtTimKiem, BorderLayout.WEST);
        panelTop.add(panelButtons, BorderLayout.EAST);

        add(panelTop, BorderLayout.NORTH);

        panelDanhSachCauThu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelDanhSachCauThu);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getTimKiemText() {
        return txtTimKiem.getText();
    }

    public void addBtnTimKiemListener(ActionListener listener) {
        btnTimKiem.addActionListener(listener);
    }

    public void addBtnThemCauThuListener(ActionListener listener) {
        btnThemCauThu.addActionListener(listener);
    }

    public JPanel getPanelDanhSachCauThu() {
        return panelDanhSachCauThu;
    }

    private static class RoundedBorder extends LineBorder {
        private int radius;

        public RoundedBorder(int radius) {
            super(Color.GRAY, 1, true);
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(lineColor);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }
}
