package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NhaTaiTro_GiaiDauDAO {

    public boolean deleteByMaGiaiDau(int maGiaiDau) {
        String sql = "DELETE FROM nhataitro_giaidau WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maGiaiDau);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
