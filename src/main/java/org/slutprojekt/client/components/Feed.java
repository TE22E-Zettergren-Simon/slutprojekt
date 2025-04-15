package org.slutprojekt.client.components;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Feed<T extends Node> extends ScrollPane {
    private VBox vbox = new VBox();
    private ArrayList<T> items = new ArrayList<>();

    public Feed() {
        setContent(vbox);
    }

    public void addTop(T newItem) {
        items.addFirst(newItem);
        vbox.getChildren().addFirst(newItem);
    }

    public void addBottom(T newItem) {
        items.add(newItem);
        vbox.getChildren().add(newItem);
    }
}
