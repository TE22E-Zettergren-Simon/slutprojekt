package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
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
    private Feed<PostComponent> feed;

    @FXML
    private void initialize() {
        reload();
    }

    // Empties the feed and queries the server for the newest feed
    @FXML
    private void reload() {
        feed.clear();
        try {
            // Get the feed
            Message out = new Message("get feed", null);
            ConnectionHolder.getInstance().getSocketConnection().write(out);
            Message in = ConnectionHolder.getInstance().getSocketConnection().read();

            // Verify the data
            if (in == null) {
                return;
            }
            if (!(in.getData() instanceof ArrayList)) {
                System.out.println(in.getData());
                return;
            }

            // Add the posts
            for (Post post: (ArrayList<Post>) in.getData()) {
                if (post instanceof ShortPost) {
                    feed.addTop(new ShortPostComponent((ShortPost) post));
                } else {
                    feed.addTop(new LongPostComponent((LongPost) post));
                }
            }
        } catch (SocketException e) {
            FXMLUtils.loadNewView("views/no-connection.fxml", feed.getScene());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
