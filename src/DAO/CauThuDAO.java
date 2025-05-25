package DAO;

import Model.CauThu;
import java.sql.*;
import java.util.*;
import Model.CauThu;

public class CauThuDAO implements GenericDAO<CauThu> {

    @Override
    public boolean insert(CauThu ct) {
        String sql = "INSERT INTO cauthu (tenCauThu, anhCauThu, ngaySinh, maQuocGia, chieuCao, canNang, maDoi, soAo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ct.getTenCauThu());
            ps.setBytes(2, ct.getAnhCauThu());
            ps.setDate(3, new java.sql.Date(ct.getNgaySinh().getTime()));
            ps.setInt(4, ct.getMaQuocGia());
            ps.setInt(5, ct.getChieuCao());
            ps.setInt(6, ct.getCanNang());
            ps.setInt(7, ct.getMaDoi());
            ps.setInt(8, ct.getSoAo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(CauThu ct) {
        String sql = "UPDATE cauthu SET tenCauThu = ?, anhCauThu = ?, ngaySinh = ?, maQuocGia = ?, chieuCao = ?, canNang = ?, maDoi = ?, soAo = ? WHERE maCauThu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ct.getTenCauThu());
            ps.setBytes(2, ct.getAnhCauThu());
            ps.setDate(3, new java.sql.Date(ct.getNgaySinh().getTime()));
            ps.setInt(4, ct.getMaQuocGia());
            ps.setInt(5, ct.getChieuCao());
            ps.setInt(6, ct.getCanNang());
            ps.setInt(7, ct.getMaDoi());
            ps.setInt(8, ct.getSoAo());
            ps.setInt(9, ct.getMaCauThu());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM cauthu WHERE maCauThu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CauThu findById(int id) {
        String sql = "SELECT * FROM cauthu WHERE maCauThu = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CauThu> findAll() {
    List<CauThu> list = new ArrayList<>();
    String sql = "SELECT c.maCauThu, c.tenCauThu, c.anhCauThu, c.ngaySinh, c.maQuocGia, c.chieuCao, c.canNang, c.maDoi, c.soAo, " +
                 "q.tenQuocGia, d.tenDoi " +
                 "FROM cauthu c " +
                 "JOIN quocgia q ON c.maQuocGia = q.maQuocGia " +
                 "JOIN doibong d ON c.maDoi = d.maDoi";

    try (Connection conn = ConnectDB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            CauThu ct = new CauThu();
            ct.setMaCauThu(rs.getInt("maCauThu"));
            ct.setTenCauThu(rs.getString("tenCauThu"));
            ct.setAnhCauThu(rs.getBytes("anhCauThu"));
            ct.setNgaySinh(rs.getDate("ngaySinh"));
            ct.setMaQuocGia(rs.getInt("maQuocGia"));
            ct.setChieuCao(rs.getInt("chieuCao"));
            ct.setCanNang(rs.getInt("canNang"));
            ct.setMaDoi(rs.getInt("maDoi"));
            ct.setSoAo(rs.getInt("soAo"));

            // Gán tên quốc gia và tên đội
            ct.setTenQuocGia(rs.getString("tenQuocGia"));
            ct.setTenDoi(rs.getString("tenDoi"));

            list.add(ct);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}


    private CauThu mapResultSet(ResultSet rs) throws SQLException {
        CauThu ct = new CauThu();
        ct.setMaCauThu(rs.getInt("maCauThu"));
        ct.setTenCauThu(rs.getString("tenCauThu"));
        ct.setAnhCauThu(rs.getBytes("anhCauThu"));
        ct.setNgaySinh(rs.getDate("ngaySinh"));
        ct.setMaQuocGia(rs.getInt("maQuocGia"));
        ct.setChieuCao(rs.getInt("chieuCao"));
        ct.setCanNang(rs.getInt("canNang"));
        ct.setMaDoi(rs.getInt("maDoi"));
        ct.setSoAo(rs.getInt("soAo"));
        return ct;
    }
    
    public interface GenericDAO<T> {
    boolean insert(T obj);
    boolean update(T obj);
    boolean delete(int id);
    T findById(int id);
    List<T> findAll();
    }
}
