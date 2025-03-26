package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slutprojekt.client.ConnectionHolder;
import org.slutprojekt.shared.models.Message;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

public class HomeController {
    @FXML
    private Label label;
    @FXML
    private TextField textField;

    @FXML
    private void submit() {
        String text = textField.getText();
        Message messageOut = new Message(text, null);
        Message messageIn;
        try {
            ConnectionHolder.getInstance().getSocketConnection().write(messageOut);
            messageIn = ConnectionHolder.getInstance().getSocketConnection().read();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        label.setText(messageIn.getMessage());
    }
}
