package Main;

import ViewMain.LoginView;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

public class MainProject {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
