package Model;

public class TheThuc {

    private int maTheThuc;
    private String tenTheThuc;

    public TheThuc() {
    }

    public TheThuc(int maTheThuc, String tenTheThuc) {
        this.maTheThuc = maTheThuc;
        this.tenTheThuc = tenTheThuc;
    }

    public int getMaTheThuc() {
        return maTheThuc;
    }

    public void setMaTheThuc(int maTheThuc) {
        this.maTheThuc = maTheThuc;
    }

    public String getTenTheThuc() {
        return tenTheThuc;
    }

    public void setTenTheThuc(String tenTheThuc) {
        this.tenTheThuc = tenTheThuc;
    }

    @Override
    public String toString() {
        return tenTheThuc;
    }
}
