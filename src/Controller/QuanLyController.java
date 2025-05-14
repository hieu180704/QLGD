package Controller;

import View.Admin.QuanLyView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyController implements ActionListener{
    private QuanLyView QuanLyView;

    public QuanLyController(QuanLyView QuanLyView) {
        this.QuanLyView = QuanLyView;
    }  

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        if (button.equals("Trang Chủ")){
            QuanLyView.openTrangChu();
        }else if(button.equals("Quản Lý Giải Đấu")){
            QuanLyView.openQuanLyGiaiDau();
        }
    }
    
}
