package DAO;

import Model.GiaiDau;
import Model.TheThuc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaiDauDAO implements GenericDAO<GiaiDau> {

    @Override
    public boolean insert(GiaiDau obj) {
        String sql = "INSERT INTO giaidau (tenGiaiDau, anhGiaiDau, maTheThuc, ngayTaoGiai, ngayBatDau, ngayKetThuc) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, obj.getTenGiaiDau());
            ps.setBytes(2, obj.getAnhGiaiDau());
            ps.setInt(3, obj.getTheThuc().getMaTheThuc());
            ps.setDate(4, obj.getNgayTaoGiai() != null ? Date.valueOf(obj.getNgayTaoGiai()) : null);
            ps.setDate(5, obj.getNgayBatDau() != null ? Date.valueOf(obj.getNgayBatDau()) : null);
            ps.setDate(6, obj.getNgayKetThuc() != null ? Date.valueOf(obj.getNgayKetThuc()) : null);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setMaGiaiDau(generatedKeys.getInt(1));
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(GiaiDau obj) {
        String sql = "UPDATE giaidau SET tenGiaiDau = ?, anhGiaiDau = ?, maTheThuc = ?, ngayTaoGiai = ?, ngayBatDau = ?, ngayKetThuc = ? WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getTenGiaiDau());
            ps.setBytes(2, obj.getAnhGiaiDau());
            ps.setInt(3, obj.getTheThuc().getMaTheThuc());
            ps.setDate(4, obj.getNgayTaoGiai() != null ? Date.valueOf(obj.getNgayTaoGiai()) : null);
            ps.setDate(5, obj.getNgayBatDau() != null ? Date.valueOf(obj.getNgayBatDau()) : null);
            ps.setDate(6, obj.getNgayKetThuc() != null ? Date.valueOf(obj.getNgayKetThuc()) : null);
            ps.setInt(7, obj.getMaGiaiDau());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM giaidau WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public GiaiDau findById(int id) {
        String sql = "SELECT * FROM giaidau WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                GiaiDau gd = map(rs);

                TheThucDAO theThucDAO = new TheThucDAO();
                gd.setTheThuc(theThucDAO.findById(gd.getTheThuc().getMaTheThuc()));

                return gd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<GiaiDau> findAll() {
        List<GiaiDau> list = new ArrayList<>();
        String sql = "SELECT * FROM giaidau";

        TheThucDAO theThucDAO = new TheThucDAO();

        try (Connection conn = ConnectDB.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                GiaiDau gd = map(rs);

                gd.setTheThuc(theThucDAO.findById(gd.getTheThuc().getMaTheThuc()));

                list.add(gd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private GiaiDau map(ResultSet rs) throws SQLException {
        GiaiDau gd = new GiaiDau();

        gd.setMaGiaiDau(rs.getInt("maGiaiDau"));
        gd.setTenGiaiDau(rs.getString("tenGiaiDau"));
        gd.setAnhGiaiDau(rs.getBytes("anhGiaiDau"));

        int maTheThuc = rs.getInt("maTheThuc");
        TheThuc theThuc = new TheThuc();
        theThuc.setMaTheThuc(maTheThuc);
        gd.setTheThuc(theThuc);

        Date ngayTaoGiaiSql = rs.getDate("ngayTaoGiai");
        Date ngayBatDauSql = rs.getDate("ngayBatDau");
        Date ngayKetThucSql = rs.getDate("ngayKetThuc");

        gd.setNgayTaoGiai(ngayTaoGiaiSql.toLocalDate());
        gd.setNgayBatDau(ngayBatDauSql.toLocalDate());
        gd.setNgayKetThuc(ngayKetThucSql.toLocalDate());

        return gd;
    }

    public List<GiaiDau> findByKeyword(String keyword) {
        List<GiaiDau> list = new ArrayList<>();
        String sql = "SELECT * FROM giaidau WHERE LOWER(tenGiaiDau) LIKE ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            TheThucDAO theThucDAO = new TheThucDAO();

            while (rs.next()) {
                GiaiDau gd = map(rs);
                // load thông tin TheThuc
                gd.setTheThuc(theThucDAO.findById(gd.getTheThuc().getMaTheThuc()));
                list.add(gd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<GiaiDau> findGiaiDauChuaCoTranDau() {
        List<GiaiDau> list = new ArrayList<>();
        String sql = "SELECT * FROM giaidau gd WHERE NOT EXISTS (SELECT 1 FROM trandau td WHERE td.maGiaiDau = gd.maGiaiDau)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<GiaiDau> findGiaiDauCoTranDau() {
        List<GiaiDau> list = new ArrayList<>();
        String sql = "SELECT * FROM giaidau gd WHERE EXISTS (SELECT 1 FROM trandau td WHERE td.maGiaiDau = gd.maGiaiDau)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
