package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slutprojekt.client.ConnectionHolder;
import org.slutprojekt.client.FXMLUtils;
import org.slutprojekt.shared.models.Message;

import java.io.IOException;
import java.net.SocketException;

public class HomeController {
    @FXML
    private Label label;
    @FXML
    private TextField textField;

    @FXML
    private void submit() {
        String text = textField.getText();
        Message messageOut = new Message(text, null);
        try {
            ConnectionHolder.getInstance().getSocketConnection().write(messageOut);
            if (!text.equalsIgnoreCase("exit")) {
                Message messageIn = ConnectionHolder.getInstance().getSocketConnection().read();
                label.setText(messageIn.getMessage());
            } else {
                FXMLUtils.loadNewView("views/no-connection.fxml", label.getScene());
            }
        } catch (SocketException e) {
                FXMLUtils.loadNewView("views/no-connection.fxml", label.getScene());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ignored) {}
    }
}
