/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

public class TrongTai {
    private int maTrongTai;
    private String tenTrongTai;
    private Date ngaySinh;
    private QuocGia quocGia;

    public TrongTai(int maTrongTai, String tenTrongTai, Date ngaySinh, QuocGia quocGia) {
        this.maTrongTai = maTrongTai;
        this.tenTrongTai = tenTrongTai;
        this.ngaySinh = ngaySinh;
        this.quocGia = quocGia;
    }

    public int getMaTrongTai() { return maTrongTai; }
    public void setMaTrongTai(int maTrongTai) { this.maTrongTai = maTrongTai; }
    public String getTenTrongTai() { return tenTrongTai; }
    public void setTenTrongTai(String tenTrongTai) { this.tenTrongTai = tenTrongTai; }
    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }
    public QuocGia getQuocGia() { return quocGia; }
    public void setQuocGia(QuocGia quocGia) { this.quocGia = quocGia; }
}
