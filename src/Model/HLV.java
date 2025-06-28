package Model;

import java.util.Date;

public class HLV {
    private int maHLV;
    private String tenHLV;
    private byte[] anhHLV;
    private Date ngaySinh;
    private int maQuocGia;
    private int maDoi;

    // Thêm 2 trường tên hiển thị cho quốc gia và đội bóng (không lưu trong DB trực tiếp, dùng join lấy)
    private String tenQuocGia;
    private String tenDoi;

    public HLV() {}

    public HLV(int maHLV, String tenHLV, byte[] anhHLV, Date ngaySinh, int maQuocGia, int maDoi) {
        this.maHLV = maHLV;
        this.tenHLV = tenHLV;
        this.anhHLV = anhHLV;
        this.ngaySinh = ngaySinh;
        this.maQuocGia = maQuocGia;
        this.maDoi = maDoi;
    }

    // Getter và Setter cho các trường cơ bản
    public int getMaHLV() { return maHLV; }
    public void setMaHLV(int maHLV) { this.maHLV = maHLV; }

    public String getTenHLV() { return tenHLV; }
    public void setTenHLV(String tenHLV) { this.tenHLV = tenHLV; }

    public byte[] getAnhHLV() { return anhHLV; }
    public void setAnhHLV(byte[] anhHLV) { this.anhHLV = anhHLV; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public int getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(int maQuocGia) { this.maQuocGia = maQuocGia; }

    public int getMaDoi() { return maDoi; }
    public void setMaDoi(int maDoi) { this.maDoi = maDoi; }

    public String getTenQuocGia() { return tenQuocGia; }
    public void setTenQuocGia(String tenQuocGia) { this.tenQuocGia = tenQuocGia; }

    public String getTenDoi() { return tenDoi; }
    public void setTenDoi(String tenDoi) { this.tenDoi = tenDoi; }
}