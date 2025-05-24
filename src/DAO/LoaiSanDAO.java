/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.LoaiSan;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ntnfa
 */
public class LoaiSanDAO {
    public List<LoaiSan> getAllLoaiSan() {
        List<LoaiSan> list = new ArrayList<LoaiSan>();
        String sql = "SELECT * FROM loaisan";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                LoaiSan ls = new LoaiSan(rs.getInt("maLoai"), rs.getString("tenLoai"));
                list.add(ls);
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
