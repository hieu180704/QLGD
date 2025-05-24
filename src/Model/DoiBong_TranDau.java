package Model;

public class DoiBong_TranDau {
    private TranDau tranDau;
    private DoiBong doiBong;
    private int soLanSut;
    private int sutTrungDich;
    private int kiemSoatBong;
    private int luotChuyenBong;
    private int chuyenChinhXac;
    private int phamLoi;
    private int vietVi;
    private int phatGoc;

    public DoiBong_TranDau() {
    }

    public DoiBong_TranDau(TranDau tranDau, DoiBong doiBong, int soLanSut, int sutTrungDich, int kiemSoatBong, int luotChuyenBong, int chuyenChinhXac, int phamLoi, int vietVi, int phatGoc) {
        this.tranDau = tranDau;
        this.doiBong = doiBong;
        this.soLanSut = soLanSut;
        this.sutTrungDich = sutTrungDich;
        this.kiemSoatBong = kiemSoatBong;
        this.luotChuyenBong = luotChuyenBong;
        this.chuyenChinhXac = chuyenChinhXac;
        this.phamLoi = phamLoi;
        this.vietVi = vietVi;
        this.phatGoc = phatGoc;
    }

    public TranDau getTranDau() {
        return tranDau;
    }

    public void setTranDau(TranDau tranDau) {
        this.tranDau = tranDau;
    }

    public DoiBong getDoiBong() {
        return doiBong;
    }

    public void setDoiBong(DoiBong doiBong) {
        this.doiBong = doiBong;
    }

    public int getSoLanSut() {
        return soLanSut;
    }

    public void setSoLanSut(int soLanSut) {
        this.soLanSut = soLanSut;
    }

    public int getSutTrungDich() {
        return sutTrungDich;
    }

    public void setSutTrungDich(int sutTrungDich) {
        this.sutTrungDich = sutTrungDich;
    }

    public int getKiemSoatBong() {
        return kiemSoatBong;
    }

    public void setKiemSoatBong(int kiemSoatBong) {
        this.kiemSoatBong = kiemSoatBong;
    }

    public int getLuotChuyenBong() {
        return luotChuyenBong;
    }

    public void setLuotChuyenBong(int luotChuyenBong) {
        this.luotChuyenBong = luotChuyenBong;
    }

    public int getChuyenChinhXac() {
        return chuyenChinhXac;
    }

    public void setChuyenChinhXac(int chuyenChinhXac) {
        this.chuyenChinhXac = chuyenChinhXac;
    }

    public int getPhamLoi() {
        return phamLoi;
    }

    public void setPhamLoi(int phamLoi) {
        this.phamLoi = phamLoi;
    }

    public int getVietVi() {
        return vietVi;
    }

    public void setVietVi(int vietVi) {
        this.vietVi = vietVi;
    }

    public int getPhatGoc() {
        return phatGoc;
    }

    public void setPhatGoc(int phatGoc) {
        this.phatGoc = phatGoc;
    }
    
    
}
