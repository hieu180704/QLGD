package View.CustomPanel;

import Model.GiaiDau;
import Model.TheThuc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.function.Consumer;

public class ItemGiaiDau extends JPanel {

    private GiaiDau giaiDau;
    private Consumer<GiaiDau> onClick;

    private JLabel lblLogo;
    private JLabel lblTenGiaiDau;
    private JLabel lblTheThuc;
    private JLabel lblNgayTao;
    private JLabel lblNgayBatDau;
    private JLabel lblNgayKetThuc;
    private JLabel lblTrangThai;

    public ItemGiaiDau(GiaiDau giaiDau, Consumer<GiaiDau> onClick) {
        this.giaiDau = giaiDau;
        this.onClick = onClick;

        setPreferredSize(new Dimension(360, 180));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 75, 85), 1, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        // 1. Ảnh logo
        lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(80, 80));
        byte[] imgBytes = giaiDau.getAnhGiaiDau();
        if (imgBytes != null && imgBytes.length > 0) {
            ImageIcon icon = new ImageIcon(imgBytes);
            Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }
        add(lblLogo, BorderLayout.WEST);

        // Panel infoPanel:
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        // Tên giải đấu
        lblTenGiaiDau = new JLabel(giaiDau.getTenGiaiDau());
        lblTenGiaiDau.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));
        lblTenGiaiDau.setForeground(Color.BLACK);

        // Thể thức
        String tenTheThuc = "N/A";
        TheThuc theThuc = giaiDau.getTheThuc();
        if (theThuc != null && theThuc.getTenTheThuc() != null) {
            tenTheThuc = theThuc.getTenTheThuc();
        }
        lblTheThuc = new JLabel(tenTheThuc);
        lblTheThuc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTheThuc.setForeground(new Color(160, 160, 180));

        // Ngày tạo
        String ngayTaoStr = "N/A";
        LocalDate ngayTao = giaiDau.getNgayTaoGiai();
        lblNgayTao = new JLabel("Ngày tạo: " + (ngayTao != null ? ngayTao.toString() : "N/A"));
        lblNgayTao.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNgayTao.setForeground(new Color(140, 140, 160));

        // Ngày bắt đầu
        LocalDate ngayBatDau = giaiDau.getNgayBatDau();
        lblNgayBatDau = new JLabel("Ngày bắt đầu: " + (ngayBatDau != null ? ngayBatDau.toString() : "N/A"));
        lblNgayBatDau.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNgayBatDau.setForeground(new Color(140, 140, 160));

        // Ngày kết thúc
        LocalDate ngayKetThuc = giaiDau.getNgayKetThuc();
        lblNgayKetThuc = new JLabel("Ngày kết thúc: " + (ngayKetThuc != null ? ngayKetThuc.toString() : "N/A"));
        lblNgayKetThuc.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNgayKetThuc.setForeground(new Color(140, 140, 160));

        // Trạng thái
        LocalDate startDate = ngayBatDau;
        LocalDate endDate = ngayKetThuc;

        String trangThai = "N/A";
        LocalDate now = LocalDate.now();

        if (startDate != null) {
            if (now.isBefore(startDate)) {
                trangThai = "Chưa Diễn Ra";
            } else if (endDate != null && now.isAfter(endDate)) {
                trangThai = "Đã Kết Thúc";
            } else {
                trangThai = "Đang Diễn Ra";
            }
        }

        lblTrangThai = new JLabel("Trình trạng: " + trangThai);
        lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTrangThai.setForeground(
                trangThai.equals("Đang Diễn Ra") ? new Color(0, 128, 0)
                : (trangThai.equals("Chưa Diễn Ra") ? Color.ORANGE : Color.RED));

        // Add vào infoPanel
        infoPanel.add(lblTenGiaiDau);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        infoPanel.add(lblTheThuc);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        infoPanel.add(lblNgayTao);
        infoPanel.add(lblNgayBatDau);
        infoPanel.add(lblNgayKetThuc);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        infoPanel.add(lblTrangThai);

        add(infoPanel, BorderLayout.CENTER);

        // Xử lý sự kiện chuột bấm
        if (onClick != null) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onClick.accept(giaiDau);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(220, 220, 255));
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(Color.WHITE);
                    setCursor(Cursor.getDefaultCursor());
                }
            });
        } else {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(235, 235, 255));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(Color.WHITE);
                }
            });
        }
    }

}
