package org.slutprojekt.client.components;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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
