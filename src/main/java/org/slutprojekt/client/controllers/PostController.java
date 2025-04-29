package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private TextField commentInput;
    @FXML
    private Label errorLabel;
    @FXML
    private Feed<CommentComponent> feed;

    private Post post;

    @FXML
    private void initialize() {
        // Get the current post and show it
        post = CurrentPostHolder.getInstance().getPost();
        PostComponent postComponent;
        if (post instanceof LongPost) {
            postComponent = new LongPostComponent((LongPost) post);
        } else {
            postComponent = new ShortPostComponent((ShortPost) post);
        }
        postComponent.extend();
        root.getChildren().add(2, postComponent);

        reload();
    }

    @FXML
    private void toHomeScreen() {
        FXMLUtils.loadNewView("views/home.fxml", root.getScene());
    }

    // Empties the feed of comments and gets the newest comments from the server
    @FXML
    private void reload() {
        feed.clear();
        try {
            // Get the post's comments
            Message<Post> out = new Message<>("get comments", post);
            ConnectionHolder.getInstance().write(out);
            Message in = ConnectionHolder.getInstance().read();

            // Verify the comments
            if (in.getMessage().equals("error")) {
                errorLabel.setText((String) in.getData());
            } else {
                // Add the comments to the feed
                for (Comment comment : (ArrayList<Comment>) in.getData()) {
                    CommentComponent commentComponent = new CommentComponent(comment);
                    feed.addBottom(commentComponent);
                }
            }
        } catch (SocketException e) {
            FXMLUtils.loadNewView("views/no-connection.fxml", feed.getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void submitComment() {
        // Don't send the comment if it is blank
        if (commentInput.getText().isBlank()) {
            errorLabel.setText("Please enter a comment");
            return;
        }

        try {
            // Request to create the comment
            Message<CreateCommentForm> out = new Message<>(
                    "create comment",
                    new CreateCommentForm(commentInput.getText(), post.getId())
            );
            ConnectionHolder.getInstance().write(out);
            Message in = ConnectionHolder.getInstance().read();

            // Verify data
            if (in.getMessage().equals("error")) {
                errorLabel.setText((String) in.getData());
            } else {
                reload();
            }
        } catch (SocketException e) {
            FXMLUtils.loadNewView("views/no-connection.fxml", feed.getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
