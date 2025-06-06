package Controller;

import DAO.GiaiDauDAO;
import Model.GiaiDau;
import View.Admin.TranDau.TranDauPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.List;

public class TrauDauController implements ActionListener{

    private TranDauPanel panel;
    private GiaiDauDAO giaiDauDAO;

    public TrauDauController(TranDauPanel panel) {
        this.panel = panel;
        this.giaiDauDAO = new GiaiDauDAO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
