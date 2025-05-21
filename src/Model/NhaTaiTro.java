package Model;

public class NhaTaiTro {

    private int maNTT;
    private String tenNTT;
    private byte[] logoNTT;
    private String email;
    private String soDienThoai;
    private String diaChi;

    public NhaTaiTro() {
    }

    public NhaTaiTro(int maNTT, String tenNTT, byte[] logoNTT, String email, String soDienThoai, String diaChi) {
        this.maNTT = maNTT;
        this.tenNTT = tenNTT;
        this.logoNTT = logoNTT;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }

    public int getMaNTT() {
        return maNTT;
    }

    public void setMaNTT(int maNTT) {
        this.maNTT = maNTT;
    }

    public String getTenNTT() {
        return tenNTT;
    }

    public void setTenNTT(String tenNTT) {
        this.tenNTT = tenNTT;
    }

    public byte[] getLogoNTT() {
        return logoNTT;
    }

    public void setLogoNTT(byte[] logoNTT) {
        this.logoNTT = logoNTT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return tenNTT;
    }
}
