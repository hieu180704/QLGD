package Model;

import java.time.LocalDate;

public class GiaiDau {

    private int maGiaiDau;
    private String tenGiaiDau;
    private byte[] anhGiaiDau;
    private TheThuc theThuc; // tham chiếu tới TheThuc
    private LocalDate ngayTaoGiai;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    // Constructor
    public GiaiDau() {
    }

    public GiaiDau(int maGiaiDau, String tenGiaiDau, byte[] anhGiaiDau, TheThuc theThuc, LocalDate ngayTaoGiai, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maGiaiDau = maGiaiDau;
        this.tenGiaiDau = tenGiaiDau;
        this.anhGiaiDau = anhGiaiDau;
        this.theThuc = theThuc;
        this.ngayTaoGiai = ngayTaoGiai;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
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

    public TheThuc getTheThuc() {
        return theThuc;
    }

    public void setTheThuc(TheThuc theThuc) {
        this.theThuc = theThuc;
    }

    public LocalDate getNgayTaoGiai() {
        return ngayTaoGiai;
    }

    public void setNgayTaoGiai(LocalDate ngayTaoGiai) {
        this.ngayTaoGiai = ngayTaoGiai;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }



    @Override
    public String toString() {
        return this.tenGiaiDau;
    }
}
