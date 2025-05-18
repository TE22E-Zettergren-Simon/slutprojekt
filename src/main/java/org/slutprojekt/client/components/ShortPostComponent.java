package org.slutprojekt.client.components;

import javafx.scene.control.Label;
import org.slutprojekt.shared.models.ShortPost;

// A reusable component displaying a short post
// When extended the styling changes a little
public class ShortPostComponent extends PostComponent {
    private final ShortPost post;

    public ShortPostComponent(ShortPost post) {
        super(post);
        this.post = post;

        Label contentLabel = new Label(post.getContent());
        getChildren().add(contentLabel);
    }

    @Override
    public void extend() {
        getStyleClass().add("extended");
    }
}
