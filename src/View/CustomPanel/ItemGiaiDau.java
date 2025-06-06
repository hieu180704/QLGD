package View.CustomPanel;

import Model.GiaiDau;
import Model.TheThuc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
        Date ngayTao = giaiDau.getNgayTaoGiai();
        if (ngayTao != null) {
            ngayTaoStr = new java.text.SimpleDateFormat("dd/MM/yyyy").format(ngayTao);
        }
        lblNgayTao = new JLabel("Ngày tạo: " + ngayTaoStr);
        lblNgayTao.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNgayTao.setForeground(new Color(140, 140, 160));

        // Ngày bắt đầu
        Date ngayBatDau = giaiDau.getNgayBatDau();
        String ngayBatDauStr = "N/A";
        if (ngayBatDau != null) {
            ngayBatDauStr = new java.text.SimpleDateFormat("dd/MM/yyyy").format(ngayBatDau);
        }
        lblNgayBatDau = new JLabel("Ngày bắt đầu: " + ngayBatDauStr);
        lblNgayBatDau.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNgayBatDau.setForeground(new Color(140, 140, 160));

        // Ngày kết thúc
        Date ngayKetThuc = giaiDau.getNgayKetThuc();
        String ngayKetThucStr = "N/A";
        if (ngayKetThuc != null) {
            ngayKetThucStr = new java.text.SimpleDateFormat("dd/MM/yyyy").format(ngayKetThuc);
        }
        lblNgayKetThuc = new JLabel("Ngày kết thúc: " + ngayKetThucStr);
        lblNgayKetThuc.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNgayKetThuc.setForeground(new Color(140, 140, 160));

        // Trạng thái
        String trangThai = "N/A";
        LocalDate now = LocalDate.now();
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (ngayBatDau instanceof java.sql.Date) {
            startDate = ((java.sql.Date) ngayBatDau).toLocalDate();
        } else if (ngayBatDau != null) {
            startDate = ngayBatDau.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        if (ngayKetThuc instanceof java.sql.Date) {
            endDate = ((java.sql.Date) ngayKetThuc).toLocalDate();
        } else if (ngayKetThuc != null) {
            endDate = ngayKetThuc.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

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
