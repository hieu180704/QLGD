package View.CustomPanel;

import Model.TranDau;
import Model.DoiBong;
import Model.DoiBong_TranDau;
import Model.GiaiDau;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ItemTranDau extends JPanel {

    public ItemTranDau(TranDau tranDau) {
        setLayout(new GridBagLayout());
        setBackground(new Color(34, 34, 34));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        GiaiDau giaiDau = tranDau.getGiaiDau();

        // Lấy danh sách DoiBong_TranDau
        List<DoiBong_TranDau> doiBongTranDauList = tranDau.getDoiBongTranDauList();

        DoiBong_TranDau dbtdChuNha = null;
        DoiBong_TranDau dbtdKhach = null;

        if (doiBongTranDauList != null) {
            for (DoiBong_TranDau dbtd : doiBongTranDauList) {
                if (dbtd.getLaChuNha() == 1) {
                    dbtdChuNha = dbtd;
                } else if (dbtd.getLaChuNha() == 0) {
                    dbtdKhach = dbtd;
                }
            }
        }

        String team1Name = dbtdChuNha != null ? dbtdChuNha.getDoiBong().getTenDoi() : "Đội 1";
        Icon team1Icon = dbtdChuNha != null && dbtdChuNha.getDoiBong().getLogoDoi() != null
                ? createIconFromBytes(dbtdChuNha.getDoiBong().getLogoDoi()) : null;

        String team2Name = dbtdKhach != null ? dbtdKhach.getDoiBong().getTenDoi() : "Đội 2";
        Icon team2Icon = dbtdKhach != null && dbtdKhach.getDoiBong().getLogoDoi() != null
                ? createIconFromBytes(dbtdKhach.getDoiBong().getLogoDoi()) : null;

        // Lấy tỉ số
        String score = "-- : --";
        if (dbtdChuNha != null && dbtdKhach != null) {
            int soBanChuNha = dbtdChuNha.getSoBanThang();
            int soBanKhach = dbtdKhach.getSoBanThang();
            score = soBanChuNha + " : " + soBanKhach;
        }

        String tenGiaiDau = (giaiDau != null) ? giaiDau.getTenGiaiDau() : "Chưa có giải đấu";

        // Tiêu đề giải đấu
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel lblTournament = new JLabel(tenGiaiDau, SwingConstants.CENTER);
        lblTournament.setFont(lblTournament.getFont().deriveFont(Font.BOLD, 14f));
        lblTournament.setForeground(Color.WHITE);
        add(lblTournament, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        // Team 1 panel
        gbc.gridx = 0;
        add(createTeamPanel(team1Name, team1Icon), gbc);

        // Trung tâm: ngày giờ trận đấu + tỉ số
        gbc.gridx = 1;
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");

        String dateStr = (tranDau != null && tranDau.getThoiGian() != null) ? tranDau.getThoiGian().format(df) : "--/--/----";
        String timeStr = (tranDau != null && tranDau.getThoiGian() != null) ? tranDau.getThoiGian().format(tf) : "--:--";

        JLabel lblDate = new JLabel(dateStr);
        lblDate.setForeground(Color.WHITE);
        lblDate.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTime = new JLabel(timeStr);
        lblTime.setForeground(Color.LIGHT_GRAY);
        lblTime.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblScore = new JLabel(score);
        lblScore.setFont(lblScore.getFont().deriveFont(Font.BOLD, 18f));
        lblScore.setForeground(Color.WHITE);
        lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(lblDate);
        center.add(lblTime);
        center.add(lblScore);
        add(center, gbc);

        // Team 2 panel
        gbc.gridx = 2;
        add(createTeamPanel(team2Name, team2Icon), gbc);

        // Hover effect
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(new Color(54, 54, 54));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(new Color(34, 34, 34));
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    private JPanel createTeamPanel(String name, Icon icon) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JLabel lblIcon = new JLabel(icon);
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblName = new JLabel(name);
        lblName.setForeground(Color.WHITE);
        lblName.setFont(lblName.getFont().deriveFont(Font.PLAIN, 12f));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(lblIcon);
        p.add(lblName);
        return p;
    }

    private Icon createIconFromBytes(byte[] imageData) {
        if (imageData == null || imageData.length == 0) {
            return null;
        }
        ImageIcon icon = new ImageIcon(imageData);
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
