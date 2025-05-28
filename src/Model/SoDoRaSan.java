package Model;

public class SoDoRaSan {
    private int maSoDo;
    private String tenSoDo;

    public SoDoRaSan() {
    }

    public SoDoRaSan(int maSoDo, String tenSoDo) {
        this.maSoDo = maSoDo;
        this.tenSoDo = tenSoDo;
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
}
