package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.slutprojekt.client.FXMLUtils;

public class CreatePostController {
    @FXML
    private TextArea headerInput;
    @FXML
    private TextArea bodyInput;

    @FXML
    private void submit() {
        //TODO: Create a new post on the server

        // Switch to the home feed
        FXMLUtils.loadNewView("views/home.fxml", headerInput.getScene());
    }
}
