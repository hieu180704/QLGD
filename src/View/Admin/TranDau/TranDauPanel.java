package View.Admin.TranDau;

import Controller.TranDauController;
import DAO.GiaiDauDAO;
import DAO.TranDauDAO;
import Model.GiaiDau;
import Model.TranDau;
import View.CustomPanel.ItemTranDau;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TranDauPanel extends JPanel {

    private JComboBox<GiaiDau> cbGiaiDauLoc;
    private List<TranDau> danhSachTranDau;
    private List<GiaiDau> dsGiaiDauCache;

    // Phần danh sách trận đấu dưới dạng item
    private JPanel panelDanhSachTranDauContent;
    private JScrollPane scrollTranDau;
    private TranDauDAO tranDauDAO;
    private int maGiaiSelected = 0;

    public TranDauPanel() {
        initComponents();
        loadTranDau(-1);
        loadGiaiDauLoc();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Bộ lọc trận đấu
        JPanel panelLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelLoc.setBorder(BorderFactory.createTitledBorder("Bộ lọc trận đấu"));
        panelLoc.add(new JLabel("Giải đấu:"));
        cbGiaiDauLoc = new JComboBox<>();
        cbGiaiDauLoc.setPreferredSize(new Dimension(200, 25));
        panelLoc.add(cbGiaiDauLoc);
        add(panelLoc, BorderLayout.NORTH);

        panelDanhSachTranDauContent = new JPanel();
        panelDanhSachTranDauContent.setLayout(new GridLayout(0, 4, 15, 15)); // 3 cột, tự động dòng, khoảng cách 15px
        panelDanhSachTranDauContent.setBackground(Color.WHITE);

        scrollTranDau = new JScrollPane(panelDanhSachTranDauContent);
        scrollTranDau.setPreferredSize(new Dimension(800, 400));
        scrollTranDau.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTranDau.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel panelTranDau = new JPanel(new BorderLayout());
        panelTranDau.setBorder(BorderFactory.createTitledBorder("Danh sách trận đấu"));
        panelTranDau.add(scrollTranDau, BorderLayout.CENTER);
        add(panelTranDau, BorderLayout.CENTER);

        cbGiaiDauLoc.addActionListener(e -> {
            GiaiDau selectedGiaiDau = (GiaiDau) cbGiaiDauLoc.getSelectedItem();
            if (selectedGiaiDau != null) {
                int maGiai = selectedGiaiDau.getMaGiaiDau();

                // Gọi hàm load trận đấu theo mã giải
                if (maGiai == 0) {
                    loadTranDau(-1); // load tất cả
                } else {
                    loadTranDau(maGiai);
                }
            }
        });
    }

    public void loadTranDau(int maGiaiDau) {
        tranDauDAO = new TranDauDAO();
        panelDanhSachTranDauContent.removeAll();

        if (maGiaiDau < 0) {
            danhSachTranDau = tranDauDAO.findAll();
        } else {
            danhSachTranDau = tranDauDAO.findByMaGiaiDau(maGiaiDau);
        }

        if (danhSachTranDau != null && !danhSachTranDau.isEmpty()) {
            for (TranDau td : danhSachTranDau) {
                addItemTranDau(td);
            }
        } else {
            JLabel lblNoData = new JLabel("Không có dữ liệu trận đấu");
            lblNoData.setForeground(Color.RED);
            panelDanhSachTranDauContent.add(lblNoData);
        }

        panelDanhSachTranDauContent.revalidate();
        panelDanhSachTranDauContent.repaint();
    }

    private void addItemTranDau(TranDau td) {
        ItemTranDau item = new ItemTranDau(td);
        item.setPreferredSize(new Dimension(240, 120));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Window window = SwingUtilities.getWindowAncestor(item);
                Frame owner = (window instanceof Frame) ? (Frame) window : null;

                ChiTietTranDauDialog dialog = new ChiTietTranDauDialog(owner, TranDauPanel.this);
                dialog.loadTranDau(td);

                // Tạo controller cho dialog và truyền đối tượng TranDau
                TranDauController controller = new TranDauController(dialog, td);

                dialog.setVisible(true);
            }
        });
        panelDanhSachTranDauContent.add(item);
    }

    public void loadGiaiDauLoc() {
        GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
        dsGiaiDauCache = giaiDauDAO.findGiaiDauCoTranDau();

        cbGiaiDauLoc.removeAllItems();

        // Tạo 1 đối tượng GiaiDau đại diện cho "Tất cả"
        GiaiDau allGiaiDau = new GiaiDau();
        allGiaiDau.setMaGiaiDau(0);
        allGiaiDau.setTenGiaiDau("Tất cả");
        cbGiaiDauLoc.addItem(allGiaiDau);

        if (dsGiaiDauCache != null && !dsGiaiDauCache.isEmpty()) {
            for (GiaiDau gd : dsGiaiDauCache) {
                cbGiaiDauLoc.addItem(gd);
            }
        }
    }
}
