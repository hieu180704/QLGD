package Model;

import java.util.Date;
import java.util.List;

public class GiaiDau {

    private int maGiaiDau;
    private String tenGiaiDau;
    private byte[] anhGiaiDau;
    private TheThuc theThuc; // tham chiếu tới TheThuc
    private Date ngayTaoGiai;
    private Date ngayBatDau;
    private Date ngayKetThuc;

    // Constructor
    public GiaiDau() {
    }

    public GiaiDau(int maGiaiDau, String tenGiaiDau, byte[] anhGiaiDau, TheThuc theThuc,
            Date ngayTaoGiai, Date ngayBatDau, Date ngayKetThuc) {
        this.maGiaiDau = maGiaiDau;
        this.tenGiaiDau = tenGiaiDau;
        this.anhGiaiDau = anhGiaiDau;
        this.theThuc = theThuc;
        this.ngayTaoGiai = ngayTaoGiai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    // Getters & Setters
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

    public TheThuc getTheThuc() {
        return theThuc;
    }

    public void setTheThuc(TheThuc theThuc) {
        this.theThuc = theThuc;
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

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}
