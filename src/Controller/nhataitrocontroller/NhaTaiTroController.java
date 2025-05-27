package Controller.nhataitrocontroller;

import DAO.NhaTaiTroDAO;
import Model.NhaTaiTro;
import java.util.List;

public class NhaTaiTroController {
    private NhaTaiTroDAO dao;

    public NhaTaiTroController() {
        dao = new NhaTaiTroDAO();
    }

    // Lấy tất cả nhà tài trợ
    public List<NhaTaiTro> getAll() {
        return dao.findAll();
    }

    // Thêm nhà tài trợ mới
    public boolean addNhaTaiTro(NhaTaiTro ntt) {
        return dao.insert(ntt);
    }

    // Cập nhật nhà tài trợ
    public boolean updateNhaTaiTro(NhaTaiTro ntt) {
        return dao.update(ntt);
    }

    // Xóa nhà tài trợ theo mã
    public boolean deleteNhaTaiTro(int maNTT) {
        return dao.delete(maNTT);
    }

    // Tìm theo mã (nếu cần)
    public NhaTaiTro findById(int maNTT) {
        return dao.findById(maNTT);
    }
    
    // Có thể thêm method tìm kiếm nâng cao nếu cần, hiện tại View lọc trong danh sách rồi
}
