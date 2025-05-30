package Model;

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

    public GiaiDau getGiaiDau() {
        return giaiDau;
    }

    public void setGiaiDau(GiaiDau giaiDau) {
        this.giaiDau = giaiDau;
    }
    
    
}


