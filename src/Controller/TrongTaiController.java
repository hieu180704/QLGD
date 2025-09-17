/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.QuocGia;
import Model.TrongTai;
import Service.TrongTaiService;
import View.Admin.QuanLyTrongTai.AddTrongTaiForm;
import View.Admin.QuanLyTrongTai.EditTrongTaiForm;
import View.Admin.QuanLyTrongTai.TrongTaiView;
import java.util.List;
import javax.swing.JOptionPane;

public class TrongTaiController {
    private TrongTaiView view;
    private TrongTaiService service;

    public TrongTaiController(TrongTaiView view) {
        this.view = view;
        this.service = new TrongTaiService();
    }

    public void loadData() {
        try {
            List<TrongTai> trongTais = service.getAllTrongTai();
            view.displayTrongTai(trongTais);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    public List<QuocGia> getAllQuocGia() {
        return service.getAllQuocGia();
    }

    public void addTrongTai() {
        AddTrongTaiForm form = new AddTrongTaiForm(this);
        form.setVisible(true);
    }

    public void saveNewTrongTai(TrongTai tt) {
        try {
            service.addTrongTai(tt);
            JOptionPane.showMessageDialog(view, "Thêm trọng tài thành công!");
            loadData();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi thêm trọng tài: " + e.getMessage());
        }
    }

    public void editTrongTai(TrongTai tt) {
        EditTrongTaiForm form = new EditTrongTaiForm(this, tt);
        form.setVisible(true);
    }

    public void saveEditedTrongTai(TrongTai tt) {
        try {
            service.updateTrongTai(tt);
            JOptionPane.showMessageDialog(view, "Sửa trọng tài thành công!");
            loadData();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi sửa trọng tài: " + e.getMessage());
        }
    }

    public void deleteTrongTai(int maTrongTai) {
        try {
            service.deleteTrongTai(maTrongTai);
            JOptionPane.showMessageDialog(view, "Xóa trọng tài thành công!");
            loadData();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi xóa trọng tài: " + e.getMessage());
        }
    }

    public void searchTrongTai(String keyword) {
        try {
            List<TrongTai> trongTais = service.searchTrongTai(keyword);
            view.displayTrongTai(trongTais);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    public List<TrongTai> getAllTrongTai() {
        return service.getAllTrongTai();
    }
}