package Service;

import DAO.SoDoDAO;
import DAO.DoiBongDAO;
import Model.SoDo;
import java.util.ArrayList;
import java.util.List;

public class SoDoService {
    private SoDoDAO soDoDAO;
    private DoiBongDAO doiBongDAO;

    public SoDoService() {
        this.soDoDAO = new SoDoDAO();
        this.doiBongDAO = new DoiBongDAO();
    }

    public boolean addSoDoRaSan(SoDo soDo) {
        if (soDo == null || soDo.getTenSoDo() == null || soDo.getTenSoDo().trim().isEmpty() ||
            soDo.getLoaiSoDo() == null || soDo.getLoaiSoDo().trim().isEmpty() ||
            soDo.getChienThuat() == null || soDo.getChienThuat().trim().isEmpty() ||
            soDo.getMaDoiBong() <= 0) {
            return false;
        }

        if (soDo.getTenSoDo().length() > 50 || soDo.getLoaiSoDo().length() > 50 || soDo.getChienThuat().length() > 50) {
            return false;
        }

        // Kiểm tra maDoiBong có tồn tại trong bảng doibong
        if (doiBongDAO.findById(soDo.getMaDoiBong()) == null) {
            return false;
        }

        List<SoDo> existingSoDoList = soDoDAO.findAll();
        for (SoDo existingSoDo : existingSoDoList) {
            if (existingSoDo.getTenSoDo().equalsIgnoreCase(soDo.getTenSoDo()) &&
                existingSoDo.getMaDoiBong() == soDo.getMaDoiBong()) {
                return false; // Trùng lặp tên cho cùng đội bóng
            }
        }

        return soDoDAO.insert(soDo);
    }

    public boolean updateSoDoRaSan(SoDo soDo) {
        if (soDo == null || soDo.getMaSoDo() <= 0 || soDo.getTenSoDo() == null || soDo.getTenSoDo().trim().isEmpty() ||
            soDo.getLoaiSoDo() == null || soDo.getLoaiSoDo().trim().isEmpty() ||
            soDo.getChienThuat() == null || soDo.getChienThuat().trim().isEmpty() ||
            soDo.getMaDoiBong() <= 0) {
            return false;
        }

        if (soDo.getTenSoDo().length() > 50 || soDo.getLoaiSoDo().length() > 50 || soDo.getChienThuat().length() > 50) {
            return false;
        }

        // Kiểm tra maDoiBong có tồn tại trong bảng doibong
        if (doiBongDAO.findById(soDo.getMaDoiBong()) == null) {
            return false;
        }

        return soDoDAO.update(soDo);
    }

    public boolean deleteSoDoRaSan(int maSoDo) {
        if (maSoDo <= 0) {
            return false;
        }
        return soDoDAO.delete(maSoDo);
    }

    public SoDo getSoDoRaSanById(int maSoDo) {
        if (maSoDo <= 0) {
            return null;
        }
        return soDoDAO.findById(maSoDo);
    }

    public List<SoDo> getAllSoDoRaSan() {
        return soDoDAO.findAll();
    }

    public List<SoDo> findSoDoRaSanByName(String tenSoDo) {
        if (tenSoDo == null || tenSoDo.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<SoDo> allSoDo = soDoDAO.findAll();
        List<SoDo> result = new ArrayList<>();
        for (SoDo soDo : allSoDo) {
            if (soDo.getTenSoDo().toLowerCase().contains(tenSoDo.toLowerCase())) {
                result.add(soDo);
            }
        }
        return result;
    }
}