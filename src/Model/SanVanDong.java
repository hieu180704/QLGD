
package Model;

public class SanVanDong {
    private int maSVD;
    private String tenSVD;
    private int sucChua;
    private String diaChi;
    private LoaiSan loaiSan;

    public SanVanDong() {
    }

    public SanVanDong(int maSVD, String tenSVD, int sucChua, String diaChi, LoaiSan loaiSan) {
        this.maSVD = maSVD;
        this.tenSVD = tenSVD;
        this.sucChua = sucChua;
        this.diaChi = diaChi;
        this.loaiSan = loaiSan;
    }

    public int getMaSVD() {
        return maSVD;
    }

    public void setMaSVD(int maSVD) {
        this.maSVD = maSVD;
    }

    public String getTenSVD() {
        return tenSVD;
    }

    public void setTenSVD(String tenSVD) {
        this.tenSVD = tenSVD;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public LoaiSan getLoaiSan() {
        return loaiSan;
    }

    public void setLoaiSan(LoaiSan loaiSan) {
        this.loaiSan = loaiSan;
    } 
}