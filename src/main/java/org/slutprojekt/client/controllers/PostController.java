package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.slutprojekt.client.components.CommentComponent;
import org.slutprojekt.client.components.Feed;
import org.slutprojekt.client.components.LongPostComponent;
import org.slutprojekt.client.components.PostComponent;
import org.slutprojekt.shared.models.*;

public class PostController {
    @FXML
    private VBox root;
    @FXML
    private Feed<CommentComponent> feed;

    @FXML
    private void initialize() {
        //TODO: Get data from server
        //TODO: Get the specific post from some state, not always the same
        PostComponent post = new LongPostComponent(new LongPost(
                1,
                new User(1, "Internet user"),
                "Something brave",
                "A bunch of stuff, blah blah blah blah blah blah blah"
        ));
        if (post instanceof LongPostComponent) {
            ((LongPostComponent) post).extend();
            System.out.println("Doing stuff");
        }
        root.getChildren().addFirst(post);

        User user = new User(0, "Somebody");
        feed.addTop(new CommentComponent(new Comment(
                0,
                user,
                new ShortPost(0, user, "Something"),
                "Yep, checks out"
        )));
    }
}
