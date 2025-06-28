package DAO;

import Model.HLV;
import java.sql.*;
import java.util.*;

public class HLVDAO implements GenericDAO<HLV> {

    @Override
    public boolean insert(HLV hlv) {
        String sql = "INSERT INTO huanluyenvien (tenHLV, anhHLV, ngaySinh, maQuocGia, maDoi) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, hlv.getTenHLV());
            ps.setBytes(2, hlv.getAnhHLV());
            ps.setDate(3, hlv.getNgaySinh() != null ? new java.sql.Date(hlv.getNgaySinh().getTime()) : null);
            ps.setInt(4, hlv.getMaQuocGia());
            ps.setInt(5, hlv.getMaDoi());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(HLV hlv) {
        String sql = "UPDATE huanluyenvien SET tenHLV = ?, anhHLV = ?, ngaySinh = ?, maQuocGia = ?, maDoi = ? WHERE maHLV = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hlv.getTenHLV());
            ps.setBytes(2, hlv.getAnhHLV());
            ps.setDate(3, hlv.getNgaySinh() != null ? new java.sql.Date(hlv.getNgaySinh().getTime()) : null);
            ps.setInt(4, hlv.getMaQuocGia());
            ps.setInt(5, hlv.getMaDoi());
            ps.setInt(6, hlv.getMaHLV());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM huanluyenvien WHERE maHLV = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public HLV findById(int id) {
        String sql = "SELECT * FROM huanluyenvien WHERE maHLV = ?";
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HLV> findAll() {
        List<HLV> list = new ArrayList<>();
        String sql = "SELECT h.maHLV, h.tenHLV, h.anhHLV, h.ngaySinh, h.maQuocGia, h.maDoi, q.tenQuocGia, d.tenDoi " +
                     "FROM huanluyenvien h " +
                     "JOIN quocgia q ON h.maQuocGia = q.maQuocGia " +
                     "JOIN doibong d ON h.maDoi = d.maDoiBong";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HLV hlv = new HLV();
                hlv.setMaHLV(rs.getInt("maHLV"));
                hlv.setTenHLV(rs.getString("tenHLV"));
                hlv.setAnhHLV(rs.getBytes("anhHLV"));
                hlv.setNgaySinh(rs.getDate("ngaySinh"));
                hlv.setMaQuocGia(rs.getInt("maQuocGia"));
                hlv.setMaDoi(rs.getInt("maDoi"));
                hlv.setTenQuocGia(rs.getString("tenQuocGia"));
                hlv.setTenDoi(rs.getString("tenDoi"));
                list.add(hlv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private HLV mapResultSet(ResultSet rs) throws SQLException {
        HLV hlv = new HLV();
        hlv.setMaHLV(rs.getInt("maHLV"));
        hlv.setTenHLV(rs.getString("tenHLV"));
        hlv.setAnhHLV(rs.getBytes("anhHLV"));
        hlv.setNgaySinh(rs.getDate("ngaySinh"));
        hlv.setMaQuocGia(rs.getInt("maQuocGia"));
        hlv.setMaDoi(rs.getInt("maDoi"));
        return hlv;
    }
}