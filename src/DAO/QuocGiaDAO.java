package DAO;

import Model.QuocGia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuocGiaDAO {
    public List<QuocGia> findAll() {
        List<QuocGia> list = new ArrayList<QuocGia>();
        String sql = "SELECT * FROM quocgia";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                QuocGia qg = new QuocGia(rs.getInt("maQuocGia"), rs.getString("tenQuocGia"));
                list.add(qg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(stmt != null) stmt.close(); } catch(Exception e) {}
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
        return list;
    }
}
