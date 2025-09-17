/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin.QuanLyTrongTai;
import Controller.TrongTaiController;
import Model.QuocGia;
import Model.TrongTai;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 *
 * @author ntnfa
 */
public class AddTrongTaiForm extends JDialog {
    private JTextField txtTenTrongTai;
    private JTextField txtNgaySinh;
    private JComboBox<QuocGia> cbQuocGia;
    private JButton btnSave, btnCancel;
    private TrongTaiController controller;

    public AddTrongTaiForm(TrongTaiController controller) {
        this.controller = controller;
        initComponents();
        loadQuocGia();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Thêm Trọng Tài");
        setSize(400, 300);
        setModal(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(245, 245, 245));

        JLabel lblTen = new JLabel("Tên Trọng Tài:");
        txtTenTrongTai = new JTextField();
        JLabel lblNgaySinh = new JLabel("Ngày Sinh (dd/MM/yyyy):");
        txtNgaySinh = new JTextField();
        JLabel lblQuocGia = new JLabel("Quốc Gia:");
        cbQuocGia = new JComboBox<>();

        inputPanel.add(lblTen);
        inputPanel.add(txtTenTrongTai);
        inputPanel.add(lblNgaySinh);
        inputPanel.add(txtNgaySinh);
        inputPanel.add(lblQuocGia);
        inputPanel.add(cbQuocGia);

        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(245, 245, 245));
        btnSave = new JButton("Lưu");
        btnCancel = new JButton("Hủy");
        btnSave.setBackground(new Color(50, 205, 50));
        btnCancel.setBackground(new Color(220, 20, 60));
        btnSave.setForeground(Color.WHITE);
        btnCancel.setForeground(Color.WHITE);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện nút
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTrongTai();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void loadQuocGia() {
        List<QuocGia> quocGias = controller.getAllQuocGia();
        for (QuocGia qg : quocGias) {
            cbQuocGia.addItem(qg);
        }
    }

    private void saveTrongTai() {
        try {
            TrongTai tt = new TrongTai();
            tt.setTenTrongTai(txtTenTrongTai.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date ngaySinh = sdf.parse(txtNgaySinh.getText());
            tt.setNgaySinh(ngaySinh);
            QuocGia selectedQuocGia = (QuocGia) cbQuocGia.getSelectedItem();
            if (selectedQuocGia != null) {
                tt.setQuocGia(selectedQuocGia);
            }
            controller.saveNewTrongTai(tt);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu: " + e.getMessage());
        }
    }
}