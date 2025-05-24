package View.Admin.QuanLyCauThu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import Controller.CauThuController.QuanLyCauThuController;
import DAO.CauThuDAO;
import Model.CauThu;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class QuanLyCauThuView extends JPanel {
    private JTextField txtTimKiemTen;
    private JTextField txtTimKiemQuocTich;
    private JTextField txtTimKiemDoi;
    private JButton btnThemCauThu;
    private JPanel panelDanhSachCauThu;

    private CauThuDAO dao = new CauThuDAO();
    private List<CauThu> danhSachCauThu;

    private QuanLyCauThuController controller;

    public QuanLyCauThuView() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiemTen = new JTextField(15);
        txtTimKiemTen.setToolTipText("Tìm theo tên cầu thủ");
        txtTimKiemQuocTich = new JTextField(15);
        txtTimKiemQuocTich.setToolTipText("Tìm theo quốc tịch");
        txtTimKiemDoi = new JTextField(15);
        txtTimKiemDoi.setToolTipText("Tìm theo đội bóng");
        btnThemCauThu = new JButton("Thêm cầu thủ");

        panelTop.add(new JLabel("Tên:"));
        panelTop.add(txtTimKiemTen);
        panelTop.add(new JLabel("Quốc tịch:"));
        panelTop.add(txtTimKiemQuocTich);
        panelTop.add(new JLabel("Đội bóng:"));
        panelTop.add(txtTimKiemDoi);
        panelTop.add(btnThemCauThu);

        add(panelTop, BorderLayout.NORTH);

        panelDanhSachCauThu = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelDanhSachCauThu);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Controller liên kết view và model
        controller = new QuanLyCauThuController(this, dao);
        controller.loadData();
        controller.hienThiCauThu(controller.getDanhSachCauThu());

        // Các listener cho tìm kiếm
        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { controller.timKiemVaHienThi(); }
            public void removeUpdate(DocumentEvent e) { controller.timKiemVaHienThi(); }
            public void changedUpdate(DocumentEvent e) { controller.timKiemVaHienThi(); }
        };
        txtTimKiemTen.getDocument().addDocumentListener(docListener);
        txtTimKiemQuocTich.getDocument().addDocumentListener(docListener);
        txtTimKiemDoi.getDocument().addDocumentListener(docListener);

        // Nút thêm cầu thủ
        btnThemCauThu.addActionListener(e -> controller.showThemDialog());
    }

    // Getters cho controller truy cập các component
    public String getTimKiemTen() { return txtTimKiemTen.getText(); }
    public String getTimKiemQuocTich() { return txtTimKiemQuocTich.getText(); }
    public String getTimKiemDoi() { return txtTimKiemDoi.getText(); }
    public JPanel getPanelDanhSachCauThu() { return panelDanhSachCauThu; }
}

