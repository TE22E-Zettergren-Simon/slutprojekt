package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.slutprojekt.client.FXMLUtils;
import org.slutprojekt.client.components.*;
import org.slutprojekt.client.state.ConnectionHolder;
import org.slutprojekt.client.state.CurrentPostHolder;
import org.slutprojekt.shared.models.*;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

public class PostController {
    @FXML
    private VBox root;
    @FXML
    private Feed<CommentComponent> feed;

    @FXML
    private void initialize() {
        // Get the current post and show it
        Post post = CurrentPostHolder.getInstance().getPost();
        PostComponent postComponent;
        if (post instanceof LongPost) {
            postComponent = new LongPostComponent((LongPost) post);
        } else {
            postComponent = new ShortPostComponent((ShortPost) post);
        }
        postComponent.extend();
        root.getChildren().add(1, postComponent);

        // Get the post's comments
        try {
            Message<Post> out = new Message<>("get comments", post);
            ConnectionHolder.getInstance().getSocketConnection().write(out);
            Message in = ConnectionHolder.getInstance().getSocketConnection().read();

            //TODO: Better error handling, write something to the user in the window
            if (in == null) {
                return;
            }
            if (!(in.getData() instanceof ArrayList)) {
                System.out.println(in.getData());
                return;
            }

            // Add the comments to the feed
            for (Comment comment : (ArrayList<Comment>) in.getData()) {
                CommentComponent commentComponent = new CommentComponent(comment);
                feed.addBottom(commentComponent);
            }
        } catch (SocketException e) {
            FXMLUtils.loadNewView("views/no-connection.fxml", feed.getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void toHomeScreen() {
        FXMLUtils.loadNewView("views/home.fxml", root.getScene());
    }
}
