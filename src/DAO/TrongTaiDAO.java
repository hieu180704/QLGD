package DAO;

import Model.QuocGia;
import Model.TrongTai;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrongTaiDAO {
    public List<TrongTai> getAllTrongTai() {
        List<TrongTai> list = new ArrayList<>();
        String sql = "SELECT t.*, q.maQuocGia, q.tenQuocGia FROM trongtai t LEFT JOIN quocgia q ON t.maQuocGia = q.maQuocGia";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TrongTai tt = new TrongTai();
                tt.setMaTrongTai(rs.getInt("maTrongTai"));
                tt.setTenTrongTai(rs.getString("tenTrongTai"));
                tt.setNgaySinh(rs.getDate("ngaySinh"));
                QuocGia qg = new QuocGia();
                qg.setMaQuocGia(rs.getInt("maQuocGia"));
                qg.setTenQuocGia(rs.getString("tenQuocGia"));
                tt.setQuocGia(qg);
                list.add(tt);
            }
        } catch (Exception e) {
            System.out.println("Lỗi lấy danh sách trọng tài: " + e.getMessage());
        }
        return list;
    }

    public boolean addTrongTai(TrongTai tt) {
        String sql = "INSERT INTO trongtai (tenTrongTai, ngaySinh, maQuocGia) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tt.getTenTrongTai());
            stmt.setDate(2, new java.sql.Date(tt.getNgaySinh().getTime()));
            stmt.setInt(3, tt.getQuocGia().getMaQuocGia());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi thêm trọng tài: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTrongTai(TrongTai tt) {
        String sql = "UPDATE trongtai SET tenTrongTai = ?, ngaySinh = ?, maQuocGia = ? WHERE maTrongTai = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tt.getTenTrongTai());
            stmt.setDate(2, new java.sql.Date(tt.getNgaySinh().getTime()));
            stmt.setInt(3, tt.getQuocGia().getMaQuocGia());
            stmt.setInt(4, tt.getMaTrongTai());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi sửa trọng tài: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTrongTai(int maTrongTai) {
        String sql = "DELETE FROM trongtai WHERE maTrongTai = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maTrongTai);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi xóa trọng tài: " + e.getMessage());
            return false;
        }
    }

    public List<TrongTai> searchTrongTai(String keyword) {
        List<TrongTai> list = new ArrayList<>();
        String sql = "SELECT t.*, q.maQuocGia, q.tenQuocGia FROM trongtai t LEFT JOIN quocgia q ON t.maQuocGia = q.maQuocGia WHERE t.tenTrongTai LIKE ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TrongTai tt = new TrongTai();
                tt.setMaTrongTai(rs.getInt("maTrongTai"));
                tt.setTenTrongTai(rs.getString("tenTrongTai"));
                tt.setNgaySinh(rs.getDate("ngaySinh"));
                QuocGia qg = new QuocGia();
                qg.setMaQuocGia(rs.getInt("maQuocGia"));
                qg.setTenQuocGia(rs.getString("tenQuocGia"));
                tt.setQuocGia(qg);
                list.add(tt);
            }
        } catch (Exception e) {
            System.out.println("Lỗi tìm kiếm trọng tài: " + e.getMessage());
        }
        return list;
    }
    
    public List<TrongTai> findAll() {
        List<TrongTai> list = new ArrayList<>();
        String sql = "SELECT * FROM trongtai";

        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TrongTai tt = new TrongTai();
                tt.setMaTrongTai(rs.getInt("maTrongTai"));
                tt.setTenTrongTai(rs.getString("tenTrongTai"));
                list.add(tt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}