package View.Admin.QuanLyHLV;

import View.CustomLayout.WrapLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class DanhSachHLVPanel extends JPanel {
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JButton btnThemHLV;
    private JButton btnLamMoi;
    private JPanel panelDanhSachHLV;

    public DanhSachHLVPanel() {
        setLayout(new BorderLayout()); 

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelTop.setBorder(new EmptyBorder(5, 10, 5, 10)); // Thêm padding cho panelTop

        txtTimKiem = new JTextField();
        txtTimKiem.setToolTipText("Tìm kiếm theo tên HLV hoặc quốc gia");
        txtTimKiem.setPreferredSize(new Dimension(300, 35));
        txtTimKiem.setMaximumSize(new Dimension(300, 35)); // Giới hạn kích thước tối đa
        txtTimKiem.setBorder(new RoundedBorder(10));

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 35));
        btnTimKiem.setBorder(new RoundedBorder(10));

        btnThemHLV = new JButton("Thêm HLV");
        btnThemHLV.setPreferredSize(new Dimension(120, 35));
        btnThemHLV.setBorder(new RoundedBorder(10));

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(100, 35));
        btnLamMoi.setBorder(new RoundedBorder(10));

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelButtons.add(btnTimKiem);
        panelButtons.add(btnThemHLV);
        panelButtons.add(btnLamMoi);

        panelTop.add(txtTimKiem);
        panelTop.add(panelButtons);

        add(panelTop, BorderLayout.NORTH);

        panelDanhSachHLV = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 10));
        JScrollPane scrollPane = new JScrollPane(panelDanhSachHLV);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getTimKiemText() {
        return txtTimKiem.getText();
    }

    public void addBtnTimKiemListener(ActionListener listener) {
        btnTimKiem.addActionListener(listener);
    }

    public void addBtnThemHLVListener(ActionListener listener) {
        btnThemHLV.addActionListener(listener);
    }

    public void addBtnLamMoiListener(ActionListener listener) {
        btnLamMoi.addActionListener(listener);
    }

    public JPanel getPanelDanhSachHLV() {
        return panelDanhSachHLV;
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