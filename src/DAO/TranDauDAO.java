package DAO;

import Model.DoiBong_TranDau;
import Model.TranDau;
import Model.GiaiDau;
import Model.TrongTai;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TranDauDAO implements GenericDAO<TranDau> {

    private DoiBong_TranDauDAO doiBongTranDauDAO = new DoiBong_TranDauDAO();

    @Override
    public boolean insert(TranDau obj) {
        String sql = "INSERT INTO trandau (maTrongTai, thoiGian, maGiaiDau) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, obj.getTrongTai().getMaTrongTai());
            ps.setTimestamp(2, Timestamp.valueOf(obj.getThoiGian()));
            ps.setInt(3, obj.getGiaiDau().getMaGiaiDau());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setMaTranDau(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int insertAndGetId(TranDau td) {
        String sql = "INSERT INTO trandau (maTrongTai, thoiGian, maGiaiDau) VALUES (?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (td.getTrongTai() != null) {
                ps.setInt(1, td.getTrongTai().getMaTrongTai());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            if (td.getThoiGian() != null) {
                ps.setTimestamp(2, Timestamp.valueOf(td.getThoiGian()));
            } else {
                ps.setNull(2, Types.TIMESTAMP);
            }

            ps.setInt(3, td.getGiaiDau().getMaGiaiDau());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean update(TranDau obj) {
        String sql = "UPDATE trandau SET maTrongTai = ?, thoiGian = ?, maGiaiDau = ? WHERE maTranDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (obj.getTrongTai() != null) {
                ps.setInt(1, obj.getTrongTai().getMaTrongTai());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            ps.setTimestamp(2, Timestamp.valueOf(obj.getThoiGian()));
            ps.setInt(3, obj.getGiaiDau().getMaGiaiDau());
            ps.setInt(4, obj.getMaTranDau());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM trandau WHERE maTranDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TranDau findById(int id) {
        String sql = "SELECT * FROM trandau WHERE maTranDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TranDau> findAll() {
        List<TranDau> list = new ArrayList<>();
        String sql = "SELECT td.*, gd.maGiaiDau, gd.tenGiaiDau "
                + "FROM trandau td "
                + "JOIN giaidau gd ON td.maGiaiDau = gd.maGiaiDau";
        try (Connection conn = ConnectDB.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                TranDau td = new TranDau();
                td.setMaTranDau(rs.getInt("maTranDau"));

                Timestamp timestamp = rs.getTimestamp("thoiGian");
                if (timestamp != null) {
                    td.setThoiGian(timestamp.toLocalDateTime());
                } else {
                    td.setThoiGian(null);
                }

                GiaiDau gd = new GiaiDau();
                gd.setMaGiaiDau(rs.getInt("maGiaiDau"));
                gd.setTenGiaiDau(rs.getString("tenGiaiDau"));
                td.setGiaiDau(gd);

                List<DoiBong_TranDau> doiBongList = doiBongTranDauDAO.findByMaTranDau(td.getMaTranDau());
                td.setDoiBongTranDauList(doiBongList);

                if (doiBongList != null) {
                    for (DoiBong_TranDau dbtd : doiBongList) {
                        if (dbtd.getLaChuNha() == 1) {
                            td.setTeam1(dbtd.getDoiBong());
                        } else {
                            td.setTeam2(dbtd.getDoiBong());
                        }
                    }
                }

                list.add(td);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private TranDau map(ResultSet rs) throws SQLException {
        TranDau td = new TranDau();

        td.setMaTranDau(rs.getInt("maTranDau"));
        Timestamp ts = rs.getTimestamp("thoiGian");
        if (ts != null) {
            td.setThoiGian(ts.toLocalDateTime());
        } else {
            td.setThoiGian(null); // hoặc xử lý null phù hợp
        }

        // Map GiaiDau (chỉ set maGiaiDau)
        GiaiDau gd = new GiaiDau();
        gd.setMaGiaiDau(rs.getInt("maGiaiDau"));
        td.setGiaiDau(gd);

        // Map TrongTai (chỉ set maTrongTai)
        TrongTai tt = new TrongTai();
        tt.setMaTrongTai(rs.getInt("maTrongTai"));
        td.setTrongTai(tt);

        return td;
    }

    public boolean hasMatchInGiaiDau(int maGiaiDau) {
        String sql = "SELECT COUNT(*) FROM trandau WHERE maGiaiDau = ?";
        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maGiaiDau);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<TranDau> findByMaGiaiDau(int maGiaiDau) {
        List<TranDau> list = new ArrayList<>();
        String sql = "SELECT td.*, gd.maGiaiDau, gd.tenGiaiDau "
                + "FROM trandau td "
                + "JOIN giaidau gd ON td.maGiaiDau = gd.maGiaiDau "
                + "WHERE td.maGiaiDau = ?";

        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maGiaiDau);

            try (ResultSet rs = ps.executeQuery()) {
                DoiBongDAO doiBongDAO = new DoiBongDAO();

                while (rs.next()) {
                    TranDau td = new TranDau();
                    td.setMaTranDau(rs.getInt("maTranDau"));

                    Timestamp timestamp = rs.getTimestamp("thoiGian");
                    if (timestamp != null) {
                        td.setThoiGian(timestamp.toLocalDateTime());
                    } else {
                        td.setThoiGian(null);
                    }

                    GiaiDau gd = new GiaiDau();
                    gd.setMaGiaiDau(rs.getInt("maGiaiDau"));
                    gd.setTenGiaiDau(rs.getString("tenGiaiDau"));
                    td.setGiaiDau(gd);

                    // Lấy danh sách DoiBong_TranDau
                    List<DoiBong_TranDau> doiBongList = doiBongTranDauDAO.findByMaTranDau(td.getMaTranDau());
                    td.setDoiBongTranDauList(doiBongList);

                    // SET team1 và team2 dựa trên doiBongList (laChuNha = 1 là home, 0 là guest)
                    for (DoiBong_TranDau dbtd : doiBongList) {
                        if (dbtd.getLaChuNha() == 1) {
                            td.setTeam1(dbtd.getDoiBong());
                        } else if (dbtd.getLaChuNha() == 0) {
                            td.setTeam2(dbtd.getDoiBong());
                        }
                    }

                    list.add(td);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TranDau> findByMaDoiBong(int maDoiBong) {
        List<TranDau> list = new ArrayList<>();
        String sql = "SELECT *"
                + "FROM trandau td "
                + "JOIN doibong_trandau dbtd ON td.maTranDau = dbtd.maTranDau "
                + "JOIN giaidau gd ON td.maGiaiDau = gd.maGiaiDau "
                + "WHERE maDoiBong = ?";

        try (Connection conn = ConnectDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDoiBong);

            try (ResultSet rs = ps.executeQuery()) {
                DoiBongDAO doiBongDAO = new DoiBongDAO();

                while (rs.next()) {
                    TranDau td = new TranDau();
                    td.setMaTranDau(rs.getInt("maTranDau"));

                    Timestamp timestamp = rs.getTimestamp("thoiGian");
                    if (timestamp != null) {
                        td.setThoiGian(timestamp.toLocalDateTime());
                    } else {
                        td.setThoiGian(null);
                    }

                    GiaiDau gd = new GiaiDau();
                    gd.setMaGiaiDau(rs.getInt("maGiaiDau"));
                    gd.setTenGiaiDau(rs.getString("tenGiaiDau"));
                    td.setGiaiDau(gd);

                    // Lấy danh sách DoiBong_TranDau
                    List<DoiBong_TranDau> doiBongList = doiBongTranDauDAO.findByMaTranDau(td.getMaTranDau());
                    td.setDoiBongTranDauList(doiBongList);

                    // SET team1 và team2 dựa trên doiBongList (laChuNha = 1 là home, 0 là guest)
                    for (DoiBong_TranDau dbtd : doiBongList) {
                        if (dbtd.getLaChuNha() == 1) {
                            td.setTeam1(dbtd.getDoiBong());
                        } else if (dbtd.getLaChuNha() == 0) {
                            td.setTeam2(dbtd.getDoiBong());
                        }
                    }

                    list.add(td);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
