package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.slutprojekt.client.FXMLUtils;
import org.slutprojekt.client.components.*;
import org.slutprojekt.client.state.CurrentPostHolder;
import org.slutprojekt.shared.models.*;

public class PostController {
    @FXML
    private VBox root;
    @FXML
    private Feed<CommentComponent> feed;

    @FXML
    private void initialize() {
        //TODO: Get data from server

        Post post = CurrentPostHolder.getInstance().getPost();
        PostComponent postComponent;
        if (post instanceof LongPost) {
            postComponent = new LongPostComponent((LongPost) post);
        } else {
            postComponent = new ShortPostComponent((ShortPost) post);
        }
        postComponent.extend();
        root.getChildren().add(1, postComponent);
    }

    @FXML
    private void toHomeScreen() {
        FXMLUtils.loadNewView("views/home.fxml", root.getScene());
    }
}
