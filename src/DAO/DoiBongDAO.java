package DAO;

import Model.DoiBong;
import Model.GiaiDau;
import Model.QuocGia;
import Model.SanVanDong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoiBongDAO implements GenericDAO<DoiBong> {

    @Override
    public boolean insert(DoiBong doiBong) {
        String sql = "INSERT INTO doibong (tenDoi, logoDoi, maSVD, maQuocGia) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doiBong.getTenDoi()); 
            ps.setBytes(2, doiBong.getLogoDoi());    

            if (doiBong.getSanVanDong() != null) {
                ps.setInt(3, doiBong.getSanVanDong().getMaSVD());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (doiBong.getQuocGia() != null) {
                ps.setInt(4, doiBong.getQuocGia().getMaQuocGia());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  
        }
    }

    @Override
    public boolean update(DoiBong obj) {
        String sql = "UPDATE doibong SET tenDoi = ?, logoDoi = ?, maSVD = ?, maQuocGia = ? WHERE maDoiBong = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getTenDoi());
            ps.setBytes(2, obj.getLogoDoi());

            if (obj.getSanVanDong() != null) {
                ps.setInt(3, obj.getSanVanDong().getMaSVD());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (obj.getQuocGia() != null) {
                ps.setInt(4, obj.getQuocGia().getMaQuocGia());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setInt(5, obj.getMaDoiBong());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM doibong WHERE maDoiBong = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public DoiBong findById(int id) {
        String sql = "SELECT * FROM doibong WHERE maDoiBong = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DoiBong> findAll() {
        List<DoiBong> list = new ArrayList<>();
        String sql = "SELECT * FROM doibong";
        try (Connection conn = ConnectDB.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- Các hàm mở rộng ---
    public List<DoiBong> findByMaGiaiDau(Integer maGiaiDau) {
        List<DoiBong> list = new ArrayList<>();
        String sql;
        if (maGiaiDau == null) {
            sql = "SELECT * FROM doibong WHERE maGiaiDau IS NULL";
        } else {
            sql = "SELECT * FROM doibong WHERE maGiaiDau = ?";
        }

        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (maGiaiDau != null) {
                ps.setInt(1, maGiaiDau);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateMaGiaiDau(int maDoiBong, Integer maGiaiDau) {
        String sql = "UPDATE doibong SET maGiaiDau = ? WHERE maDoiBong = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (maGiaiDau == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, maGiaiDau);
            }

            ps.setInt(2, maDoiBong);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private DoiBong map(ResultSet rs) throws SQLException {
        DoiBong d = new DoiBong();
        d.setMaDoiBong(rs.getInt("maDoiBong"));
        d.setTenDoi(rs.getString("tenDoi"));
        d.setLogoDoi(rs.getBytes("logoDoi"));

        // Map sanVanDong
        int maSVD = rs.getInt("maSVD");
        if (!rs.wasNull()) {
            SanVanDong svd = new SanVanDong();
            svd.setMaSVD(maSVD);
            d.setSanVanDong(svd);
        }

        // Map quocGia
        int maQuocGia = rs.getInt("maQuocGia");
        if (!rs.wasNull()) {
            QuocGia qg = new QuocGia();
            qg.setMaQuocGia(maQuocGia);
            d.setQuocGia(qg);
        }

        return d;
    }


    public boolean updateMaGiaiDauNull(int maGiaiDau) {
        String sql = "UPDATE doibong SET maGiaiDau = NULL WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maGiaiDau);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
