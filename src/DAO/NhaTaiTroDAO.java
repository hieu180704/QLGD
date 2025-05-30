package DAO;

import Model.NhaTaiTro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class NhaTaiTroDAO implements GenericDAO<NhaTaiTro> {

    @Override
    public boolean insert(NhaTaiTro obj) {
        String sql = "INSERT INTO nhataitro (tenNTT, logoNTT, email, soDienThoai, diaChi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, obj.getTenNTT());
            ps.setBytes(2, obj.getLogoNTT());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getSoDienThoai());
            ps.setString(5, obj.getDiaChi());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setMaNTT(generatedKeys.getInt(1));
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(NhaTaiTro obj) {
        String sql = "UPDATE nhataitro SET tenNTT = ?, logoNTT = ?, email = ?, soDienThoai = ?, diaChi = ? WHERE maNTT = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
        try (Connection conn = ConnectDB.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private NhaTaiTro map(ResultSet rs) throws SQLException {
        NhaTaiTro ntt = new NhaTaiTro();
        ntt.setMaNTT(rs.getInt("maNTT"));
        ntt.setTenNTT(rs.getString("tenNTT"));
        ntt.setLogoNTT(rs.getBytes("logoNTT"));
        ntt.setEmail(rs.getString("email"));
        ntt.setSoDienThoai(rs.getString("soDienThoai"));
        ntt.setDiaChi(rs.getString("diaChi"));
        return ntt;
    }

    public List<NhaTaiTro> getNhaTaiTroByGiaiDau(int maGiaiDau) {
        List<NhaTaiTro> list = new ArrayList<>();
        String sql = "SELECT nt.* FROM nhataitro nt "
                + "JOIN giaidau_nhataitro gnt ON nt.maNTT = gnt.maNTT "
                + "WHERE gnt.maGiaiDau = ?";

        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maGiaiDau);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NhaTaiTro ntt = new NhaTaiTro();
                ntt.setMaNTT(rs.getInt("maNTT"));
                ntt.setTenNTT(rs.getString("tenNTT"));
                ntt.setLogoNTT(rs.getBytes("logoNTT"));
                ntt.setEmail(rs.getString("email"));
                ntt.setSoDienThoai(rs.getString("soDienThoai"));
                ntt.setDiaChi(rs.getString("diaChi"));

                list.add(ntt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy logo nhà tài trợ từ database
    public ImageIcon getLogoFromDatabase(int maNTT) {
        String sql = "SELECT logoNTT FROM nhataitro WHERE maNTT = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNTT);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("logoNTT");
                if (imgBytes != null) {
                    // Chuyển byte array thành ImageIcon
                    ImageIcon imageIcon = new ImageIcon(imgBytes);
                    return imageIcon;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // trả về null nếu không tìm thấy logo
    }

}
