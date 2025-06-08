package View.Admin.QuanLyDoiBong;

import Controller.DoiBongController;
import DAO.QuocGiaDAO;
import DAO.SanVanDongDAO;
import Model.DoiBong;
import Model.QuocGia;
import View.Admin.QuanLyCauThu.ThemCauThuDialog;
import View.Admin.QuanLyDoiBong.SuaXoaDoiBongDialog;
import View.Admin.QuanLyDoiBong.ThemDoiBongDialog.SanVanDongItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

public class DoiBongPanel extends JPanel {
    private QuocGiaDAO quocGiaDAO = new QuocGiaDAO();
    private SanVanDongDAO sanVanDongDAO = new SanVanDongDAO();

    public DoiBongPanel(DoiBong db, DoiBongController controller) throws IOException {
        setPreferredSize(new Dimension(140, 160));
        setLayout(new BorderLayout());

        setBackground(new Color(179, 218, 255));  // Nền xanh nhạt

        // Sử dụng JLabel thông thường thay vì ImageCircleLabel
        JLabel lblAnh = new JLabel();

        byte[] logoBytes = db.getLogoDoi();
        try {
            if (logoBytes != null) {
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(logoBytes));
                if (bufferedImage != null) {
                    Image scaledImage = resizeImage(bufferedImage, 85, 85); // Giới hạn ở 85x85
                    lblAnh.setIcon(new ImageIcon(scaledImage));
                } else {
                    lblAnh.setIcon(null);
                }
            } else {
                lblAnh.setIcon(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            lblAnh.setIcon(null);
        }

        JPanel pnlAnhWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlAnhWrapper.setBorder(new EmptyBorder(5, 0, 0, 0));  
        pnlAnhWrapper.setOpaque(false);  
        pnlAnhWrapper.add(lblAnh);

        add(pnlAnhWrapper, BorderLayout.NORTH);

        JPanel pnlThongTin = new JPanel(new GridLayout(3, 1));
        pnlThongTin.setOpaque(false);  

        // Tạo và đặt font in đậm cho các nhãn
        JLabel teamLabel = new JLabel(db.getTenDoi() != null ? db.getTenDoi() : "", JLabel.CENTER);
        teamLabel.setFont(new Font(teamLabel.getFont().getName(), Font.BOLD, teamLabel.getFont().getSize()));
        pnlThongTin.add(teamLabel);

        JLabel countryLabel = new JLabel(db.getTenQuocGia() != null ? db.getTenQuocGia() : "", JLabel.CENTER);
        countryLabel.setFont(new Font(countryLabel.getFont().getName(), Font.BOLD, countryLabel.getFont().getSize()));
        pnlThongTin.add(countryLabel);

        JLabel stadiumLabel = new JLabel(db.getTenSanVanDong() != null ? db.getTenSanVanDong() : "", JLabel.CENTER);
        stadiumLabel.setFont(new Font(stadiumLabel.getFont().getName(), Font.BOLD, stadiumLabel.getFont().getSize()));
        pnlThongTin.add(stadiumLabel);

        add(pnlThongTin, BorderLayout.CENTER);

        // Thêm MouseListener để mở dialog khi nhấn vào đội bóng
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<QuocGia> dsQuocGia = quocGiaDAO.findAll();
                
                List<ThemCauThuDialog.QuocGiaItem> dsQuocGiaItems = dsQuocGia.stream()
                    .map(qg -> new ThemCauThuDialog.QuocGiaItem(qg.getMaQuocGia(), qg.getTenQuocGia()))
                    .collect(Collectors.toList());
                List<SanVanDongItem> dsSanVanDong = sanVanDongDAO.getAllSanVanDong().stream()
                        .map(svd -> new SanVanDongItem(svd.getMaSVD(), svd.getTenSVD()))
                        .collect(Collectors.toList());

                
                SuaXoaDoiBongDialog dialog = new SuaXoaDoiBongDialog(null, db, dsQuocGiaItems, dsSanVanDong);
                dialog.setVisible(true);

                if (dialog.isUpdated()) {
                    DoiBong updatedDoiBong = dialog.getDoiBongSuaXoa();
                    try {
                        controller.updateDoiBong(updatedDoiBong);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (dialog.isDeleted()) {
                    try {
                        controller.deleteDoiBong(db.getMaDoiBong());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private Image resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resizedImage;
    }
}