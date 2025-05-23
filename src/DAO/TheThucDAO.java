package DAO;

import Model.TheThuc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TheThucDAO implements GenericDAO<TheThuc> {

    @Override
    public boolean insert(TheThuc obj) {
        String sql = "INSERT INTO thethuc (tenTheThuc) VALUES (?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, obj.getTenTheThuc());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) return false;

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) obj.setMaTheThuc(generatedKeys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(TheThuc obj) {
        String sql = "UPDATE thethuc SET tenTheThuc = ? WHERE maTheThuc = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getTenTheThuc());
            ps.setInt(2, obj.getMaTheThuc());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM thethuc WHERE maTheThuc = ?";
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
    public TheThuc findById(int id) {
        String sql = "SELECT * FROM thethuc WHERE maTheThuc = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
    public List<TheThuc> findAll() {
        List<TheThuc> list = new ArrayList<>();
        String sql = "SELECT * FROM thethuc";
        try (Connection conn = ConnectDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private TheThuc map(ResultSet rs) throws SQLException {
        TheThuc tt = new TheThuc();
        tt.setMaTheThuc(rs.getInt("maTheThuc"));
        tt.setTenTheThuc(rs.getString("tenTheThuc"));
        return tt;
    }
}
