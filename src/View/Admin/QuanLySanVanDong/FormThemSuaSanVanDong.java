/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Admin.QuanLySanVanDong;

import Controller.SanVanDongController;
import Model.LoaiSan;
import Model.SanVanDong;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 *
 * @author ntnfa
 */
public class FormThemSuaSanVanDong extends JDialog {
    private JTextField txtTen;
    private JTextField txtSucChua;
    private JTextField txtDiaChi;
    private JComboBox<LoaiSan> cbLoaiSan;
    private JButton btnLuu, btnHuy;

    private SanVanDongController controller;
    private SanVanDong sanVanDongSua;
    
    public FormThemSuaSanVanDong(JFrame parent, SanVanDongController controller) {
        super(parent, true);
        this.controller = controller;

        setTitle("Thêm/Sửa Sân vận động");
        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Tên sân vận động:"));
        txtTen = new JTextField();
        panel.add(txtTen);

        panel.add(new JLabel("Sức chứa:"));
        txtSucChua = new JTextField();
        panel.add(txtSucChua);
        
        panel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        panel.add(txtDiaChi);

        panel.add(new JLabel("Loại sân:"));
        cbLoaiSan = new JComboBox<LoaiSan>();
        panel.add(cbLoaiSan);

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
                luuSanVanDong();
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    public void hienThiLoaiSan(List<LoaiSan> list) {
        cbLoaiSan.removeAllItems();
        for (LoaiSan ls : list) {
            cbLoaiSan.addItem(ls);
        }
    }
    
    public void hienThiSanVanDong(SanVanDong svd) {
        this.sanVanDongSua = svd;
        if (svd != null) {
            txtTen.setText(svd.getTenSVD());
            txtSucChua.setText(String.valueOf(svd.getSucChua()));
            txtDiaChi.setText(svd.getDiaChi());
            cbLoaiSan.setSelectedItem(svd.getLoaiSan());
        } else {
            txtTen.setText("");
            txtSucChua.setText("");
            txtDiaChi.setText("");
            if (cbLoaiSan.getItemCount() > 0) {
                cbLoaiSan.setSelectedIndex(0);
            }
        }
    }
    
    private void luuSanVanDong() {
        String ten = txtTen.getText().trim();
        String sucChua = txtSucChua.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        LoaiSan ls = (LoaiSan) cbLoaiSan.getSelectedItem();

        if (ten.isEmpty() || sucChua.isEmpty() || diaChi.isEmpty() || ls == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }
        
        int sucChuaInt;
        try {
            sucChuaInt = Integer.parseInt(sucChua);
            if (sucChuaInt <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Sức chứa phải là số nguyên dương");
            return;
        }      


        if (sanVanDongSua == null) {
            controller.themSanVanDong(new SanVanDong(0, ten, sucChuaInt, diaChi, ls));
        } else {
            sanVanDongSua.setTenSVD(ten);
            sanVanDongSua.setSucChua(sucChuaInt);
            sanVanDongSua.setDiaChi(diaChi);
            sanVanDongSua.setLoaiSan(ls);
            controller.suaSanVanDong(sanVanDongSua);
        }
        dispose();
    }
}
