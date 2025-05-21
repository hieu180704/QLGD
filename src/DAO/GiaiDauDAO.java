package DAO;

import Model.GiaiDau;
import java.sql.*;
import java.util.*;

public class GiaiDauDAO implements GenericDAO<GiaiDau> {

    @Override
    public boolean insert(GiaiDau obj) {
        String sql = "INSERT INTO giaidau (tenGiaiDau, anhGiaiDau, maNTT, maTheThuc, ngayTaoGiai, ngayBatDau) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getTenGiaiDau());
            ps.setBytes(2, obj.getAnhGiaiDau());
            ps.setInt(3, obj.getMaNTT());
            ps.setInt(4, obj.getMaTheThuc());
            ps.setDate(5, new java.sql.Date(obj.getNgayTaoGiai().getTime()));
            ps.setDate(6, new java.sql.Date(obj.getNgayBatDau().getTime()));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(GiaiDau obj) {
        String sql = "UPDATE giaidau SET tenGiaiDau = ?, anhGiaiDau = ?, maNTT = ?, maTheThuc = ?, ngayTaoGiai = ?, ngayBatDau = ? WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getTenGiaiDau());
            ps.setBytes(2, obj.getAnhGiaiDau());
            ps.setInt(3, obj.getMaNTT());
            ps.setInt(4, obj.getMaTheThuc());
            ps.setDate(5, new java.sql.Date(obj.getNgayTaoGiai().getTime()));
            ps.setDate(6, new java.sql.Date(obj.getNgayBatDau().getTime()));
            ps.setInt(7, obj.getMaGiaiDau());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM giaidau WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public GiaiDau findById(int id) {
        String sql = "SELECT * FROM giaidau WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return map(rs);
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

        try (Connection conn = ConnectDB.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
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
        gd.setMaNTT(rs.getInt("maNTT"));
        gd.setMaTheThuc(rs.getInt("maTheThuc"));
        gd.setNgayTaoGiai(rs.getDate("ngayTaoGiai"));
        gd.setNgayBatDau(rs.getDate("ngayBatDau"));

        // Gắn đối tượng NhaTaiTro và TheThuc
        NhaTaiTroDAO nttDAO = new NhaTaiTroDAO();
        TheThucDAO theThucDAO = new TheThucDAO();

        gd.setNhaTaiTro(nttDAO.findById(gd.getMaNTT()));
        gd.setTheThuc(theThucDAO.findById(gd.getMaTheThuc()));

        return gd;
    }
}
