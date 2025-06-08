package Model;

public class SoDo {
    private int maSoDo;
    private String tenSoDo;
    private String loaiSoDo;
    private String chienThuat;
    private int maDoiBong;

    public SoDo() {
    }

    public SoDo(int maSoDo, String tenSoDo, String loaiSoDo, String chienThuat, int maDoiBong) {
        this.maSoDo = maSoDo;
        this.tenSoDo = tenSoDo;
        this.loaiSoDo = loaiSoDo;
        this.chienThuat = chienThuat;
        this.maDoiBong = maDoiBong;
    }

    public int getMaSoDo() {
        return maSoDo;
    }

    public void setMaSoDo(int maSoDo) {
        this.maSoDo = maSoDo;
    }

    public String getTenSoDo() {
        return tenSoDo;
    }

    public void setTenSoDo(String tenSoDo) {
        this.tenSoDo = tenSoDo;
    }

    public String getLoaiSoDo() {
        return loaiSoDo;
    }

    public void setLoaiSoDo(String loaiSoDo) {
        this.loaiSoDo = loaiSoDo;
    }

    public String getChienThuat() {
        return chienThuat;
    }

    public void setChienThuat(String chienThuat) {
        this.chienThuat = chienThuat;
    }

    public int getMaDoiBong() {
        return maDoiBong;
    }

    public void setMaDoiBong(int maDoiBong) {
        this.maDoiBong = maDoiBong;
    }
}