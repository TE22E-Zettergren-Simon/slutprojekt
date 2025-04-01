package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void submit() {
        // Get the username and make sure it isn't blank
        String username = usernameField.getText();
        if (username.isBlank()) {
            errorLabel.setText("No username entered");
            return;
        }

        // Get the password and make sure it isn't blank
        String password = passwordField.getText();
        if (password.isBlank()) {
            errorLabel.setText("No password entered");
            return;
        }

        //TODO: Talk to server
        errorLabel.setText("TODO");
    }
}
