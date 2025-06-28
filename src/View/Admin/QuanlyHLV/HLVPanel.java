package View.Admin.QuanLyHLV;

import Controller.HLVController;
import DAO.DoiBongDAO;
import DAO.QuocGiaDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import Model.HLV;
import View.Admin.QuanLyCauThu.ImageCircleLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.QuocGiaItem;
import View.Admin.QuanLyCauThu.ThemCauThuDialog.DoiBongItem;

public class HLVPanel extends JPanel {

    private QuocGiaDAO quocGiaDAO = new QuocGiaDAO();
    private DoiBongDAO doiBongDAO = new DoiBongDAO();

    public HLVPanel(HLV hlv, HLVController controller) throws IOException {
        setPreferredSize(new Dimension(140, 160));
        setLayout(new BorderLayout());

        setBackground(new Color(179, 218, 255));

        ImageCircleLabel lblAnh = new ImageCircleLabel();

        byte[] anhBytes = hlv.getAnhHLV();
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(anhBytes));
            if (bufferedImage != null) {
                Image resizedImg = bufferedImage.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                lblAnh.setImage(resizedImg);
            } else {
                lblAnh.setImage(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            lblAnh.setImage(null);
        }

        JPanel pnlAnhWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlAnhWrapper.setBorder(new EmptyBorder(5, 0, 0, 0));
        pnlAnhWrapper.setOpaque(false);
        pnlAnhWrapper.add(lblAnh);

        add(pnlAnhWrapper, BorderLayout.NORTH);

        JPanel pnlThongTin = new JPanel(new GridLayout(3, 1));
        pnlThongTin.setOpaque(false);
        JLabel tenHLVLabel = new JLabel(hlv.getTenHLV() != null ? hlv.getTenHLV() : "", JLabel.CENTER);
        tenHLVLabel.setFont(new Font(tenHLVLabel.getFont().getName(), Font.BOLD, tenHLVLabel.getFont().getSize()));
        pnlThongTin.add(tenHLVLabel);

        JLabel tenQuocGiaLabel = new JLabel(hlv.getTenQuocGia() != null ? hlv.getTenQuocGia() : "", JLabel.CENTER);
        tenQuocGiaLabel.setFont(new Font(tenQuocGiaLabel.getFont().getName(), Font.BOLD, tenQuocGiaLabel.getFont().getSize()));
        pnlThongTin.add(tenQuocGiaLabel);

        JLabel tenDoiLabel = new JLabel(hlv.getTenDoi() != null ? hlv.getTenDoi() : "", JLabel.CENTER);
        tenDoiLabel.setFont(new Font(tenDoiLabel.getFont().getName(), Font.BOLD, tenDoiLabel.getFont().getSize()));
        pnlThongTin.add(tenDoiLabel);

        add(pnlThongTin, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<QuocGiaItem> dsQuocGia = quocGiaDAO.findAll().stream()
                    .map(qg -> new QuocGiaItem(qg.getMaQuocGia(), qg.getTenQuocGia()))
                    .collect(Collectors.toList());

                List<DoiBongItem> dsDoiBong = doiBongDAO.findAll().stream()
                    .map(db -> new DoiBongItem(db.getMaDoiBong(), db.getTenDoi()))
                    .collect(Collectors.toList());

                SuaXoaHLVDialog dialog = new SuaXoaHLVDialog(null, hlv, dsQuocGia, dsDoiBong);
                dialog.setVisible(true);

                if (dialog.isUpdated()) {
                    HLV updated = dialog.getHLV();
                    try {
                        controller.updateHLV(updated);
                    } catch (IOException ex) {
                        Logger.getLogger(HLVPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (dialog.isDeleted()) {
                    try {
                        controller.deleteHLV(hlv.getMaHLV());
                    } catch (IOException ex) {
                        Logger.getLogger(HLVPanel.class.getName()).log(Level.SEVERE, null, ex);
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