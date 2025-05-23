package DAO;

import java.sql.*;
import Model.UserModel;
import Model.UserAccountModel;

public class UserDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/quanlygiaidau?useSSL=false&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Kiểm tra username/password trong DB
    public UserAccountModel checkLogin(String username, String password) {
        String sql = "SELECT * FROM taikhoan WHERE tenDangNhap = ? AND matKhau = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserAccountModel user = new UserAccountModel();
                user.setUsername(rs.getString("tenDangNhap"));
                user.setPassword(rs.getString("matKhau"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // không tìm thấy user
    }
    
    public boolean updateUserInfo(String oldUsername, String newEmail, String newUsername) {
    String sql = "UPDATE taikhoan SET tenDangNhap = ?, email = ? WHERE tenDangNhap = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, newUsername);
        ps.setString(2, newEmail);
        ps.setString(3, oldUsername);
        int rowsUpdated = ps.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}
