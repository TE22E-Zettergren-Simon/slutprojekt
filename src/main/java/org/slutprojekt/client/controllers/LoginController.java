package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slutprojekt.client.state.ConnectionHolder;
import org.slutprojekt.client.FXMLUtils;
import org.slutprojekt.shared.models.LoginForm;
import org.slutprojekt.shared.models.Message;

import java.io.IOException;
import java.net.SocketException;

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

        // Log in to the server
        try {
            Message<LoginForm> out = new Message<>(
                    "login",
                    new LoginForm(username, password)
            );
            ConnectionHolder.getInstance().getSocketConnection().write(out);

            Message in = ConnectionHolder.getInstance().getSocketConnection().read();

            // The returned message should always be with a String
            if (!(in.getData() instanceof String)) {
                errorLabel.setText("Wrong data format");
                return;
            }

            // Display a possible error message from the server
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
