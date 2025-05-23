/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin.QuanLyTrongTai;

import Controller.trongtaicontroller.TrongTaiController;
import Model.QuocGia;
import Model.TrongTai;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;

public class FormThemSuaTrongTai extends JDialog {

    private JTextField txtTen;
    private JTextField txtNgaySinh;
    private JComboBox<QuocGia> cbQuocGia;
    private JButton btnLuu, btnHuy;

    private TrongTaiController controller;
    private TrongTai trongTaiSua;

    public FormThemSuaTrongTai(JFrame parent, TrongTaiController controller) {
        super(parent, true);
        this.controller = controller;

        setTitle("Thêm/Sửa Trọng tài");
        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Tên trọng tài:"));
        txtTen = new JTextField();
        panel.add(txtTen);

        panel.add(new JLabel("Ngày sinh (yyyy-mm-dd):"));
        txtNgaySinh = new JTextField();
        panel.add(txtNgaySinh);

        panel.add(new JLabel("Quốc gia:"));
        cbQuocGia = new JComboBox<QuocGia>();
        panel.add(cbQuocGia);

        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        JPanel panelButton = new JPanel();
        panelButton.add(btnLuu);
        panelButton.add(btnHuy);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(panelButton, BorderLayout.SOUTH);

        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                luuTrongTai();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void hienThiQuocGia(List<QuocGia> list) {
        cbQuocGia.removeAllItems();
        for (QuocGia qg : list) {
            cbQuocGia.addItem(qg);
        }
    }

    public void hienThiTrongTai(TrongTai tt) {
        this.trongTaiSua = tt;
        if (tt != null) {
            txtTen.setText(tt.getTenTrongTai());
            txtNgaySinh.setText(tt.getNgaySinh().toString());
            cbQuocGia.setSelectedItem(tt.getQuocGia());
        } else {
            txtTen.setText("");
            txtNgaySinh.setText("");
            if (cbQuocGia.getItemCount() > 0) {
                cbQuocGia.setSelectedIndex(0);
            }
        }
    }

    private void luuTrongTai() {
        String ten = txtTen.getText().trim();
        String ngaySinhStr = txtNgaySinh.getText().trim();
        QuocGia qg = (QuocGia) cbQuocGia.getSelectedItem();

        if (ten.isEmpty() || ngaySinhStr.isEmpty() || qg == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        Date ngaySinh;
        try {
            ngaySinh = Date.valueOf(ngaySinhStr);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng yyyy-mm-dd");
            return;
        }

        if (trongTaiSua == null) {
            controller.themTrongTai(new TrongTai(0, ten, ngaySinh, qg));
        } else {
            trongTaiSua.setTenTrongTai(ten);
            trongTaiSua.setNgaySinh(ngaySinh);
            trongTaiSua.setQuocGia(qg);
            controller.suaTrongTai(trongTaiSua);
        }
        dispose();
    }
}

