package View.Admin;

import Controller.QuanLyController;
import View.CustomButton.RoundBorder;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class QuanLyView extends JFrame {

    private TrangChuPanel trangChuPanel = new TrangChuPanel();
    private QuanLyGiaiDauPanel quanLyGiaiDauPanel = new QuanLyGiaiDauPanel();
    private DanhSachDoiBongPanel danhSachDoiBongPanel = new DanhSachDoiBongPanel();
    private DanhSachCauThuPanel danhSachCauThuPanel = new DanhSachCauThuPanel();
    private DanhSachHLVPanel danhSachHLVPanel = new DanhSachHLVPanel();
    private DanhSachTrongTaiPanel danhSachTrongTaiPanel = new DanhSachTrongTaiPanel();
    private QuanLyTaiKhoanPanel quanLyTaiKhoanPanel = new QuanLyTaiKhoanPanel();

    private JLayeredPane layerPanel;
    private JButton btnTrangChu;
    private JButton btnDangXuat;
    private JButton btnQuanLyGiaiDau;
    private JButton btnDanhSachDoiBong;
    private JButton btnDanhSachCauThu;
    private JButton btnDanhSachTrongTai;
    private JButton btnDanhSachHLV;
    private JButton btnQuanLyTaiKhoan;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel sidebarPanel;
    private JPanel headerPanel;

    public QuanLyView() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        QuanLyController quanLyController = new QuanLyController(this);

        // Gán event và style cho các nút sidebar
        addSidebarButtonStyle(btnTrangChu, quanLyController);
        addSidebarButtonStyle(btnQuanLyGiaiDau, quanLyController);
        addSidebarButtonStyle(btnDanhSachDoiBong, quanLyController);
        addSidebarButtonStyle(btnDanhSachCauThu, quanLyController);
        addSidebarButtonStyle(btnDanhSachTrongTai, quanLyController);
        addSidebarButtonStyle(btnDanhSachHLV, quanLyController);
        addSidebarButtonStyle(btnQuanLyTaiKhoan, quanLyController);

        // Nút đăng xuất
        btnDangXuat.addActionListener(quanLyController);
        btnDangXuat.setBackground(new Color(255, 77, 77));
        btnDangXuat.setForeground(Color.WHITE);
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setMaximumSize(new Dimension(200, 50));
        btnDangXuat.setHorizontalAlignment(SwingConstants.CENTER);

        // Thêm các panel vào CardLayout
        CardLayout cardLayout = (CardLayout) layerPanel.getLayout();
        layerPanel.add(trangChuPanel, "TrangChuPanel");
        layerPanel.add(quanLyGiaiDauPanel, "QuanLyGiaiDauPanel");
        layerPanel.add(danhSachDoiBongPanel, "DanhSachDoiBongPanel");
        layerPanel.add(danhSachCauThuPanel, "DanhSachCauThuPanel");
        layerPanel.add(danhSachHLVPanel, "DanhSachHLVPanel");
        layerPanel.add(danhSachTrongTaiPanel, "DanhSachTrongTaiPanel");
        layerPanel.add(quanLyTaiKhoanPanel, "QuanLyTaiKhoanPanel");

        cardLayout.show(layerPanel, "TrangChuPanel");

        // Thiết lập màu nền sidebar
        sidebarPanel.setBackground(new Color(0, 51, 102));
        
        JButton[] sidebarButtons = {btnTrangChu, btnQuanLyGiaiDau, btnDanhSachDoiBong, btnDanhSachCauThu,
                btnDanhSachTrongTai, btnDanhSachHLV, btnQuanLyTaiKhoan};

        // Hiển thị username ở header (nếu cần)
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void addSidebarButtonStyle(JButton button, java.awt.event.ActionListener listener) {
        button.addActionListener(listener);

        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(15);
        button.setBackground(new Color(0, 51, 102));
        button.setOpaque(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 51, 102));
            }
        });
    }
    
    private RoundBorder rou = new RoundBorder();

    private void initComponents() {
        
        jLabel1 = new JLabel("    Quản Lý Giải Đấu Bóng Đá");
        jLabel2 = new JLabel("Username          ");
        layerPanel = new JLayeredPane();
        layerPanel.setLayout(new CardLayout());
        
        Color sidebarBg = new Color(0, 51, 102);
        Color sidebarHover = new Color(0, 102, 204);
        Color logoutBg = new Color(255, 77, 77);
        Color logoutHover = new Color(255, 102, 102);

        btnTrangChu = rou.createSidebarButton("Trang Chủ", 20, sidebarBg, sidebarHover);
        btnQuanLyGiaiDau = rou.createSidebarButton("Quản Lý Giải Đấu", 20, sidebarBg, sidebarHover);
        btnDanhSachDoiBong = rou.createSidebarButton("Danh Sách Đội Bóng", 20, sidebarBg, sidebarHover);
        btnDanhSachCauThu = rou.createSidebarButton("Danh Sách Cầu Thủ", 20, sidebarBg, sidebarHover);
        btnDanhSachTrongTai = rou.createSidebarButton("Danh Sách Trọng Tài", 20, sidebarBg, sidebarHover);
        btnDanhSachHLV = rou.createSidebarButton("Danh Sách HLV", 20, sidebarBg, sidebarHover);
        btnQuanLyTaiKhoan = rou.createSidebarButton("Quản Lý Tài Khoản", 20, sidebarBg, sidebarHover);

        btnDangXuat = rou.createSidebarButton("Đăng Xuất", 20, logoutBg, logoutHover);

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(sidebarBg);
        sidebarPanel.setPreferredSize(new Dimension(230, 600));

        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(btnTrangChu);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnQuanLyGiaiDau);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnDanhSachDoiBong);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnDanhSachCauThu);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnDanhSachTrongTai);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnDanhSachHLV);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnQuanLyTaiKhoan);

        sidebarPanel.add(Box.createVerticalGlue()); // đẩy btnDangXuat xuống dưới cùng

        sidebarPanel.add(btnDangXuat);
        sidebarPanel.add(Box.createVerticalStrut(20));
        
        // Màu riêng cho btnDangXuat
        btnDangXuat.setBackground(new Color(255, 51, 51));

        getContentPane().add(sidebarPanel, BorderLayout.WEST);

        // Header panel cấu hình
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setPreferredSize(new Dimension(770, 80));
        headerPanel.setLayout(new BorderLayout());

        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 24));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel1.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.add(jLabel1, BorderLayout.WEST);

        jLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        jLabel2.setForeground(Color.WHITE);
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        headerPanel.add(jLabel2, BorderLayout.EAST);

        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // Thêm layerPanel vào center
        getContentPane().add(layerPanel, BorderLayout.CENTER);

        pack();
    }

    // Các hàm mở panel
    public void openTrangChu() {
        showPanel("TrangChuPanel");
    }

    public void openQuanLyGiaiDauPanel() {
        showPanel("QuanLyGiaiDauPanel");
    }

    public void openDanhSachDoiBong() {
        showPanel("DanhSachDoiBongPanel");
    }

    public void openDanhSachCauThu() {
        showPanel("DanhSachCauThuPanel");
    }

    public void openDanhSachHLV() {
        showPanel("DanhSachHLVPanel");
    }

    public void openDanhSachTrongTai() {
        showPanel("DanhSachTrongTaiPanel");
    }

    public void openQuanLyTaiKhoan() {
        showPanel("QuanLyTaiKhoanPanel");
    }

    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) layerPanel.getLayout();
        cardLayout.show(layerPanel, panelName);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new QuanLyView().setVisible(true);
        });
    }
}
