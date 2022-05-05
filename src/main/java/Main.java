import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import view.WelcomeFrame;

import javax.swing.*;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        initializeUI();
        SwingUtilities.invokeLater(() -> {
            WelcomeFrame welcomeFrame = new WelcomeFrame();
            welcomeFrame.setVisible(true);
        });
    }

    public static void initializeUI(){
        try {
            Locale.setDefault(new Locale("en"));
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
