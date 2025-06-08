package DAO;

import Model.SoDo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SoDoDAO implements GenericDAO<SoDo> {
@Override
    public boolean insert(SoDo obj) {
        String sql = "INSERT INTO sodo (tenSoDo, loaiSoDo, chienThuat, maDoiBong) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, obj.getTenSoDo());
            ps.setString(2, obj.getLoaiSoDo());
            ps.setString(3, obj.getChienThuat());
            ps.setInt(4, obj.getMaDoiBong()); // Thêm giá trị cho maDoiBong
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setMaSoDo(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(SoDo obj) {
        String sql = "UPDATE sodo SET tenSoDo = ?, loaiSoDo = ?, chienThuat = ?, maDoiBong = ? WHERE maSoDo = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, obj.getTenSoDo());
            ps.setString(2, obj.getLoaiSoDo());
            ps.setString(3, obj.getChienThuat());
            ps.setInt(4, obj.getMaDoiBong());
            ps.setInt(5, obj.getMaSoDo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM sodo WHERE maSoDo = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public SoDo findById(int id) {
        String sql = "SELECT * FROM sodo WHERE maSoDo = ?";
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
    public List<SoDo> findAll() {
        List<SoDo> list = new ArrayList<>();
        String sql = "SELECT * FROM sodo";
        try (Connection conn = ConnectDB.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private SoDo map(ResultSet rs) throws SQLException {
        SoDo soDo = new SoDo();
        soDo.setMaSoDo(rs.getInt("maSoDo"));
        soDo.setTenSoDo(rs.getString("tenSoDo"));
        soDo.setLoaiSoDo(rs.getString("loaiSoDo"));
        soDo.setChienThuat(rs.getString("chienThuat"));
        soDo.setMaDoiBong(rs.getInt("maDoiBong"));
        return soDo;
    }
}