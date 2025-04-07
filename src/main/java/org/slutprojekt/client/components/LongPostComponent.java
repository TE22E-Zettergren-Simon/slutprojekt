package org.slutprojekt.client.components;

import javafx.scene.control.Label;
import org.slutprojekt.shared.models.LongPost;

public class LongPostComponent extends PostComponent {
    private LongPost post;

    public LongPostComponent(LongPost post) {
        super(post.getCreator(), post.getId());
        this.post = post;

        Label headingLabel = new Label(post.getHeading());
        getChildren().add(headingLabel);

        Label infoLabel = new Label("See more... ");
        getChildren().add(infoLabel);
    }
}
