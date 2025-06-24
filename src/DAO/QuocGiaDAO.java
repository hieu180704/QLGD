package DAO;

import Model.QuocGia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuocGiaDAO {
    public List<QuocGia> getAllQuocGia() {
        List<QuocGia> list = new ArrayList<>();
        String sql = "SELECT * FROM quocgia";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                QuocGia qg = new QuocGia();
                qg.setMaQuocGia(rs.getInt("maQuocGia"));
                qg.setTenQuocGia(rs.getString("tenQuocGia"));
                list.add(qg);
            }
        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách quốc gia: " + e.getMessage());
        }
        return list;
    }
    
     public List<QuocGia> findAll() {
        List<QuocGia> list = new ArrayList<>();
        String sql = "SELECT FROM quocgia";
        try (Connection conn = ConnectDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                QuocGia qg = new QuocGia();
                qg.setMaQuocGia(rs.getInt("maQuocGia"));
                qg.setTenQuocGia(rs.getString("tenQuocGia"));
                list.add(qg);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
