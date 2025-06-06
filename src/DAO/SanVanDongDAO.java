package DAO;

import Model.LoaiSan;
import Model.SanVanDong;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SanVanDongDAO {
    public List<SanVanDong> getAllSanVanDong() {
        List<SanVanDong> list = new ArrayList<SanVanDong>();
        String sql = "SELECT svd.maSVD, svd.tenSVD, svd.sucChua, svd.diaChi, svd.maLoai, ls.tenLoai " +
                     "FROM sanvandong svd LEFT JOIN loaisan ls ON svd.maLoai = ls.maLoai";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                LoaiSan ls = new LoaiSan(rs.getInt("maLoai"), rs.getString("tenLoai"));
                SanVanDong svd = new SanVanDong(
                        rs.getInt("maSVD"),
                        rs.getString("tenSVD"),
                        rs.getInt("sucChua"),
                        rs.getString("diaChi"),
                        ls
                );
                list.add(svd);
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

    // Thêm phương thức findAll để lấy tất cả các sân vận động
    public List<SanVanDong> findAll() {
        List<SanVanDong> list = new ArrayList<SanVanDong>();
        String sql = "SELECT svd.maSVD, svd.tenSVD, svd.sucChua, svd.diaChi, svd.maLoai, ls.tenLoai " +
                     "FROM sanvandong svd LEFT JOIN loaisan ls ON svd.maLoai = ls.maLoai";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectDB.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                LoaiSan ls = new LoaiSan(rs.getInt("maLoai"), rs.getString("tenLoai"));
                SanVanDong svd = new SanVanDong(
                        rs.getInt("maSVD"),
                        rs.getString("tenSVD"),
                        rs.getInt("sucChua"),
                        rs.getString("diaChi"),
                        ls
                );
                list.add(svd);
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

    public List<SanVanDong> searchSanVanDongByName(String name) {
        List<SanVanDong> list = new ArrayList<SanVanDong>();
        String sql = "SELECT svd.maSVD, svd.tenSVD, svd.sucChua, svd.diaChi, svd.maLoai, ls.tenLoai " +
                     "FROM sanvandong svd LEFT JOIN loaisan ls ON svd.maLoai = ls.maLoai " +
                     "WHERE svd.tenSVD LIKE ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                LoaiSan ls = new LoaiSan(rs.getInt("maLoai"), rs.getString("tenLoai"));
                SanVanDong svd = new SanVanDong(
                        rs.getInt("maSVD"),
                        rs.getString("tenSVD"),
                        rs.getInt("sucChua"),
                        rs.getString("diaChi"),
                        ls
                );
                list.add(svd);
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

    public boolean insertSanVanDong(SanVanDong svd) {
        String sql = "INSERT INTO sanvandong (tenSVD, sucChua, diaChi, maLoai) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, svd.getTenSVD());
            ps.setInt(2, svd.getSucChua());
            ps.setString(3, svd.getDiaChi());
            ps.setInt(4, svd.getLoaiSan().getMaLoai());
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

    public boolean updateSanVanDong(SanVanDong svd) {
        String sql = "UPDATE sanvandong SET tenSVD = ?, sucChua = ?, diaChi = ?, maLoai = ? WHERE maSVD = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, svd.getTenSVD());
            ps.setInt(2, svd.getSucChua());
            ps.setString(3, svd.getDiaChi());
            ps.setInt(4, svd.getLoaiSan().getMaLoai());
            ps.setInt(5, svd.getMaSVD());
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

    public boolean deleteSanVanDong(int maSVD) {
        String sql = "DELETE FROM sanvandong WHERE maSVD = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectDB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maSVD);
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
