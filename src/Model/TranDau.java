package Model;

import java.util.Date;

public class TranDau {
    private int maTranDau;
    private Date thoiGian;
    private GiaiDau giaiDau;
    private TrongTai trongTai;

    public TranDau() {
    }

    public TranDau(int maTranDau, Date thoiGian, GiaiDau giaiDau, TrongTai trongTai) {
        this.maTranDau = maTranDau;
        this.thoiGian = thoiGian;
        this.giaiDau = giaiDau;
        this.trongTai = trongTai;
    }

    public int getMaTranDau() {
        return maTranDau;
    }

    public void setMaTranDau(int maTranDau) {
        this.maTranDau = maTranDau;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
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
    
    
}
