package DAO;

import Model.CauThu;
import java.sql.*;
import java.util.*;

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
        String sql = "SELECT * FROM cauthu";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
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
}
