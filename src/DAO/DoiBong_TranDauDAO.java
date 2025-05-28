package DAO;

import Model.DoiBong_TranDau;
import Model.TranDau;
import Model.DoiBong;
import Model.SoDoRaSan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoiBong_TranDauDAO implements GenericDAO<DoiBong_TranDau> {

    @Override
    public boolean insert(DoiBong_TranDau obj) {
        String sql = "INSERT INTO doibong_trandau "
                + "(maTranDau, maDoiBong, laChuNha, soBanThang, soLanSut, sutTrungDich, kiemSoatBong, luotChuyenBong, chuyenChinhXac, phamLoi, vietVi, phatGoc) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, obj.getTranDau().getMaTranDau());
            ps.setInt(2, obj.getDoiBong().getMaDoiBong());
            ps.setInt(3, obj.getLaChuNha());
            ps.setInt(4, obj.getSoBanThang());
            ps.setInt(5, obj.getSoLanSut());
            ps.setInt(6, obj.getSutTrungDich());
            ps.setInt(7, obj.getKiemSoatBong());
            ps.setInt(8, obj.getLuotChuyenBong());
            ps.setInt(9, obj.getChuyenChinhXac());
            ps.setInt(10, obj.getPhamLoi());
            ps.setInt(11, obj.getVietVi());
            ps.setInt(12, obj.getPhatGoc());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(DoiBong_TranDau obj) {
        String sql = "UPDATE doibong_trandau SET "
                + "laChuNha = ?, soBanThang = ?, soLanSut = ?, sutTrungDich = ?, kiemSoatBong = ?, "
                + "luotChuyenBong = ?, chuyenChinhXac = ?, phamLoi = ?, vietVi = ?, phatGoc = ? "
                + "WHERE maTranDau = ? AND maDoiBong = ?";

        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, obj.getLaChuNha());
            ps.setInt(2, obj.getSoBanThang());
            ps.setInt(3, obj.getSoLanSut());
            ps.setInt(4, obj.getSutTrungDich());
            ps.setInt(5, obj.getKiemSoatBong());
            ps.setInt(6, obj.getLuotChuyenBong());
            ps.setInt(7, obj.getChuyenChinhXac());
            ps.setInt(8, obj.getPhamLoi());
            ps.setInt(9, obj.getVietVi());
            ps.setInt(10, obj.getPhatGoc());

            ps.setInt(11, obj.getTranDau().getMaTranDau());
            ps.setInt(12, obj.getDoiBong().getMaDoiBong());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        // Không hợp lý với khóa composite, nên không dùng
        throw new UnsupportedOperationException("Vui lòng dùng delete theo maTranDau và maDoiBong");
    }

    public boolean delete(int maTranDau, int maDoiBong) {
        String sql = "DELETE FROM doibong_trandau WHERE maTranDau = ? AND maDoiBong = ?";

        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTranDau);
            ps.setInt(2, maDoiBong);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public DoiBong_TranDau findById(int id) {
        // Không thể tìm bằng 1 id do khóa composite
        throw new UnsupportedOperationException("Phải dùng findById(maTranDau, maDoiBong)");
    }

    public DoiBong_TranDau findById(int maTranDau, int maDoiBong) {
        String sql = "SELECT * FROM doibong_trandau WHERE maTranDau = ? AND maDoiBong = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maTranDau);
            ps.setInt(2, maDoiBong);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DoiBong_TranDau obj = new DoiBong_TranDau();
                obj.setTranDau(new TranDau());
                obj.getTranDau().setMaTranDau(rs.getInt("maTranDau"));
                obj.setDoiBong(new DoiBong());
                obj.getDoiBong().setMaDoiBong(rs.getInt("maDoiBong"));
                obj.setLaChuNha(rs.getInt("laChuNha"));
                obj.setSoBanThang(rs.getInt("soBanThang"));
                obj.setSoLanSut(rs.getInt("soLanSut"));
                obj.setSutTrungDich(rs.getInt("sutTrungDich"));
                obj.setKiemSoatBong(rs.getInt("kiemSoatBong"));
                obj.setLuotChuyenBong(rs.getInt("luotChuyenBong"));
                obj.setChuyenChinhXac(rs.getInt("chuyenChinhXac"));
                obj.setPhamLoi(rs.getInt("phamLoi"));
                obj.setVietVi(rs.getInt("vietVi"));
                obj.setPhatGoc(rs.getInt("phatGoc"));
                return obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DoiBong_TranDau> findAll() {
        List<DoiBong_TranDau> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM doibong_trandau";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DoiBong_TranDau obj = new DoiBong_TranDau();
                obj.setTranDau(new TranDau());
                obj.getTranDau().setMaTranDau(rs.getInt("maTranDau"));
                obj.setDoiBong(new DoiBong());
                obj.getDoiBong().setMaDoiBong(rs.getInt("maDoiBong"));
                obj.setLaChuNha(rs.getInt("laChuNha"));
                obj.setSoBanThang(rs.getInt("soBanThang"));
                obj.setSoLanSut(rs.getInt("soLanSut"));
                obj.setSutTrungDich(rs.getInt("sutTrungDich"));
                obj.setKiemSoatBong(rs.getInt("kiemSoatBong"));
                obj.setLuotChuyenBong(rs.getInt("luotChuyenBong"));
                obj.setChuyenChinhXac(rs.getInt("chuyenChinhXac"));
                obj.setPhamLoi(rs.getInt("phamLoi"));
                obj.setVietVi(rs.getInt("vietVi"));
                obj.setPhatGoc(rs.getInt("phatGoc"));

                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DoiBong_TranDau> findByMaTranDau(int maTranDau) {
        List<DoiBong_TranDau> list = new ArrayList<>();
        String sql = "SELECT * FROM doibong_trandau WHERE maTranDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTranDau);
            ResultSet rs = ps.executeQuery();
            DoiBongDAO doiBongDAO = new DoiBongDAO();
            while (rs.next()) {
                DoiBong_TranDau dbtd = new DoiBong_TranDau();

                // Lấy tran dau (chỉ maTranDau)
                TranDau td = new TranDau();
                td.setMaTranDau(maTranDau);
                dbtd.setTranDau(td);

                // Lấy doi bong
                int maDoiBong = rs.getInt("maDoiBong");
                DoiBong db = doiBongDAO.findById(maDoiBong);
                dbtd.setDoiBong(db);

                // Các trường khác
                dbtd.setLaChuNha(rs.getInt("laChuNha"));
                dbtd.setSoBanThang(rs.getInt("soBanThang"));
                dbtd.setSoLanSut(rs.getInt("soLanSut"));
                dbtd.setSutTrungDich(rs.getInt("sutTrungDich"));
                dbtd.setKiemSoatBong(rs.getInt("kiemSoatBong"));
                dbtd.setLuotChuyenBong(rs.getInt("luotChuyenBong"));
                dbtd.setChuyenChinhXac(rs.getInt("chuyenChinhXac"));
                dbtd.setPhamLoi(rs.getInt("phamLoi"));
                dbtd.setVietVi(rs.getInt("vietVi"));
                dbtd.setPhatGoc(rs.getInt("phatGoc"));

                list.add(dbtd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteByMaTranDau(int maTranDau) {
        String sql = "DELETE FROM doibong_trandau WHERE maTranDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTranDau);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
