package DAO;

import Model.DoiBong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoiBongDAO {

    public List<DoiBong> findAll() {
        List<DoiBong> list = new ArrayList<>();
        String sql = "SELECT maDoiBong, tenDoi FROM doibong";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DoiBong db = new DoiBong();
                db.setMaDoiBong(rs.getInt("maDoiBong"));
                db.setTenDoi(rs.getString("tenDoi"));
                list.add(db);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
