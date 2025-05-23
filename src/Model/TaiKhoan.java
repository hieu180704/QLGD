package Model;

public class TaiKhoan {
    private int mataikhoan;
    private String tendangnhap;
    private String matkhau;
    private String email;
    private int loaitaikhoan;

    public TaiKhoan() {
    }

    public TaiKhoan(int mataikhoan, String tendangnhap, String matkhau, String email, int loaitaikhoan) {
        this.mataikhoan = mataikhoan;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.email = email;
        this.loaitaikhoan = loaitaikhoan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMataikhoan() {
        return mataikhoan;
    }

    public void setMataikhoan(int mataikhoan) {
        this.mataikhoan = mataikhoan;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getLoaitaikhoan() {
        return loaitaikhoan;
    }

    public void setLoaitaikhoan(int loaitaikhoan) {
        this.loaitaikhoan = loaitaikhoan;
    }

    
}