package org.slutprojekt.client.components;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Feed<T extends Node> extends VBox {
    private ArrayList<T> items = new ArrayList<>();

    public void addMultiple(ArrayList<T> newItems) {
        items.addAll(newItems);
        getChildren().addAll(newItems);
    }

    public void addTop(T newItem) {
        items.addFirst(newItem);
        getChildren().addFirst(newItem);
    }

    public void addBottom(T newItem) {
        items.add(newItem);
        getChildren().add(newItem);
    }
}
