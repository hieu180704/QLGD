package Controller.trandaucontroller;

import DAO.GiaiDauDAO;
import Model.GiaiDau;
import View.Admin.TranDauPanel;

import javax.swing.*;
import java.util.List;

public class TrauDauController {

    private TranDauPanel panel;
    private GiaiDauDAO giaiDauDAO;

    public TrauDauController(TranDauPanel panel) {
        this.panel = panel;
        this.giaiDauDAO = new GiaiDauDAO();
    }
}
