package View.Admin.QuanLyDoiBong;

import Controller.DoiBongController;
import DAO.QuocGiaDAO;
import DAO.SanVanDongDAO;
import Model.DoiBong;
import Model.QuocGia;
import View.Admin.QuanLyCauThu.ImageCircleLabel;
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

        ImageCircleLabel lblAnh = new ImageCircleLabel();

        byte[] logoBytes = db.getLogoDoi();
        try {
            if (logoBytes != null) {
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(logoBytes));
                if (bufferedImage != null) {
                    Image scaledImage = bufferedImage.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                    lblAnh.setImage(scaledImage);
                } else {
                    lblAnh.setImage(null);
                }
            } else {
                lblAnh.setImage(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            lblAnh.setImage(null);
        }

        JPanel pnlAnhWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlAnhWrapper.setBorder(new EmptyBorder(5, 0, 0, 0));  // Cách mép trên 5px
        pnlAnhWrapper.setOpaque(false);  // Panel ảnh trong suốt để thấy nền ngoài
        pnlAnhWrapper.add(lblAnh);

        add(pnlAnhWrapper, BorderLayout.NORTH);

        JPanel pnlThongTin = new JPanel(new GridLayout(3, 1));
        pnlThongTin.setOpaque(false);  // Trong suốt để nền ngoài nổi bật
        pnlThongTin.add(new JLabel(db.getTenDoi() != null ? db.getTenDoi() : "", JLabel.CENTER));
        pnlThongTin.add(new JLabel(db.getTenQuocGia() != null ? db.getTenQuocGia() : "", JLabel.CENTER));
        pnlThongTin.add(new JLabel(db.getTenSanVanDong() != null ? db.getTenSanVanDong() : "", JLabel.CENTER));
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

                // Mở dialog SuaXoaDoiBongDialog
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
