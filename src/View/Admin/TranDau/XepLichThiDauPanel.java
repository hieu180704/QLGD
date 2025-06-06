package View.Admin.TranDau;

import DAO.GiaiDauDAO;
import Model.GiaiDau;
import View.CustomPanel.ItemGiaiDauNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class XepLichThiDauPanel extends JPanel {

    private JLabel lblTitle;
    private JPanel panelDanhSachGiaiDau;
    private JScrollPane scrollDanhSachGiaiDau;
    private JButton btnTaoLichThiDau;

    private List<GiaiDau> danhSachGiaiDau;

    // Biến lưu item được chọn để quản lý trạng thái highlight
    private ItemGiaiDauNull selectedItem;

    public XepLichThiDauPanel() {
        initComponents();
        loadGiaiDau();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Tiêu đề lớn với font đẹp, màu xanh đậm
        lblTitle = new JLabel("Xếp Lịch Thi Đấu");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(25, 50, 120));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle, BorderLayout.NORTH);

        // Panel chứa danh sách các giải đấu
        panelDanhSachGiaiDau = new JPanel();
        panelDanhSachGiaiDau.setLayout(new BoxLayout(panelDanhSachGiaiDau, BoxLayout.Y_AXIS));
        panelDanhSachGiaiDau.setBackground(Color.WHITE);
        panelDanhSachGiaiDau.setBorder(new EmptyBorder(10, 10, 10, 10));

        scrollDanhSachGiaiDau = new JScrollPane(panelDanhSachGiaiDau);
        scrollDanhSachGiaiDau.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDanhSachGiaiDau.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDanhSachGiaiDau.setPreferredSize(new Dimension(450, 450));
        add(scrollDanhSachGiaiDau, BorderLayout.CENTER);

    }

    public void loadGiaiDau() {
        panelDanhSachGiaiDau.removeAll();
        GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
        danhSachGiaiDau = giaiDauDAO.findGiaiDauChuaCoTranDau();

        if (danhSachGiaiDau == null || danhSachGiaiDau.isEmpty()) {
            JLabel lblNoData = new JLabel("Không có giải đấu chưa có trận thi đấu");
            lblNoData.setForeground(Color.RED);
            lblNoData.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            lblNoData.setHorizontalAlignment(SwingConstants.CENTER);
            panelDanhSachGiaiDau.setLayout(new BorderLayout());
            panelDanhSachGiaiDau.add(lblNoData, BorderLayout.CENTER);
        } else {
            panelDanhSachGiaiDau.setLayout(new BoxLayout(panelDanhSachGiaiDau, BoxLayout.Y_AXIS));
            for (GiaiDau gd : danhSachGiaiDau) {
                addItemGiaiDau(gd);
            }
        }

        panelDanhSachGiaiDau.revalidate();
        panelDanhSachGiaiDau.repaint();
    }

    private void addItemGiaiDau(GiaiDau gd) {
        ItemGiaiDauNull item = new ItemGiaiDauNull(gd, this);
        item.setPreferredSize(new Dimension(420, 80));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // cho full chiều ngang khi resize

        // Thêm mouse listener để highlight và chọn duy nhất 1 item
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectItem(item);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (item != selectedItem) {
                    item.setBackground(new Color(230, 245, 255));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (item != selectedItem) {
                    item.setBackground(Color.WHITE);
                }
            }
        });

        panelDanhSachGiaiDau.add(item);
    }

    private void selectItem(ItemGiaiDauNull item) {
        if (selectedItem != null) {
            selectedItem.setBackground(Color.WHITE);
        }
        selectedItem = item;
        selectedItem.setBackground(new Color(175, 215, 255));
        // Tự động gọi sự kiện hoặc notify controller bên ngoài nếu cần
    }

    // Lấy giải đấu đang chọn
    public GiaiDau getSelectedGiaiDau() {
        if (selectedItem != null) {
            return selectedItem.getGiaiDau();
        }
        return null;
    }

    public JButton getBtnTaoLichThiDau() {
        return btnTaoLichThiDau;
    }

    // Nếu muốn controller bên ngoài gọi clear selection
    public void clearSelection() {
        if (selectedItem != null) {
            selectedItem.setBackground(Color.WHITE);
            selectedItem = null;
        }
    }
}
