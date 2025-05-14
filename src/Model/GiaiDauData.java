package Model;

import java.util.ArrayList;
import java.util.List;

public class GiaiDauData {
    public static List<GiaiDauModel> getData() {
        List<GiaiDauModel> giaiDauList = new ArrayList<>();

        giaiDauList.add(new GiaiDauModel(
            "GD01", 
            "Giải Vô Địch Quốc Gia", 
            "logos/vdqg.png",  // giả sử ảnh lưu trong thư mục logos
            "Chia bảng", 
            2024
        ));

        giaiDauList.add(new GiaiDauModel(
            "GD02", 
            "Giải Cúp Quốc Gia", 
            "logos/cupquocgia.png", 
            "Loại trực tiếp", 
            2024
        ));

        giaiDauList.add(new GiaiDauModel(
            "GD03", 
            "Giải Ngoại Hạng", 
            "logos/ngoaihak.png", 
            "Thể thức vòng tròn", 
            2024
        ));
        
        giaiDauList.add(new GiaiDauModel(
            "GD04", 
            "Giải Ngoại Hạng", 
            "logos/ngoaihak.png", 
            "Thể thức vòng tròn", 
            2024
        ));        

        giaiDauList.add(new GiaiDauModel(
            "GD05", 
            "Giải Ngoại Hạng", 
            "logos/ngoaihak.png", 
            "Thể thức vòng tròn", 
            2024
        ));          
        
        return giaiDauList;
    }
}
