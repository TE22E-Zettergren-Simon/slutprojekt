package org.slutprojekt.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class FXMLUtils {
    // Loads the file at fxmlPath into the provided scene
    public static void loadNewView(String fxmlPath, Scene scene) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlPath));
        scene.setRoot(fxmlLoader.load());
    }
}
