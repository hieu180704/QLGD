package View.CustomPanel;

import Model.GiaiDau;
import Model.NhaTaiTro;
import Model.TheThuc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;

public class ItemGiaiDau extends JPanel {

    private GiaiDau giaiDau;
    private Consumer<GiaiDau> onClick;

    private JLabel lblLogo;
    private JLabel lblTenGiaiDau;
    private JLabel lblNhaTaiTro;
    private JLabel lblTheThuc;
    private JLabel lblNgayTao;
    private JLabel lblNgayBatDau;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ItemGiaiDau(GiaiDau giaiDau) {
        setPreferredSize(new Dimension(320, 140));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 75, 85), 1, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        // Logo giải đấu
        lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(80, 80));
        lblLogo.setOpaque(false);

        byte[] imgBytes = giaiDau.getAnhGiaiDau();
        if (imgBytes != null && imgBytes.length > 0) {
            ImageIcon icon = new ImageIcon(imgBytes);
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }

        add(lblLogo, BorderLayout.WEST);

        // Panel thông tin
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        lblTenGiaiDau = new JLabel(giaiDau.getTenGiaiDau());
        lblTenGiaiDau.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
        lblTenGiaiDau.setForeground(Color.BLACK);

        String tenNTT = (giaiDau.getNhaTaiTro().getTenNTT() != null) ? giaiDau.getNhaTaiTro().getTenNTT() : "N/A";
        lblNhaTaiTro = new JLabel("Nhà tài trợ: " + tenNTT);
        lblNhaTaiTro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblNhaTaiTro.setForeground(new Color(160, 160, 180));

        String tenTheThuc = (giaiDau.getTheThuc().getTenTheThuc() != null) ? giaiDau.getTheThuc().getTenTheThuc() : "N/A";
        lblTheThuc = new JLabel("Thể thức: " + tenTheThuc);
        lblTheThuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTheThuc.setForeground(new Color(160, 160, 180));

        String ngayTao = (giaiDau.getNgayTaoGiai() != null) ? sdf.format(giaiDau.getNgayTaoGiai()) : "N/A";
        String ngayBatDau = (giaiDau.getNgayBatDau() != null) ? sdf.format(giaiDau.getNgayBatDau()) : "N/A";

        lblNgayTao = new JLabel("Tạo: " + ngayTao);
        lblNgayTao.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNgayTao.setForeground(new Color(140, 140, 160));

        lblNgayBatDau = new JLabel("Bắt đầu: " + ngayBatDau);
        lblNgayBatDau.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNgayBatDau.setForeground(new Color(140, 140, 160));

        infoPanel.add(lblTenGiaiDau);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        infoPanel.add(lblNhaTaiTro);
        infoPanel.add(lblTheThuc);
        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(lblNgayTao);
        infoPanel.add(lblNgayBatDau);

        add(infoPanel, BorderLayout.CENTER);

        // Hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(235, 235, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(Color.WHITE);
            }
        });
    }

    public ItemGiaiDau(GiaiDau giaiDau, Consumer<GiaiDau> onClick) {
        this.giaiDau = giaiDau;
        this.onClick = onClick;

        setPreferredSize(new Dimension(320, 180));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        JLabel lblTen = new JLabel(giaiDau.getTenGiaiDau());
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(lblTen, BorderLayout.CENTER);

        // Thêm MouseListener để bắt sự kiện click toàn panel
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClick != null) {
                    onClick.accept(giaiDau);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(220, 220, 255));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}
