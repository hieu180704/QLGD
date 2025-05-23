package View.Admin;

import View.Admin.QuanLyGiaiDau.QuanLyGiaiDauPanel;
import Controller.QuanLyController;

import Model.GiaiDau;
import View.Admin.QuanLyGiaiDau.DetailGiaiDauPanel;
import View.Admin.QuanLyGiaiDau.ThemGiaiDauPanel;

import View.CustomButton.RoundBorder;
import com.formdev.flatlaf.FlatLightLaf;
import DAO.TaiKhoanDAO;
import Model.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class QuanLyView extends JFrame {

    private TrangChuPanel trangChuPanel = new TrangChuPanel();
    private QuanLyGiaiDauPanel quanLyGiaiDauPanel = new QuanLyGiaiDauPanel();
    private DanhSachDoiBongPanel danhSachDoiBongPanel = new DanhSachDoiBongPanel();
    private DanhSachCauThuPanel danhSachCauThuPanel = new DanhSachCauThuPanel();
    private DanhSachHLVPanel danhSachHLVPanel = new DanhSachHLVPanel();
    private DanhSachTrongTaiPanel danhSachTrongTaiPanel = new DanhSachTrongTaiPanel();
    private QuanLyTaiKhoanPanel quanLyTaiKhoanPanel = new QuanLyTaiKhoanPanel();
    private DetailGiaiDauPanel detailGiaiDauPanel = new DetailGiaiDauPanel();
    private RoundBorder rou = new RoundBorder();

    private TaiKhoan usercurrent;
    private int maTaiKhoan;

    private JLayeredPane layerPanel;
    private JButton btnTrangChu;
    private JButton btnDangXuat;
    private JButton btnQuanLyGiaiDau;
    private JButton btnDanhSachDoiBong;
    private JButton btnDanhSachCauThu;
    private JButton btnDanhSachTrongTai;
    private JButton btnQuanLyTranDau;
    private JButton btnDanhSachHLV;
    private JButton btnQuanLyTaiKhoan;
    private JButton btnQuanLySanDau;
    private JButton btnQuanLyNhaTaiTro;
    private JButton btnThongKe;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel sidebarPanel;
    private JPanel headerPanel;

    public QuanLyView() {
        designView();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        QuanLyController quanLyController = new QuanLyController(this);

        // Gán event và style cho các nút sidebar
        addSidebarButtonStyle(btnTrangChu, quanLyController);
        addSidebarButtonStyle(btnQuanLyGiaiDau, quanLyController);
        addSidebarButtonStyle(btnQuanLyTranDau, quanLyController);
        addSidebarButtonStyle(btnDanhSachDoiBong, quanLyController);
        addSidebarButtonStyle(btnDanhSachCauThu, quanLyController);
        addSidebarButtonStyle(btnDanhSachTrongTai, quanLyController);
        addSidebarButtonStyle(btnDanhSachHLV, quanLyController);
        addSidebarButtonStyle(btnQuanLyTaiKhoan, quanLyController);
        addSidebarButtonStyle(btnQuanLySanDau, quanLyController);
        addSidebarButtonStyle(btnQuanLyNhaTaiTro, quanLyController);
        addSidebarButtonStyle(btnThongKe, quanLyController);

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

    private void designView() {

        jLabel1 = new JLabel("    Quản Lý Giải Đấu Bóng Đá");
        jLabel2 = new JLabel("Username          ");
        layerPanel = new JLayeredPane();
        layerPanel.setLayout(new CardLayout());

        Color sidebarBg = new Color(0, 51, 102);
        Color sidebarHover = new Color(0, 102, 204);
        Color logoutBg = new Color(255, 77, 77);
        Color logoutHover = new Color(255, 102, 102);

        btnTrangChu = rou.createSidebarButton("Trang Chủ", 20, sidebarBg, sidebarHover);
        ImageIcon anhTC1 = new ImageIcon(getClass().getResource("/Resources/home.png"));
        Image anhTC2 = anhTC1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhTC3 = new ImageIcon(anhTC2);
        btnTrangChu.setIcon(anhTC3);
        btnTrangChu.setIconTextGap(5);
        btnQuanLyGiaiDau = rou.createSidebarButton("Quản Lý Giải Đấu", 20, sidebarBg, sidebarHover);
        ImageIcon anhGD1 = new ImageIcon(getClass().getResource("/Resources/tournament.png"));
        Image anhGD2 = anhGD1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhGD3 = new ImageIcon(anhGD2);
        btnQuanLyGiaiDau.setIcon(anhGD3);
        btnQuanLyGiaiDau.setIconTextGap(5);
        btnQuanLyTranDau = rou.createSidebarButton("Quản Lý Trận Đấu", 20, sidebarBg, sidebarHover);
        ImageIcon anhTD1 = new ImageIcon(getClass().getResource("/Resources/match.png"));
        Image anhTD2 = anhTD1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhTD3 = new ImageIcon(anhTD2);
        btnQuanLyTranDau.setIcon(anhTD3);
        btnQuanLyTranDau.setIconTextGap(5);
        btnDanhSachDoiBong = rou.createSidebarButton("Danh Sách Đội Bóng", 20, sidebarBg, sidebarHover);
        ImageIcon anhDB1 = new ImageIcon(getClass().getResource("/Resources/football-club.png"));
        Image anhDB2 = anhDB1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhDB3 = new ImageIcon(anhDB2);
        btnDanhSachDoiBong.setIcon(anhDB3);
        btnDanhSachDoiBong.setIconTextGap(5);
        btnDanhSachCauThu = rou.createSidebarButton("Danh Sách Cầu Thủ", 20, sidebarBg, sidebarHover);
        ImageIcon anhCT1 = new ImageIcon(getClass().getResource("/Resources/soccer-player.png"));
        Image anhCT2 = anhCT1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhCT3 = new ImageIcon(anhCT2);
        btnDanhSachCauThu.setIcon(anhCT3);
        btnDanhSachCauThu.setIconTextGap(5);
        btnDanhSachTrongTai = rou.createSidebarButton("Danh Sách Trọng Tài", 20, sidebarBg, sidebarHover);
        ImageIcon anhTT1 = new ImageIcon(getClass().getResource("/Resources/referee.png"));
        Image anhTT2 = anhTT1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhTT3 = new ImageIcon(anhTT2);
        btnDanhSachTrongTai.setIcon(anhTT3);
        btnDanhSachTrongTai.setIconTextGap(5);
        btnDanhSachHLV = rou.createSidebarButton("Danh Sách HLV", 20, sidebarBg, sidebarHover);
        ImageIcon anhHLV1 = new ImageIcon(getClass().getResource("/Resources/manager.png"));
        Image anhHLV2 = anhHLV1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhHLV3 = new ImageIcon(anhHLV2);
        btnDanhSachHLV.setIcon(anhHLV3);
        btnDanhSachHLV.setIconTextGap(5);
        btnQuanLyTaiKhoan = rou.createSidebarButton("Quản Lý Tài Khoản", 20, sidebarBg, sidebarHover);
        ImageIcon anhTK1 = new ImageIcon(getClass().getResource("/Resources/user.png"));
        Image anhTK2 = anhTK1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhTK3 = new ImageIcon(anhTK2);
        btnQuanLyTaiKhoan.setIcon(anhTK3);
        btnQuanLyTaiKhoan.setIconTextGap(5);
        btnQuanLySanDau = rou.createSidebarButton("Quản Lý Sân Đấu", 20, sidebarBg, sidebarHover);
        ImageIcon anhSD1 = new ImageIcon(getClass().getResource("/Resources/stadium.png"));
        Image anhSD2 = anhSD1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhSD3 = new ImageIcon(anhSD2);
        btnQuanLySanDau.setIcon(anhSD3);
        btnQuanLySanDau.setIconTextGap(5);
        btnQuanLyNhaTaiTro = rou.createSidebarButton("Quản Lý Nhà Tài Trợ", 20, sidebarBg, sidebarHover);
        ImageIcon anhNTT1 = new ImageIcon(getClass().getResource("/Resources/sponsor.png"));
        Image anhNTT2 = anhNTT1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhNTT3 = new ImageIcon(anhNTT2);
        btnQuanLyNhaTaiTro.setIcon(anhNTT3);
        btnQuanLyNhaTaiTro.setIconTextGap(5);
        btnThongKe = rou.createSidebarButton("Thống Kê", 20, sidebarBg, sidebarHover);
        ImageIcon anhT1 = new ImageIcon(getClass().getResource("/Resources/total.png"));
        Image anhT2 = anhT1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon anhT3 = new ImageIcon(anhT2);
        btnThongKe.setIcon(anhT3);
        btnThongKe.setIconTextGap(5);

        btnDangXuat = new JButton("Đăng Xuất");

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(sidebarBg);
        sidebarPanel.setPreferredSize(new Dimension(260, 600));

        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(btnTrangChu);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnQuanLyGiaiDau);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnQuanLyTranDau);
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
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnQuanLySanDau);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnQuanLyNhaTaiTro);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnThongKe);

        sidebarPanel.add(Box.createVerticalGlue());
        getContentPane().add(sidebarPanel, BorderLayout.WEST);

        // Header panel cấu hình
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 51, 102));
        headerPanel.setPreferredSize(new Dimension(770, 80));

        jLabel1 = new JLabel("    Quản Lý Giải Đấu Bóng Đá");
        jLabel1.setFont(new Font("Tahoma", Font.BOLD, 24));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel1.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.add(jLabel1, BorderLayout.WEST);

        JPanel userButtonGroup = new JPanel();
        userButtonGroup.setLayout(new BoxLayout(userButtonGroup, BoxLayout.X_AXIS));
        userButtonGroup.setOpaque(false);

        jLabel2 = new JLabel("Username");
        jLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
        jLabel2.setForeground(Color.WHITE);
        userButtonGroup.add(jLabel2);
        userButtonGroup.add(Box.createRigidArea(new Dimension(8, 0)));

        btnDangXuat = new JButton("Đăng Xuất");
        btnDangXuat.setBackground(new Color(255, 77, 77));
        btnDangXuat.setForeground(Color.WHITE);
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangXuat.setPreferredSize(null);
        btnDangXuat.setMaximumSize(null);
        userButtonGroup.add(btnDangXuat);

        JPanel userLogoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        userLogoutPanel.setOpaque(false);
        userLogoutPanel.add(userButtonGroup);
        userLogoutPanel.setBorder(BorderFactory.createEmptyBorder(27, 0, 0, 0));

        headerPanel.add(userLogoutPanel, BorderLayout.EAST);

        getContentPane().add(headerPanel, BorderLayout.NORTH);
        // Thêm layerPanel vào center
        getContentPane().add(layerPanel, BorderLayout.CENTER);

        pack();
    }

    // Cập nhật label hiển thị tên người dùng
    public void updateUserInfoDisplay(TaiKhoan user) {
        jLabel2.setText(user.getTendangnhap());
    }

    // Các hàm mở panel
    public void openTrangChu() {
        showPanel("TrangChuPanel");
    }

    public void openQuanLyGiaiDauPanel() {
        quanLyGiaiDauPanel.loadData(); //Load dữ liệu trước khi gọi panel
        CardLayout cardLayout = (CardLayout) layerPanel.getLayout();
        cardLayout.show(layerPanel, "QuanLyGiaiDauPanel");
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

    public void openThemGiaiDauPanel() {
        showPanel("ThemGiaiDauPanel");
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
