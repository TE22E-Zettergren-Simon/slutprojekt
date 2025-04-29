package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.slutprojekt.client.FXMLUtils;
import org.slutprojekt.client.components.Feed;
import org.slutprojekt.client.components.LongPostComponent;
import org.slutprojekt.client.components.PostComponent;
import org.slutprojekt.client.components.ShortPostComponent;
import org.slutprojekt.client.state.ConnectionHolder;
import org.slutprojekt.shared.models.*;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;

public class HomeController {
    @FXML
    private Label errorLabel;
    @FXML
    private Feed<PostComponent> feed;

    @FXML
    private void initialize() {
        reload();
    }

    @FXML
    private void logout() {
        try {
            // Try to log out
            Message out = new Message("logout", null);
            ConnectionHolder.getInstance().write(out);
            Message in = ConnectionHolder.getInstance().read();

            // If logging out went wrong, print something, it should never happen
            if (in.getMessage().equals("error")) {
                errorLabel.setText("Failed to logout");
            }

            // Otherwise, go to start screen
            FXMLUtils.loadNewView("views/start.fxml", feed.getScene());
        } catch (SocketException e) {
            FXMLUtils.loadNewView("views/no-connection.fxml", feed.getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void toCreatePost() {
        FXMLUtils.loadNewView("views/create-post.fxml", feed.getScene());
    }

    // Empties the feed and queries the server for the newest feed
    @FXML
    private void reload() {
        feed.clear();
        try {
            // Get the feed
            Message out = new Message("get feed", null);
            ConnectionHolder.getInstance().write(out);
            Message in = ConnectionHolder.getInstance().read();

            // Verify the data
            if (in.getMessage().equals("error")) {
                errorLabel.setText((String) in.getData());
            } else {
                // Add the posts
                for (Post post : (ArrayList<Post>) in.getData()) {
                    if (post instanceof ShortPost) {
                        feed.addTop(new ShortPostComponent((ShortPost) post));
                    } else {
                        feed.addTop(new LongPostComponent((LongPost) post));
                    }
                }
            }
        } catch (SocketException e) {
            FXMLUtils.loadNewView("views/no-connection.fxml", feed.getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
