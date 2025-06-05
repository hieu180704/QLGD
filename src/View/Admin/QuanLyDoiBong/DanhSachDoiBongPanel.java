package View.Admin.QuanLyDoiBong;

import DAO.DoiBongDAO;
import Model.DoiBong;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import View.CustomLayout.WrapLayout;
import javax.swing.border.LineBorder;

public class DanhSachDoiBongPanel extends JPanel {
    private JTextField txtTimKiem;
    private JButton btnTimKiem;
    private JButton btnThemDoiBong;
    private JButton btnLamMoi;
    private JPanel panelDanhSachDoiBong;

    public DanhSachDoiBongPanel() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        txtTimKiem = new JTextField();
        txtTimKiem.setToolTipText("Tìm kiếm theo tên đội bóng hoặc quốc gia");
        txtTimKiem.setPreferredSize(new Dimension(300, 35));
        txtTimKiem.setBorder(new RoundedBorder(10));

        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 35));
        btnTimKiem.setBorder(new RoundedBorder(10));

        btnThemDoiBong = new JButton("Thêm đội bóng");
        btnThemDoiBong.setPreferredSize(new Dimension(120, 35));
        btnThemDoiBong.setBorder(new RoundedBorder(10));

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(100, 35));
        btnLamMoi.setBorder(new RoundedBorder(10));

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelButtons.add(btnTimKiem);
        panelButtons.add(btnThemDoiBong);
        panelButtons.add(btnLamMoi);

        panelTop.add(txtTimKiem);
        panelTop.add(panelButtons);

        add(panelTop, BorderLayout.NORTH);

        panelDanhSachDoiBong = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 10));
        JScrollPane scrollPane = new JScrollPane(panelDanhSachDoiBong);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        loadDanhSachDoiBong();
    }

    public void loadDanhSachDoiBong() {
        DoiBongDAO doiBongDAO = new DoiBongDAO();
        List<DoiBong> danhSach = doiBongDAO.findAll();

        panelDanhSachDoiBong.removeAll();

        for (DoiBong db : danhSach) {
            
            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(140, 160));
            card.setLayout(new BorderLayout());
            card.setBackground(new Color(179, 218, 255));

            byte[] logoBytes = db.getLogoDoi();
            ImageIcon icon = null;
            if (logoBytes != null) {
                Image img = Toolkit.getDefaultToolkit().createImage(logoBytes);
                Image scaled = img.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaled);
            } else {
                icon = new ImageIcon(); // ảnh mặc định hoặc để trống
            }
            JLabel lblAnh = new JLabel(icon, JLabel.CENTER);
            card.add(lblAnh, BorderLayout.NORTH);

            JLabel lblTenDoi = new JLabel(db.getTenDoi(), JLabel.CENTER);
            card.add(lblTenDoi, BorderLayout.CENTER);

            panelDanhSachDoiBong.add(card);
        }

        panelDanhSachDoiBong.revalidate();
        panelDanhSachDoiBong.repaint();
    }

    public String getTimKiemText() {
        return txtTimKiem.getText();
    }

    public void addBtnTimKiemListener(ActionListener listener) {
        btnTimKiem.addActionListener(listener);
    }

    public void addBtnThemDoiBongListener(ActionListener listener) {
        btnThemDoiBong.addActionListener(listener);
    }

    public void addBtnLamMoiListener(ActionListener listener) {
        btnLamMoi.addActionListener(listener);
    }

    public JPanel getPanelDanhSachDoiBong() {
        return panelDanhSachDoiBong;
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
