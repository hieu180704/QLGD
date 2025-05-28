package View.Admin.TranDau;

import DAO.GiaiDauDAO;
import Model.GiaiDau;
import View.CustomPanel.ItemGiaiDauNull;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class XepLichThiDauPanel extends JPanel {

    private JLabel lblTitle;
    private JPanel panelDanhSachGiaiDau;
    private JScrollPane scrollDanhSachGiaiDau;
    private JButton btnTaoLichThiDau;

    private List<GiaiDau> danhSachGiaiDau;

    public XepLichThiDauPanel() {
        initComponents();
        loadGiaiDau();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // Tiêu đề lớn
        lblTitle = new JLabel("Xếp Lịch Thi Đấu");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(30, 30, 80));
        add(lblTitle, BorderLayout.NORTH);

        // Panel chứa danh sách các giải đấu chưa có trận đấu
        panelDanhSachGiaiDau = new JPanel();
        panelDanhSachGiaiDau.setLayout(new BoxLayout(panelDanhSachGiaiDau, BoxLayout.Y_AXIS));
        panelDanhSachGiaiDau.setBackground(Color.WHITE);
        panelDanhSachGiaiDau.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollDanhSachGiaiDau = new JScrollPane(panelDanhSachGiaiDau);
        scrollDanhSachGiaiDau.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDanhSachGiaiDau.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDanhSachGiaiDau.setPreferredSize(new Dimension(400, 400));
        add(scrollDanhSachGiaiDau, BorderLayout.CENTER);
    }

    public void loadGiaiDau() {
        panelDanhSachGiaiDau.removeAll();
        GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
        danhSachGiaiDau = giaiDauDAO.findGiaiDauChuaCoTranDau();
        if (danhSachGiaiDau != null && !danhSachGiaiDau.isEmpty()) {
            for (GiaiDau gd : danhSachGiaiDau) {
                addItemGiaiDau(gd);
            }
        } else {
            JLabel lblNoData = new JLabel("Không có dữ liệu giải đấu");
            lblNoData.setForeground(Color.RED);
            panelDanhSachGiaiDau.add(lblNoData);
        }
        panelDanhSachGiaiDau.revalidate();
        panelDanhSachGiaiDau.repaint();
    }

    private void addItemGiaiDau(GiaiDau gd) {
        ItemGiaiDauNull item = new ItemGiaiDauNull(gd,this);
        item.setPreferredSize(new Dimension(320, 80));
        panelDanhSachGiaiDau.add(item);
    }

    // Xóa lựa chọn các item khác
    private void clearSelection() {
        for (Component comp : panelDanhSachGiaiDau.getComponents()) {
            if (comp instanceof ItemGiaiDauNull) {
                comp.setBackground(Color.WHITE);
            }
        }
    }

    // Lấy giải đấu đã chọn
    private GiaiDau selectedGiaiDau;

    public GiaiDau getSelectedGiaiDau() {
        return selectedGiaiDau;
    }

    // Cho phép controller gán hành động cho nút tạo lịch thi đấu
    public JButton getBtnTaoLichThiDau() {
        return btnTaoLichThiDau;
    }
}
