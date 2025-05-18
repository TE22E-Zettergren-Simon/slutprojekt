package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.slutprojekt.client.FXMLUtils;

// Controller for the start screen (start.fxml)
// Really only redirect to the login and signup screens
public class StartController {
    @FXML
    private VBox root;

    @FXML
    private void toLoginScreen() {
        try {
            FXMLUtils.loadNewView("views/login.fxml", root.getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void toSignupScreen() {
        try {
            FXMLUtils.loadNewView("views/signup.fxml", root.getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
