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

// Controller for the create post screen (create-post.fxml)
// Allows the user to crete a post on the server
public class CreatePostController {
    @FXML
    private TextField headerInput;
    @FXML
    private TextArea bodyInput;
    @FXML
    private Label errorLabel;

    // Attempts to make a post
    // Fails if the input is bad or the server fails
    // An error message is displayed or the screen switches to the no connection screen if a failure happens
    @FXML
    private void submit() {
        try {
            // Don't send message if no header is provided
            if (headerInput.getText().isBlank()) {
                errorLabel.setText("Please enter a post header");
            }
            // Send a message and receive a response
            Message<CreatePostForm> out = new Message<>(
                    "create post",
                    new CreatePostForm(headerInput.getText(), bodyInput.getText())
            );
            ConnectionHolder.getInstance().write(out);
            Message in = ConnectionHolder.getInstance().read();

            // Verify data
            if (in.getMessage().equals("error")) {
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
