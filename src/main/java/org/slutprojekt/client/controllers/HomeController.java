package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import org.slutprojekt.client.components.Feed;
import org.slutprojekt.client.components.LongPostComponent;
import org.slutprojekt.client.components.PostComponent;
import org.slutprojekt.client.components.ShortPostComponent;
import org.slutprojekt.shared.models.LongPost;
import org.slutprojekt.shared.models.ShortPost;
import org.slutprojekt.shared.models.User;

public class HomeController {
    @FXML
    private Feed<PostComponent> feed;

    @FXML
    private void initialize() {
        //feed.setMaxWidth(300.0);
        //TODO: Get data from the server
        feed.addTop(new ShortPostComponent(new ShortPost(
                0,
                new User(0, "Simon"),
                "Something profound"
        )));
        feed.addTop(new LongPostComponent(new LongPost(
                1,
                new User(1, "Internet user"),
                "Something brave",
                "A bunch of stuff, blah blah blah blah blah blah blah"
        )));
    }
}
