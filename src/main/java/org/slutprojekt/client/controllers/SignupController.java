package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField1;
    @FXML
    private PasswordField passwordField2;

    @FXML
    private Label errorLabel;

    @FXML
    private void submit() {
        // Gets the username and makes sure it is not blank
        String username = usernameField.getText();
        if (username.isBlank()) {
            errorLabel.setText("No username entered");
            return;
        }

        // Gets the passwords and makes sure they are not blank and they match
        String password1 = passwordField1.getText();
        String password2 = passwordField2.getText();
        if  (password1.isBlank() || password2.isBlank()) {
            errorLabel.setText("No password entered");
            return;
        }
        if (!password1.equals(password2)) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        //TODO: talk to server
        errorLabel.setText("TODO");
    }
}
