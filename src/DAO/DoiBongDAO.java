package DAO;

import Model.DoiBong;
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
        String sql = "SELECT d.*, q.tenQuocGia, s.tenSVD " +
                     "FROM doibong d " +
                     "LEFT JOIN QuocGia q ON d.maQuocGia = q.maQuocGia " +
                     "LEFT JOIN SanVanDong s ON d.maSVD = s.maSVD " +
                     "WHERE d.maDoiBong = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapWithDetails(rs);
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
        String sql = "SELECT d.*, q.tenQuocGia, s.tenSVD " +
                     "FROM doibong d " +
                     "LEFT JOIN QuocGia q ON d.maQuocGia = q.maQuocGia " +
                     "LEFT JOIN SanVanDong s ON d.maSVD = s.maSVD";
        try (Connection conn = ConnectDB.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapWithDetails(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private DoiBong mapWithDetails(ResultSet rs) throws SQLException {
        DoiBong d = new DoiBong();
        d.setMaDoiBong(rs.getInt("maDoiBong"));
        d.setTenDoi(rs.getString("tenDoi"));
        d.setLogoDoi(rs.getBytes("logoDoi"));

        int maSVD = rs.getInt("maSVD");
        if (!rs.wasNull()) {
            SanVanDong svd = new SanVanDong();
            svd.setMaSVD(maSVD);
            svd.setTenSVD(rs.getString("tenSVD"));
            d.setSanVanDong(svd);
        }

        int maQuocGia = rs.getInt("maQuocGia");
        if (!rs.wasNull()) {
            QuocGia qg = new QuocGia();
            qg.setMaQuocGia(maQuocGia);
            qg.setTenQuocGia(rs.getString("tenQuocGia"));
            d.setQuocGia(qg);
        }

        return d;
    }

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

        int maSVD = rs.getInt("maSVD");
        if (!rs.wasNull()) {
            SanVanDong svd = new SanVanDong();
            svd.setMaSVD(maSVD);
            // Giả sử bạn có phương thức để lấy tenSVD từ maSVD
            svd.setTenSVD(getTenSanVanDongFromDB(maSVD)); // Cần triển khai phương thức này
            d.setSanVanDong(svd);
        }

        int maQuocGia = rs.getInt("maQuocGia");
        if (!rs.wasNull()) {
            QuocGia qg = new QuocGia();
            qg.setMaQuocGia(maQuocGia);
            // Giả sử bạn có phương thức để lấy tenQuocGia từ maQuocGia
            qg.setTenQuocGia(getTenQuocGiaFromDB(maQuocGia)); // Cần triển khai phương thức này
            d.setQuocGia(qg);
        }

        return d;
    }

    private String getTenSanVanDongFromDB(int maSVD) throws SQLException {
        String sql = "SELECT tenSVD FROM SanVanDong WHERE maSVD = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSVD);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tenSVD");
                }
            }
        }
        return null;
    }

    private String getTenQuocGiaFromDB(int maQuocGia) throws SQLException {
        String sql = "SELECT tenQuocGia FROM QuocGia WHERE maQuocGia = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maQuocGia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("tenQuocGia");
                }
            }
        }
        return null;
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
