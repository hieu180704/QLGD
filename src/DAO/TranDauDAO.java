package DAO;

import Model.TranDau;
import Model.GiaiDau;
import Model.TrongTai;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TranDauDAO implements GenericDAO<TranDau> {

    @Override
    public boolean insert(TranDau obj) {
        String sql = "INSERT INTO trandau (maTrongTai, thoiGian, maGiaiDau) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, obj.getTrongTai().getMaTrongTai());
            ps.setTimestamp(2, new Timestamp(obj.getThoiGian().getTime()));
            ps.setInt(3, obj.getGiaiDau().getMaGiaiDau());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setMaTranDau(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(TranDau obj) {
        String sql = "UPDATE trandau SET maTrongTai = ?, thoiGian = ?, maGiaiDau = ? WHERE maTranDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, obj.getTrongTai().getMaTrongTai());
            ps.setTimestamp(2, new Timestamp(obj.getThoiGian().getTime()));
            ps.setInt(3, obj.getGiaiDau().getMaGiaiDau());
            ps.setInt(4, obj.getMaTranDau());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM trandau WHERE maTranDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TranDau findById(int id) {
        String sql = "SELECT * FROM trandau WHERE maTranDau = ?";
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
    public List<TranDau> findAll() {
        List<TranDau> list = new ArrayList<>();
        String sql = "SELECT * FROM trandau";
        try (Connection conn = ConnectDB.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private TranDau map(ResultSet rs) throws SQLException {
        TranDau td = new TranDau();

        td.setMaTranDau(rs.getInt("maTranDau"));
        td.setThoiGian(rs.getTimestamp("thoiGian"));

        // Map GiaiDau (chỉ set maGiaiDau)
        GiaiDau gd = new GiaiDau();
        gd.setMaGiaiDau(rs.getInt("maGiaiDau"));
        td.setGiaiDau(gd);

        // Map TrongTai (chỉ set maTrongTai)
        TrongTai tt = new TrongTai();
        tt.setMaTrongTai(rs.getInt("maTrongTai"));
        td.setTrongTai(tt);

        return td;
    }

    public boolean hasMatchInGiaiDau(int maGiaiDau) {
        String sql = "SELECT COUNT(*) FROM trandau WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maGiaiDau);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
