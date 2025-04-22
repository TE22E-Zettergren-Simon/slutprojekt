package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slutprojekt.client.FXMLUtils;
import org.slutprojekt.client.state.ConnectionHolder;
import org.slutprojekt.shared.models.CreatePostForm;
import org.slutprojekt.shared.models.Message;

import java.net.SocketException;

public class CreatePostController {
    @FXML
    private TextField headerInput;
    @FXML
    private TextArea bodyInput;
    @FXML
    private Label errorLabel;

    @FXML
    private void submit() {
        try {
            // Send a message and receive a response
            Message<CreatePostForm> out = new Message<>(
                    "create post",
                    new CreatePostForm(headerInput.getText(), bodyInput.getText())
            );
            ConnectionHolder.getInstance().getSocketConnection().write(out);
            Message in = ConnectionHolder.getInstance().getSocketConnection().read();

            // Verify data
            if (in == null) {
                errorLabel.setText("No data received");
            } else if (in.getMessage().equals("error")) {
                errorLabel.setText((String) in.getData());
            } else {
                // Switch to the home feed if creating the post went ok
                FXMLUtils.loadNewView("views/home.fxml", headerInput.getScene());
            }
        } catch (SocketException e) {
            FXMLUtils.loadNewView("views/no-connection.fxml", headerInput.getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
