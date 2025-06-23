package Controller;

import DAO.DoiBong_TranDauDAO;
import DAO.GiaiDauDAO;
import DAO.TranDauDAO;
import Model.DoiBong_TranDau;
import Model.GiaiDau;
import Model.TranDau;
import Model.TrongTai;
import View.Admin.TranDau.ChiTietTranDauDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class TranDauController implements ActionListener {

    private ChiTietTranDauDialog dialog;
    private TranDau tranDau;

    private TranDauDAO tranDauDAO = new TranDauDAO();
    private DoiBong_TranDauDAO dbtdDAO = new DoiBong_TranDauDAO();
    private GiaiDauDAO giaiDauDAO = new GiaiDauDAO();

    public TranDauController(ChiTietTranDauDialog dialog, TranDau tranDau) {
        this.dialog = dialog;
        this.tranDau = tranDau;
        this.dialog.getBtnLuu().addActionListener(this);
        this.dialog.getBtnDong().addActionListener(e -> dialog.dispose());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dialog.getBtnLuu()) {
            saveTranDau();
        }
    }

    private void saveTranDau() {
        try {
            int hour = (Integer) dialog.getCbHour().getSelectedItem();
            int minute = (Integer) dialog.getCbMinute().getSelectedItem();

            if (dialog.getDatePicker().getDate() == null) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng chọn ngày trận đấu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate selectedDate = dialog.getDatePicker().getDate();
            LocalDate today = LocalDate.now();

            if (selectedDate.isBefore(today)) {
                JOptionPane.showMessageDialog(dialog, "Ngày thi đấu không được nhỏ hơn ngày hiện tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Lấy thông tin chi tiết giải đấu đầy đủ
//            GiaiDau giaiDau = tranDau.getGiaiDau();
//            if (giaiDau != null) {
//                GiaiDauDAO giaiDauDAO = new GiaiDauDAO();
//                giaiDau = giaiDauDAO.findById(giaiDau.getMaGiaiDau());
//
//                if (giaiDau != null) {
//                    LocalDate giaiDauStart = giaiDau.getNgayBatDau();
//                    LocalDate giaiDauEnd = giaiDau.getNgayKetThuc();
//
//                    if (giaiDauStart != null && selectedDate.isBefore(giaiDauStart)) {
//                        JOptionPane.showMessageDialog(dialog,
//                                "Ngày trận đấu không được trước ngày bắt đầu giải đấu (" + giaiDauStart + ")!",
//                                "Lỗi", JOptionPane.ERROR_MESSAGE);
//                        return;
//                    }
//
//                    if (giaiDauEnd != null && selectedDate.isAfter(giaiDauEnd)) {
//                        JOptionPane.showMessageDialog(dialog,
//                                "Ngày trận đấu không được sau ngày kết thúc giải đấu (" + giaiDauEnd + ")!",
//                                "Lỗi", JOptionPane.ERROR_MESSAGE);
//                        return;
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(dialog, "Không tìm thấy thông tin giải đấu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//            } else {
//                JOptionPane.showMessageDialog(dialog, "Chưa có giải đấu liên quan cho trận đấu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return;
//            }

            tranDau.setThoiGian(selectedDate.atTime(hour, minute));

            TrongTai selectedReferee = (TrongTai) dialog.getCbTrongTai().getSelectedItem();
            tranDau.setTrongTai(selectedReferee);

            boolean success = tranDauDAO.update(tranDau);
            if (!success) {
                JOptionPane.showMessageDialog(dialog, "Lỗi lưu thời gian trận đấu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (DoiBong_TranDau dbtd : tranDau.getDoiBongTranDauList()) {
                var map = dbtd.getLaChuNha() == 1 ? dialog.getSpnTeam1Stats() : dialog.getSpnTeam2Stats();

                dbtd.setSoLanSut((Integer) map.get("Số lần sút").getValue());
                dbtd.setSoBanThang((Integer) map.get("Số bàn thắng").getValue());
                dbtd.setSutTrungDich((Integer) map.get("Sút trúng đích").getValue());
                dbtd.setKiemSoatBong((Integer) map.get("Cầm Bóng").getValue());
                dbtd.setLuotChuyenBong((Integer) map.get("Lượt chuyền bóng").getValue());
                dbtd.setChuyenChinhXac((Integer) map.get("Chuyền chính xác").getValue());
                dbtd.setPhamLoi((Integer) map.get("Phạm lỗi").getValue());
                dbtd.setVietVi((Integer) map.get("Việt vị").getValue());
                dbtd.setPhatGoc((Integer) map.get("Phạt góc").getValue());
                
                boolean dbtdSuccess = dbtdDAO.update(dbtd);
                if (!dbtdSuccess) {
                    JOptionPane.showMessageDialog(dialog, "Lỗi lưu thông số đội bóng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            JOptionPane.showMessageDialog(dialog, "Lưu dữ liệu thành công!");
            if (dialog.getTranDauPanel() != null) {
                dialog.getTranDauPanel().loadTranDau(-1);
            }
            dialog.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
