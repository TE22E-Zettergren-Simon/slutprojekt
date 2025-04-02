package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slutprojekt.client.ConnectionHolder;
import org.slutprojekt.client.FXMLUtils;
import org.slutprojekt.shared.models.LoginForm;
import org.slutprojekt.shared.models.Message;

import java.io.IOException;
import java.net.SocketException;

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

        // Try to sign up to the server
        try {
            // Send message
            Message<LoginForm> out = new Message<>(
                    "signup",
                    new LoginForm(username, password1)
            );
            ConnectionHolder.getInstance().getSocketConnection().write(out);

            Message in = ConnectionHolder.getInstance().getSocketConnection().read();

            // The returned data should always be a string
            if (!(in.getData() instanceof String)) {
                errorLabel.setText("Wrong data format");
                return;
            }

            // Display the error to the user if it is an error
            if (in.getMessage().equals("error")) {
                errorLabel.setText((String) in.getData());
                return;
            }

            FXMLUtils.loadNewView("views/home.fxml", usernameField.getScene());
        } catch (SocketException e) {
            FXMLUtils.loadNewView("views/no-connection.fxml", usernameField.getScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ignored) {}
    }
}
