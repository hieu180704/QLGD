package DAO;

import Model.NhaTaiTro;
import java.sql.*;
import java.util.*;

public class NhaTaiTroDAO implements GenericDAO<NhaTaiTro> {

    @Override
    public boolean insert(NhaTaiTro obj) {
        String sql = "INSERT INTO nhataitro (tenNTT, logoNTT, email, soDienThoai, diaChi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getTenNTT());
            ps.setBytes(2, obj.getLogoNTT());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getSoDienThoai());
            ps.setString(5, obj.getDiaChi());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(NhaTaiTro obj) {
        String sql = "UPDATE nhataitro SET tenNTT = ?, logoNTT = ?, email = ?, soDienThoai = ?, diaChi = ? WHERE maNTT = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, obj.getTenNTT());
            ps.setBytes(2, obj.getLogoNTT());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getSoDienThoai());
            ps.setString(5, obj.getDiaChi());
            ps.setInt(6, obj.getMaNTT());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM nhataitro WHERE maNTT = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public NhaTaiTro findById(int id) {
        String sql = "SELECT * FROM nhataitro WHERE maNTT = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<NhaTaiTro> findAll() {
        List<NhaTaiTro> list = new ArrayList<>();
        String sql = "SELECT * FROM nhataitro";
        try (Connection conn = ConnectDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private NhaTaiTro map(ResultSet rs) throws SQLException {
        return new NhaTaiTro(
            rs.getInt("maNTT"),
            rs.getString("tenNTT"),
            rs.getBytes("logoNTT"),
            rs.getString("email"),
            rs.getString("soDienThoai"),
            rs.getString("diaChi")
        );
    }
}
