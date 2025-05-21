package Model;

import java.util.Date;

public class CauThu {
    private int maCauThu;
    private String tenCauThu;
    private byte[] anhCauThu;
    private Date ngaySinh;
    private int maQuocGia;
    private int chieuCao;
    private int canNang;
    private int maDoi;
    private int soAo;

    public CauThu() {}

    public CauThu(int maCauThu, String tenCauThu, byte[] anhCauThu, Date ngaySinh,
                  int maQuocGia, int chieuCao, int canNang, int maDoi, int soAo) {
        this.maCauThu = maCauThu;
        this.tenCauThu = tenCauThu;
        this.anhCauThu = anhCauThu;
        this.ngaySinh = ngaySinh;
        this.maQuocGia = maQuocGia;
        this.chieuCao = chieuCao;
        this.canNang = canNang;
        this.maDoi = maDoi;
        this.soAo = soAo;
    }

    public int getMaCauThu() { return maCauThu; }
    public void setMaCauThu(int maCauThu) { this.maCauThu = maCauThu; }

    public String getTenCauThu() { return tenCauThu; }
    public void setTenCauThu(String tenCauThu) { this.tenCauThu = tenCauThu; }

    public byte[] getAnhCauThu() { return anhCauThu; }
    public void setAnhCauThu(byte[] anhCauThu) { this.anhCauThu = anhCauThu; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public int getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(int maQuocGia) { this.maQuocGia = maQuocGia; }

    public int getChieuCao() { return chieuCao; }
    public void setChieuCao(int chieuCao) { this.chieuCao = chieuCao; }

    public int getCanNang() { return canNang; }
    public void setCanNang(int canNang) { this.canNang = canNang; }

    public int getMaDoi() { return maDoi; }
    public void setMaDoi(int maDoi) { this.maDoi = maDoi; }

    public int getSoAo() { return soAo; }
    public void setSoAo(int soAo) { this.soAo = soAo; }
}
