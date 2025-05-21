package DAO;

import Model.TheThuc;
import java.sql.*;
import java.util.*;

public class TheThucDAO implements GenericDAO<TheThuc> {

    @Override
    public boolean insert(TheThuc obj) {
        String sql = "INSERT INTO thethuc (tenTheThuc) VALUES (?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getTenTheThuc());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
        }
        return false;
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
        }
        return false;
    }

    @Override
    public TheThuc findById(int id) {
        String sql = "SELECT * FROM thethuc WHERE maTheThuc = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TheThuc(rs.getInt("maTheThuc"), rs.getString("tenTheThuc"));
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
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new TheThuc(rs.getInt("maTheThuc"), rs.getString("tenTheThuc")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
