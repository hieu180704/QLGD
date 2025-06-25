package View.Admin.QuanLyCauThu;

import Controller.QuanLyCauThuController;
import DAO.DoiBongDAO;
import DAO.QuocGiaDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import Model.CauThu;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.DoiBongItem;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import javax.imageio.ImageIO;

public class CauThuPanel extends JPanel {
    private QuocGiaDAO quocGiaDAO = new QuocGiaDAO();
    private DoiBongDAO doiBongDAO = new DoiBongDAO();

    public CauThuPanel(CauThu ct, QuanLyCauThuController controller) {
        setPreferredSize(new Dimension(140, 160));
        setLayout(new BorderLayout());
        setBackground(new Color(179, 218, 255));

        ImageCircleLabel lblAnh = new ImageCircleLabel();
        byte[] anhBytes = ct.getAnhCauThu();

        try {
            if (anhBytes != null && anhBytes.length > 0) {
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(anhBytes));
                if (bufferedImage != null) {
                    Image resizedImg = resizeImage(bufferedImage, 90, 90);
                    lblAnh.setImage(resizedImg);
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể đọc ảnh của cầu thủ.", "Lỗi ảnh", JOptionPane.WARNING_MESSAGE);
                    lblAnh.setImage(null);
                }
            } else {
                lblAnh.setImage(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải ảnh: " + e.getMessage(), "Lỗi ảnh", JOptionPane.ERROR_MESSAGE);
            lblAnh.setImage(null);
        }

        JPanel pnlAnhWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlAnhWrapper.setBorder(new EmptyBorder(5, 0, 5, 0)); // Thêm padding trên/dưới
        pnlAnhWrapper.setOpaque(false);
        pnlAnhWrapper.add(lblAnh);
        add(pnlAnhWrapper, BorderLayout.NORTH);

        JPanel pnlThongTin = new JPanel(new GridLayout(3, 1));
        pnlThongTin.setOpaque(false);
        pnlThongTin.setBorder(new EmptyBorder(5, 10, 5, 10)); // Thêm padding cho thông tin
        JLabel tenCauThuLabel = new JLabel(ct.getTenCauThu() != null ? ct.getTenCauThu() : "", JLabel.CENTER);
        tenCauThuLabel.setFont(new Font(tenCauThuLabel.getFont().getName(), Font.BOLD, 12));
        pnlThongTin.add(tenCauThuLabel);

        JLabel tenQuocGiaLabel = new JLabel(ct.getTenQuocGia() != null ? ct.getTenQuocGia() : "", JLabel.CENTER);
        tenQuocGiaLabel.setFont(new Font(tenQuocGiaLabel.getFont().getName(), Font.BOLD, 12));
        pnlThongTin.add(tenQuocGiaLabel);

        JLabel tenDoiLabel = new JLabel(ct.getTenDoi() != null ? ct.getTenDoi() : "", JLabel.CENTER);
        tenDoiLabel.setFont(new Font(tenDoiLabel.getFont().getName(), Font.BOLD, 12));
        pnlThongTin.add(tenDoiLabel);
        add(pnlThongTin, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    List<QuocGiaItem> dsQuocGia = quocGiaDAO.findAll().stream()
                            .map(qg -> new QuocGiaItem(qg.getMaQuocGia(), qg.getTenQuocGia()))
                            .collect(Collectors.toList());

                    List<DoiBongItem> dsDoiBong = doiBongDAO.findAll().stream()
                            .map(db -> new DoiBongItem(db.getMaDoiBong(), db.getTenDoi()))
                            .collect(Collectors.toList());

                    SuaXoaCauThuDialog dialog = new SuaXoaCauThuDialog(null, ct, dsQuocGia, dsDoiBong);
                    dialog.setVisible(true);

                    if (dialog.isUpdated()) {
                        CauThu updated = dialog.getCauThuSuaXoa(); // Sửa từ getCauThu() thành getCauThuSuaXoa()
                        controller.updateCauThu(updated);
                    } else if (dialog.isDeleted()) {
                        controller.deleteCauThu(ct.getMaCauThu());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CauThuPanel.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(CauThuPanel.this, "Lỗi khi xử lý: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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