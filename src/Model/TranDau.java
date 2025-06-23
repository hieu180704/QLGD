package Model;

import java.time.LocalDateTime;
import java.util.List;

public class TranDau {

    private int maTranDau;
    private LocalDateTime thoiGian;
    private GiaiDau giaiDau;
    private TrongTai trongTai;
    private DoiBong team1;
    private DoiBong team2;
    private List<DoiBong_TranDau> doiBongTranDauList;

    public TranDau() {
    }

    public TranDau(int maTranDau, LocalDateTime thoiGian, GiaiDau giaiDau, TrongTai trongTai, DoiBong team1, DoiBong team2) {
        this.maTranDau = maTranDau;
        this.thoiGian = thoiGian;
        this.giaiDau = giaiDau;
        this.trongTai = trongTai;
        this.team1 = team1;
        this.team2 = team2;
    }

    public DoiBong getTeam1() {
        return team1;
    }

    public void setTeam1(DoiBong team1) {
        this.team1 = team1;
    }

    public DoiBong getTeam2() {
        return team2;
    }

    public void setTeam2(DoiBong team2) {
        this.team2 = team2;
    }

    public int getMaTranDau() {
        return maTranDau;
    }

    public void setMaTranDau(int maTranDau) {
        this.maTranDau = maTranDau;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public GiaiDau getGiaiDau() {
        return giaiDau;
    }

    public void setGiaiDau(GiaiDau giaiDau) {
        this.giaiDau = giaiDau;
    }

    public TrongTai getTrongTai() {
        return trongTai;
    }

    public void setTrongTai(TrongTai trongTai) {
        this.trongTai = trongTai;
    }

    public List<DoiBong_TranDau> getDoiBongTranDauList() {
        return doiBongTranDauList;
    }

    public void setDoiBongTranDauList(List<DoiBong_TranDau> doiBongTranDauList) {
        this.doiBongTranDauList = doiBongTranDauList;
    }
}
