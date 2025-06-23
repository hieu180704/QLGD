package View.CustomPanel;

import DAO.DoiBongDAO;
import DAO.DoiBong_TranDauDAO;
import DAO.TranDauDAO;
import Model.DoiBong;
import Model.DoiBong_TranDau;
import Model.GiaiDau;
import Model.TranDau;
import View.Admin.TranDau.XepLichThiDauPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ItemGiaiDauNull extends JPanel {

    private DoiBongDAO doiBongDAO = new DoiBongDAO();
    private TranDauDAO tranDauDAO = new TranDauDAO();
    private DoiBong_TranDauDAO dbtdDAO = new DoiBong_TranDauDAO();

    private GiaiDau giaiDau;
    private JLabel lblAnh;
    private JLabel lblTen;
    private XepLichThiDauPanel xepLichThiDauPanel;

    public ItemGiaiDauNull(GiaiDau gd, XepLichThiDauPanel xepLichThiDauPanel) {
        this.giaiDau = gd;
        this.xepLichThiDauPanel = xepLichThiDauPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(5, 5));
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
                int option = JOptionPane.showConfirmDialog(ItemGiaiDauNull.this,
                        "Bạn có muốn tạo lịch thi đấu cho giải này?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    // Gọi hàm tạo lịch thi đấu
                    taoLichThiDau(giaiDau);
                }
            }
        });
    }

    public GiaiDau getGiaiDau() {
        return giaiDau;
    }

    public void taoLichThiDau(GiaiDau giaiDau) {
        int maGiaiDau = giaiDau.getMaGiaiDau();

        List<DoiBong> dsDoi = doiBongDAO.findByMaGiaiDau(maGiaiDau);
        if (dsDoi.size() < 2) {
            JOptionPane.showMessageDialog(null, "Giải đấu phải có ít nhất 2 đội để tạo lịch thi đấu!");
            return;
        }

        for (int i = 0; i < dsDoi.size(); i++) {
            for (int j = i + 1; j < dsDoi.size(); j++) {
                TranDau td = new TranDau();
                td.setGiaiDau(giaiDau);
                td.setTrongTai(null); // có thể để null
                td.setThoiGian(null); // hoặc để mặc định

                int maTranDauMoi = tranDauDAO.insertAndGetId(td);
                if (maTranDauMoi <= 0) {
                    JOptionPane.showMessageDialog(null, "Lỗi tạo trận đấu!");
                    return;
                }

                // Tạo 2 quan hệ đội-trận đấu
                DoiBong_TranDau home = new DoiBong_TranDau();
                home.setTranDau(td);
                td.setMaTranDau(maTranDauMoi); // set id vừa tạo
                home.getTranDau().setMaTranDau(maTranDauMoi);
                home.setDoiBong(dsDoi.get(i));
                home.setLaChuNha(1); // chủ nhà
                dbtdDAO.insert(home);

                DoiBong_TranDau away = new DoiBong_TranDau();
                away.setTranDau(td);
                away.setDoiBong(dsDoi.get(j));
                away.setLaChuNha(0); // khách
                dbtdDAO.insert(away);
            }
        }

        JOptionPane.showMessageDialog(null, "Tạo lịch thi đấu thành công!");
        xepLichThiDauPanel.loadGiaiDau();
    }
}
