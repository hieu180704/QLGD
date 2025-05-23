/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class QuocGia {
    private int maQuocGia;
    private String tenQuocGia;

    public QuocGia(int maQuocGia, String tenQuocGia) {
        this.maQuocGia = maQuocGia;
        this.tenQuocGia = tenQuocGia;
    }

    public int getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(int maQuocGia) { this.maQuocGia = maQuocGia; }
    public String getTenQuocGia() { return tenQuocGia; }
    public void setTenQuocGia(String tenQuocGia) { this.tenQuocGia = tenQuocGia; }
    @Override
    public String toString() { return tenQuocGia; }
}


