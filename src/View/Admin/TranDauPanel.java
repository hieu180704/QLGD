package View.Admin;

import DAO.GiaiDauDAO;
import View.CustomPanel.ItemGiaiDauNull;
import Model.GiaiDau;
import View.Admin.QuanLyGiaiDau.DetailGiaiDauPanel;
import View.CustomPanel.ItemGiaiDau;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TranDauPanel extends JPanel {

    // Thay cbGiaiDauChuaTran thành panel chứa item
    private JPanel panelGiaiDauChuaTranContent;
    private JScrollPane scrollGiaiDauChuaTran;

    private JComboBox<String> cbGiaiDauLoc;
    private DatePicker dpThoiGianLoc;
    private JComboBox<String> cbTrangThaiTran;

    private JTable tblTranDau;
    private DefaultTableModel modelTranDau;
    private List<GiaiDau> danhSachGiaiDau;
    private GiaiDauDAO giaiDauDAO;

    public TranDauPanel() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel chứa các ItemGiaiDauNull
        JPanel panelChuaTran = new JPanel(new BorderLayout());
        panelChuaTran.setBorder(BorderFactory.createTitledBorder("Giải đấu chưa có trận đấu"));

        panelGiaiDauChuaTranContent = new JPanel();
        panelGiaiDauChuaTranContent.setLayout(new FlowLayout(FlowLayout.LEFT,15,15));
        scrollGiaiDauChuaTran = new JScrollPane(panelGiaiDauChuaTranContent);
        scrollGiaiDauChuaTran.setPreferredSize(new Dimension(320, 150));

        panelChuaTran.add(scrollGiaiDauChuaTran, BorderLayout.CENTER);

        add(panelChuaTran, BorderLayout.NORTH);

        // Phần lọc trận đấu vẫn giữ như cũ
        JPanel panelLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelLoc.setBorder(BorderFactory.createTitledBorder("Bộ lọc trận đấu"));

        panelLoc.add(new JLabel("Giải đấu:"));
        cbGiaiDauLoc = new JComboBox<>();
        cbGiaiDauLoc.setPreferredSize(new Dimension(200, 25));
        panelLoc.add(cbGiaiDauLoc);

        panelLoc.add(new JLabel("Ngày đá:"));
        dpThoiGianLoc = new DatePicker();
        dpThoiGianLoc.setPreferredSize(new Dimension(150, 25));
        panelLoc.add(dpThoiGianLoc);

        panelLoc.add(new JLabel("Trạng thái:"));
        cbTrangThaiTran = new JComboBox<>(new String[]{"Tất cả", "Chưa đá", "Đã đá"});
        cbTrangThaiTran.setPreferredSize(new Dimension(120, 25));
        panelLoc.add(cbTrangThaiTran);

        add(panelLoc, BorderLayout.CENTER);

        // Bảng trận đấu
        modelTranDau = new DefaultTableModel(new Object[]{"Mã trận", "Trọng tài", "Thời gian", "Giải đấu", "Trạng thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTranDau = new JTable(modelTranDau);
        JScrollPane scrollPane = new JScrollPane(tblTranDau);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(BorderFactory.createTitledBorder("Danh sách trận đấu"));
        panelTable.add(scrollPane, BorderLayout.CENTER);

        add(panelTable, BorderLayout.SOUTH);
    }

    public void loadData() {
        panelGiaiDauChuaTranContent.removeAll();
        giaiDauDAO = new GiaiDauDAO();
        danhSachGiaiDau = giaiDauDAO.findAll();
        if (danhSachGiaiDau != null && !danhSachGiaiDau.isEmpty()) {
            for (GiaiDau gd : danhSachGiaiDau) {
                addItemGiaiDau(gd);
            }
        } else {
            JLabel lblNoData = new JLabel("Không có dữ liệu giải đấu");
            lblNoData.setForeground(Color.RED);
            panelGiaiDauChuaTranContent.add(lblNoData);
        }
        panelGiaiDauChuaTranContent.revalidate();
        panelGiaiDauChuaTranContent.repaint();
    }

    private void addItemGiaiDau(GiaiDau gd) {
        ItemGiaiDauNull item = new ItemGiaiDauNull(gd);
        item.setPreferredSize(new Dimension(320, 80));
        panelGiaiDauChuaTranContent.add(item);
    }

    // Các getter để controller thao tác
    public JComboBox<String> getCbGiaiDauLoc() {
        return cbGiaiDauLoc;
    }

    public DatePicker getDpThoiGianLoc() {
        return dpThoiGianLoc;
    }

    public JComboBox<String> getCbTrangThaiTran() {
        return cbTrangThaiTran;
    }

    public JTable getTblTranDau() {
        return tblTranDau;
    }

    public DefaultTableModel getModelTranDau() {
        return modelTranDau;
    }
}
