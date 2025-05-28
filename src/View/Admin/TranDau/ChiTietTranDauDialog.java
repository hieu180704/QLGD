package View.Admin.TranDau;

import DAO.DoiBong_TranDauDAO;
import DAO.TranDauDAO;
import DAO.TrongTaiDAO;
import Model.TranDau;
import Model.DoiBong_TranDau;
import Model.TrongTai;
import View.Admin.TranDau.TranDauPanel;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public class ChiTietTranDauDialog extends JDialog {

    private DatePicker datePicker;
    private JComboBox<Integer> cbHour, cbMinute;
    private JComboBox<TrongTai> cbTrongTai;

    private Map<String, JSpinner> spnTeam1Stats = new LinkedHashMap<>();
    private Map<String, JSpinner> spnTeam2Stats = new LinkedHashMap<>();

    private JButton btnLuu;
    private JButton btnDong;

    private JPanel pnlTeam1, pnlTeam2;
    private TranDauPanel tranDauPanel;

    private String team1Name = "Đội nhà";
    private String team2Name = "Đội khách";
    private TrongTaiDAO trongTaiDAO;

    public ChiTietTranDauDialog(Frame owner, TranDauPanel tranDauPanel) {
        super(owner, "Chi tiết trận đấu", true);
        this.tranDauPanel = tranDauPanel;
        initComponents();
        setSize(700, 600);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        trongTaiDAO = new TrongTaiDAO();
        setLayout(new BorderLayout(10, 10));

        JPanel pnlTime = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTime.add(new JLabel("Ngày thi đấu:"));
        datePicker = new DatePicker();
        pnlTime.add(datePicker);

        pnlTime.add(new JLabel("Giờ:"));
        cbHour = new JComboBox<>();
        for (int i = 0; i <= 23; i++) {
            cbHour.addItem(i);
        }
        pnlTime.add(cbHour);

        pnlTime.add(new JLabel("Phút:"));
        cbMinute = new JComboBox<>();
        for (int i = 0; i <= 59; i++) {
            cbMinute.addItem(i);
        }
        pnlTime.add(cbMinute);

        pnlTime.add(new JLabel("Trọng tài:"));
        cbTrongTai = new JComboBox<>();
        List<TrongTai> dsTrongTai = trongTaiDAO.findAll();
        for (TrongTai tt : dsTrongTai) {
            cbTrongTai.addItem(tt);
        }
        pnlTime.add(cbTrongTai);

        add(pnlTime, BorderLayout.NORTH);

        String[] labels = {
            "Số lần sút", "Số bàn thắng", "Sút trúng đích", "Kiểm soát bóng (%)",
            "Lượt chuyền bóng", "Chuyền chính xác", "Phạm lỗi", "Việt vị", "Phạt góc"
        };

        JPanel pnlTeams = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlTeam1 = createTeamStatsPanel(team1Name, spnTeam1Stats, labels);
        pnlTeam2 = createTeamStatsPanel(team2Name, spnTeam2Stats, labels);
        pnlTeams.add(pnlTeam1);
        pnlTeams.add(pnlTeam2);

        add(pnlTeams, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel();
        btnLuu = new JButton("Lưu");
        btnDong = new JButton("Đóng");
        pnlButtons.add(btnLuu);
        pnlButtons.add(btnDong);

        add(pnlButtons, BorderLayout.SOUTH);

        btnDong.addActionListener(e -> dispose());
    }

    private JPanel createTeamStatsPanel(String title, Map<String, JSpinner> map, String[] labels) {
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setBorder(BorderFactory.createTitledBorder(title));

        for (String label : labels) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel lbl = new JLabel(label + ":");
            lbl.setPreferredSize(new Dimension(130, 20));
            JSpinner spn = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
            spn.setPreferredSize(new Dimension(80, 25));
            row.add(lbl);
            row.add(spn);
            pnl.add(row);
            map.put(label, spn);
        }

        return pnl;
    }

    public void setTeamNames(String team1, String team2) {
        this.team1Name = team1;
        this.team2Name = team2;

        if (pnlTeam1 != null) {
            pnlTeam1.setBorder(BorderFactory.createTitledBorder(team1));
            pnlTeam1.revalidate();
            pnlTeam1.repaint();
        }
        if (pnlTeam2 != null) {
            pnlTeam2.setBorder(BorderFactory.createTitledBorder(team2));
            pnlTeam2.revalidate();
            pnlTeam2.repaint();
        }

        System.out.println("DEBUG: Set team names to: " + team1 + " - " + team2);
    }

    public void loadTranDau(TranDau td) {
        if (td.getThoiGian() != null) {
            LocalDate date = td.getThoiGian().toLocalDate();
            datePicker.setDate(date);
            cbHour.setSelectedItem(td.getThoiGian().getHour());
            cbMinute.setSelectedItem(td.getThoiGian().getMinute());
        }

        if (td.getTeam1() != null && td.getTeam2() != null) {
            setTeamNames(td.getTeam1().getTenDoi(), td.getTeam2().getTenDoi());
        } else {
        }

        for (DoiBong_TranDau dbtd : td.getDoiBongTranDauList()) {
            Map<String, JSpinner> map = dbtd.getLaChuNha() == 1 ? spnTeam1Stats : spnTeam2Stats;

            map.get("Số lần sút").setValue(dbtd.getSoLanSut());
            map.get("Số bàn thắng").setValue(dbtd.getSoBanThang());
            map.get("Sút trúng đích").setValue(dbtd.getSutTrungDich());
            map.get("Kiểm soát bóng (%)").setValue(dbtd.getKiemSoatBong());
            map.get("Lượt chuyền bóng").setValue(dbtd.getLuotChuyenBong());
            map.get("Chuyền chính xác").setValue(dbtd.getChuyenChinhXac());
            map.get("Phạm lỗi").setValue(dbtd.getPhamLoi());
            map.get("Việt vị").setValue(dbtd.getVietVi());
            map.get("Phạt góc").setValue(dbtd.getPhatGoc());
        }
    }

    public void setSaveAction(TranDau tranDau) {
        btnLuu.addActionListener(e -> {
            try {
                int hour = (Integer) cbHour.getSelectedItem();
                int minute = (Integer) cbMinute.getSelectedItem();

                if (datePicker.getDate() == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày trận đấu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                LocalDate selectedDate = datePicker.getDate();
                LocalDate today = LocalDate.now();

                // Kiểm tra ngày chọn có nhỏ hơn ngày hiện tại không
                if (selectedDate.isBefore(today)) {
                    JOptionPane.showMessageDialog(this, "Ngày thi đấu không được nhỏ hơn ngày hiện tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                tranDau.setThoiGian(selectedDate.atTime(hour, minute));

                TrongTai selectedReferee = (TrongTai) cbTrongTai.getSelectedItem();
                tranDau.setTrongTai(selectedReferee);

                TranDauDAO tranDauDAO = new TranDauDAO();
                DoiBong_TranDauDAO dbtdDAO = new DoiBong_TranDauDAO();

                boolean success = tranDauDAO.update(tranDau);
                if (!success) {
                    JOptionPane.showMessageDialog(this, "Lỗi lưu thời gian trận đấu", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for (DoiBong_TranDau dbtd : tranDau.getDoiBongTranDauList()) {
                    Map<String, JSpinner> map = dbtd.getLaChuNha() == 1 ? spnTeam1Stats : spnTeam2Stats;

                    dbtd.setSoLanSut((Integer) map.get("Số lần sút").getValue());
                    dbtd.setSoBanThang((Integer) map.get("Số bàn thắng").getValue());
                    dbtd.setSutTrungDich((Integer) map.get("Sút trúng đích").getValue());
                    dbtd.setKiemSoatBong((Integer) map.get("Kiểm soát bóng (%)").getValue());
                    dbtd.setLuotChuyenBong((Integer) map.get("Lượt chuyền bóng").getValue());
                    dbtd.setChuyenChinhXac((Integer) map.get("Chuyền chính xác").getValue());
                    dbtd.setPhamLoi((Integer) map.get("Phạm lỗi").getValue());
                    dbtd.setVietVi((Integer) map.get("Việt vị").getValue());
                    dbtd.setPhatGoc((Integer) map.get("Phạt góc").getValue());

                    boolean dbtdSuccess = dbtdDAO.update(dbtd);
                    if (!dbtdSuccess) {
                        JOptionPane.showMessageDialog(this, "Lỗi lưu thông số đội bóng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                JOptionPane.showMessageDialog(this, "Lưu dữ liệu thành công!");
                if (tranDauPanel != null) {
                    tranDauPanel.loadTranDau(-1);
                }
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
