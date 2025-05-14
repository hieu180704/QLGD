package Model;

public class GiaiDauModel {
    private String maGiai;
    private String tenGiai;
    private String logoGiai;  // đường dẫn hoặc URL ảnh logo
    private String theThuc;
    private int muaGiai;      // năm mùa giải

    public GiaiDauModel(String maGiai, String tenGiai, String logoGiai, String theThuc, int muaGiai) {
        this.maGiai = maGiai;
        this.tenGiai = tenGiai;
        this.logoGiai = logoGiai;
        this.theThuc = theThuc;
        this.muaGiai = muaGiai;
    }

    public String getMaGiai() {
        return maGiai;
    }

    public void setMaGiai(String maGiai) {
        this.maGiai = maGiai;
    }

    public String getTenGiai() {
        return tenGiai;
    }

    public void setTenGiai(String tenGiai) {
        this.tenGiai = tenGiai;
    }

    public String getLogoGiai() {
        return logoGiai;
    }

    public void setLogoGiai(String logoGiai) {
        this.logoGiai = logoGiai;
    }

    public String getTheThuc() {
        return theThuc;
    }

    public void setTheThuc(String theThuc) {
        this.theThuc = theThuc;
    }

    public int getMuaGiai() {
        return muaGiai;
    }

    public void setMuaGiai(int muaGiai) {
        this.muaGiai = muaGiai;
    }
}
