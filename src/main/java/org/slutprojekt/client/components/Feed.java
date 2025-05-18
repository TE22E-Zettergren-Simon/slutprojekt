package org.slutprojekt.client.components;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

// A list of nodes displayed in a vertical fashion
// Used for the main post feed and all comment feeds
public class Feed<T extends Node> extends ScrollPane {
    private final VBox vbox = new VBox();

    public Feed() {
        setContent(vbox);
    }

    public void addTop(T newItem) {
        vbox.getChildren().addFirst(newItem);
    }

    public void addBottom(T newItem) {
        vbox.getChildren().add(newItem);
    }

    public void clear() {
        vbox.getChildren().clear();
    }
}
