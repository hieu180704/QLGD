package Model;

import java.util.Date;

public class GiaiDau {

    private int maGiaiDau;
    private String tenGiaiDau;
    private byte[] anhGiaiDau;
    private int maNTT;
    private int maTheThuc;
    private Date ngayTaoGiai;
    private Date ngayBatDau;
    private NhaTaiTro nhaTaiTro;
    private TheThuc theThuc;

    public GiaiDau() {
    }

    public GiaiDau(int maGiaiDau, String tenGiaiDau, byte[] anhGiaiDau, int maNTT, int maTheThuc, Date ngayTaoGiai, Date ngayBatDau) {
        this.maGiaiDau = maGiaiDau;
        this.tenGiaiDau = tenGiaiDau;
        this.anhGiaiDau = anhGiaiDau;
        this.maNTT = maNTT;
        this.maTheThuc = maTheThuc;
        this.ngayTaoGiai = ngayTaoGiai;
        this.ngayBatDau = ngayBatDau;

    }

    public int getMaGiaiDau() {
        return maGiaiDau;
    }

    public void setMaGiaiDau(int maGiaiDau) {
        this.maGiaiDau = maGiaiDau;
    }

    public String getTenGiaiDau() {
        return tenGiaiDau;
    }

    public void setTenGiaiDau(String tenGiaiDau) {
        this.tenGiaiDau = tenGiaiDau;
    }

    public byte[] getAnhGiaiDau() {
        return anhGiaiDau;
    }

    public void setAnhGiaiDau(byte[] anhGiaiDau) {
        this.anhGiaiDau = anhGiaiDau;
    }

    public int getMaNTT() {
        return maNTT;
    }

    public void setMaNTT(int maNTT) {
        this.maNTT = maNTT;
    }

    public int getMaTheThuc() {
        return maTheThuc;
    }

    public void setMaTheThuc(int maTheThuc) {
        this.maTheThuc = maTheThuc;
    }

    public Date getNgayTaoGiai() {
        return ngayTaoGiai;
    }

    public void setNgayTaoGiai(Date ngayTaoGiai) {
        this.ngayTaoGiai = ngayTaoGiai;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public NhaTaiTro getNhaTaiTro() {
        return nhaTaiTro;
    }

    public void setNhaTaiTro(NhaTaiTro nhaTaiTro) {
        this.nhaTaiTro = nhaTaiTro;
    }

    public TheThuc getTheThuc() {
        return theThuc;
    }

    public void setTheThuc(TheThuc theThuc) {
        this.theThuc = theThuc;
    }
}
