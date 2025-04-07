package org.slutprojekt.client.components;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slutprojekt.shared.models.User;

public abstract class ContentComponent extends VBox {
    protected User creator;

    public ContentComponent(User creator) {
        this.creator = creator;

        Label usernameLabel = new Label(creator.getUsername());
        getChildren().add(usernameLabel);
    }
}
