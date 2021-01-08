package ie.gmit.sw;

import javafx.application.*;

/**
 * Runs the program
 *
 * @author Arnas Steponavicius
 */
public class Runner {

    public static void main(String[] args) {
        System.out.println("[INFO] Launching GUI...");
        Application.launch(AppWindow.class, args);
    }
}
