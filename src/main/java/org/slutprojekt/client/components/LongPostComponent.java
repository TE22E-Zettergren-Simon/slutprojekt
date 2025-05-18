package org.slutprojekt.client.components;

import javafx.scene.control.Label;
import org.slutprojekt.shared.models.LongPost;

// Can be extended to show the whole post, not just the heading
// So that the post does not take up too much space on the main feed
public class LongPostComponent extends PostComponent {
    private final LongPost post;

    public LongPostComponent(LongPost post) {
        super(post);
        this.post = post;

        Label headingLabel = new Label(post.getHeading());
        getChildren().add(headingLabel);

        Label infoLabel = new Label("See more... ");
        getChildren().add(infoLabel);
    }

    @Override
    public void extend() {
        ((Label) getChildren().getLast()).setText(post.getBody());
        getStyleClass().add("extended");
    }
}
