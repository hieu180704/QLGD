package Service;

import DAO.NhaTaiTroDAO;
import Model.NhaTaiTro;
import java.util.List;
import java.util.stream.Collectors;

public class NhaTaiTroService {
    private NhaTaiTroDAO dao;

    public NhaTaiTroService() {
        this.dao = new NhaTaiTroDAO();
    }

    // Lấy tất cả nhà tài trợ
    public List<NhaTaiTro> getAllNhaTaiTro() {
        return dao.findAll();
    }

    // Thêm nhà tài trợ mới
    public String addNhaTaiTro(NhaTaiTro ntt) {
        // Kiểm tra đầu vào
        if (ntt.getTenNTT() == null || ntt.getTenNTT().trim().isEmpty()) {
            return "Tên nhà tài trợ không được để trống!";
        }
        if (ntt.getEmail() == null || ntt.getEmail().trim().isEmpty()) {
            return "Email không được để trống!";
        }

        // Gọi DAO để thêm
        boolean success = dao.insert(ntt);
        if (success) {
            return "Thêm nhà tài trợ thành công!";
        } else {
            return "Thêm nhà tài trợ thất bại.";
        }
    }

    // Cập nhật nhà tài trợ
    public String updateNhaTaiTro(NhaTaiTro ntt) {
        // Kiểm tra đầu vào
        if (ntt.getTenNTT() == null || ntt.getTenNTT().trim().isEmpty()) {
            return "Tên nhà tài trợ không được để trống!";
        }
        if (ntt.getEmail() == null || ntt.getEmail().trim().isEmpty()) {
            return "Email không được để trống!";
        }

        // Gọi DAO để cập nhật
        boolean success = dao.update(ntt);
        if (success) {
            return "Cập nhật nhà tài trợ thành công!";
        } else {
            return "Cập nhật nhà tài trợ thất bại.";
        }
    }

    // Xóa nhà tài trợ
    public String deleteNhaTaiTro(int maNTT) {
        boolean success = dao.delete(maNTT);
        if (success) {
            return "Xóa nhà tài trợ thành công!";
        } else {
            return "Xóa nhà tài trợ thất bại.";
        }
    }

    // Tìm kiếm nhà tài trợ theo từ khóa
    public List<NhaTaiTro> searchNhaTaiTro(String keyword) {
        List<NhaTaiTro> list = dao.findAll();
        if (keyword == null || keyword.trim().isEmpty()) {
            return list;
        }
        return list.stream()
                .filter(ntt -> ntt.getTenNTT().toLowerCase().contains(keyword.toLowerCase())
                        || ntt.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Tìm nhà tài trợ theo mã
    public NhaTaiTro findById(int maNTT) {
        return dao.findById(maNTT);
    }
}