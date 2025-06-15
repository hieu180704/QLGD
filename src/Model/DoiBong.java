package Model;

import Model.QuocGia;
import Model.SanVanDong;

public class DoiBong {
    private int maDoiBong;
    private String tenDoi;
    private byte[] logoDoi;
    private SanVanDong sanVanDong;
    private QuocGia quocGia;
    private GiaiDau giaiDau;

    public DoiBong() {
    }

    public DoiBong(int maDoiBong, String tenDoi, byte[] logoDoi, SanVanDong sanVanDong, QuocGia quocGia, GiaiDau giaiDau) {
        this.maDoiBong = maDoiBong;
        this.tenDoi = tenDoi;
        this.logoDoi = logoDoi;
        this.sanVanDong = sanVanDong;
        this.quocGia = quocGia;
        this.giaiDau = giaiDau;
    }

    public int getMaDoiBong() {
        return maDoiBong;
    }

    public void setMaDoiBong(int maDoiBong) {
        this.maDoiBong = maDoiBong;
    }

    public String getTenDoi() {
        return tenDoi;
    }

    public void setTenDoi(String tenDoi) {
        this.tenDoi = tenDoi;
    }

    public byte[] getLogoDoi() {
        return logoDoi;
    }

    public void setLogoDoi(byte[] logoDoi) {
        this.logoDoi = logoDoi;
    }

    public SanVanDong getSanVanDong() {
        return sanVanDong;
    }

    public void setSanVanDong(SanVanDong sanVanDong) {
        this.sanVanDong = sanVanDong;
    }

    public QuocGia getQuocGia() {
        return quocGia;
    }

    public void setQuocGia(QuocGia quocGia) {
        this.quocGia = quocGia;
    }

    public String getTenQuocGia() {
        return quocGia != null ? quocGia.getTenQuocGia() : null;
    }

    public String getTenSanVanDong() {
        return sanVanDong != null ? sanVanDong.getTenSVD() : null;
    }

    public String getTenSVD() { // Giữ lại để tương thích
        return getTenSanVanDong();
    }

    public GiaiDau getGiaiDau() {
        return giaiDau;
    }

    public void setGiaiDau(GiaiDau giaiDau) {
        this.giaiDau = giaiDau;
    }

    public int getMaQuocGia() {
        return quocGia != null ? quocGia.getMaQuocGia() : 0;
    }

    public void setMaQuocGia(int maQuocGia) {
        if (this.quocGia == null) {
            this.quocGia = new QuocGia();
        }
        this.quocGia.setMaQuocGia(maQuocGia);
    }

    public int getMaSVD() {
        return sanVanDong != null ? sanVanDong.getMaSVD() : 0;
    }

    public void setMaSVD(int maSVD) {
        if (this.sanVanDong == null) {
            this.sanVanDong = new SanVanDong();
        }
        this.sanVanDong.setMaSVD(maSVD);
    }
//    public String getTenSVD() {
//        return sanVanDong != null ? sanVanDong.getTenSVD() : null;  
//    }
    

    @Override
    public String toString() {
        return tenDoi + " (Mã: " + maDoiBong + ")"; // Hiển thị tên đội bóng và mã trong JComboBox
    }
}

