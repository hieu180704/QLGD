package DAO;

import Model.TaiKhoan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO implements GenericDAO<TaiKhoan>{

    // Thêm tài khoản mới
    public boolean insert(TaiKhoan tk) {
        String sql = "INSERT INTO taikhoan (tenDangNhap, matKhau, loaiTaiKhoan) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, tk.getTendangnhap());
            ps.setString(2, tk.getMatkhau());
            ps.setInt(3, tk.getLoaitaikhoan());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật tài khoản theo maTaiKhoan
    public boolean update(TaiKhoan tk) {
        String sql = "UPDATE taikhoan SET tenDangNhap = ?, matKhau = ?, loaiTaiKhoan = ? WHERE maTaiKhoan = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, tk.getTendangnhap());
            ps.setString(2, tk.getMatkhau());
            ps.setInt(3, tk.getLoaitaikhoan());
            ps.setInt(4, tk.getMataikhoan());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa tài khoản theo maTaiKhoan
    public boolean delete(int maTaiKhoan) {
        String sql = "DELETE FROM taikhoan WHERE maTaiKhoan = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, maTaiKhoan);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm tài khoản theo maTaiKhoan
    public TaiKhoan findById(int maTaiKhoan) {
        String sql = "SELECT * FROM taikhoan WHERE maTaiKhoan = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, maTaiKhoan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToTaiKhoan(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy tất cả tài khoản
    public List<TaiKhoan> findAll() {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM taikhoan";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while (rs.next()) {
                TaiKhoan tk = mapResultSetToTaiKhoan(rs);
                list.add(tk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Hàm tiện ích map ResultSet thành TaiKhoan
    private TaiKhoan mapResultSetToTaiKhoan(ResultSet rs) throws SQLException {
        TaiKhoan tk = new TaiKhoan();
        tk.setMataikhoan(rs.getInt("maTaiKhoan"));
        tk.setTendangnhap(rs.getString("tenDangNhap"));
        tk.setMatkhau(rs.getString("matKhau"));
        tk.setLoaitaikhoan(rs.getInt("loaiTaiKhoan"));
        return tk;
    }
}
