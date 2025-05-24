/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.QuocGia;
import Model.TrongTai;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrongTaiDAO {
    public List<TrongTai> getAllTrongTai() {
        List<TrongTai> list = new ArrayList<TrongTai>();
        String sql = "SELECT tt.maTrongTai, tt.tenTrongTai, tt.ngaySinh, tt.maQuocGia, qg.tenQuocGia " +
                     "FROM trongtai tt LEFT JOIN quocgia qg ON tt.maQuocGia = qg.maQuocGia";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                QuocGia qg = new QuocGia(rs.getInt("maQuocGia"), rs.getString("tenQuocGia"));
                TrongTai tt = new TrongTai(
                        rs.getInt("maTrongTai"),
                        rs.getString("tenTrongTai"),
                        rs.getDate("ngaySinh"),
                        qg
                );
                list.add(tt);
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

    public List<TrongTai> searchTrongTaiByName(String name) {
        List<TrongTai> list = new ArrayList<TrongTai>();
        String sql = "SELECT tt.maTrongTai, tt.tenTrongTai, tt.ngaySinh, tt.maQuocGia, qg.tenQuocGia " +
                     "FROM trongtai tt LEFT JOIN quocgia qg ON tt.maQuocGia = qg.maQuocGia " +
                     "WHERE tt.tenTrongTai LIKE ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                QuocGia qg = new QuocGia(rs.getInt("maQuocGia"), rs.getString("tenQuocGia"));
                TrongTai tt = new TrongTai(
                        rs.getInt("maTrongTai"),
                        rs.getString("tenTrongTai"),
                        rs.getDate("ngaySinh"),
                        qg
                );
                list.add(tt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(ps != null) ps.close(); } catch(Exception e) {}
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
        return list;
    }

    public boolean insertTrongTai(TrongTai tt) {
        String sql = "INSERT INTO trongtai (tenTrongTai, ngaySinh, maQuocGia) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, tt.getTenTrongTai());
            ps.setDate(2, tt.getNgaySinh());
            ps.setInt(3, tt.getQuocGia().getMaQuocGia());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if(ps != null) ps.close(); } catch(Exception e) {}
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
    }

    public boolean updateTrongTai(TrongTai tt) {
        String sql = "UPDATE trongtai SET tenTrongTai = ?, ngaySinh = ?, maQuocGia = ? WHERE maTrongTai = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, tt.getTenTrongTai());
            ps.setDate(2, tt.getNgaySinh());
            ps.setInt(3, tt.getQuocGia().getMaQuocGia());
            ps.setInt(4, tt.getMaTrongTai());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if(ps != null) ps.close(); } catch(Exception e) {}
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
    }

    public boolean deleteTrongTai(int maTrongTai) {
        String sql = "DELETE FROM trongtai WHERE maTrongTai = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maTrongTai);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if(ps != null) ps.close(); } catch(Exception e) {}
            try { if(conn != null) conn.close(); } catch(Exception e) {}
        }
    }
}

