package org.slutprojekt.client.components;

import javafx.scene.control.Label;
import org.slutprojekt.shared.models.ShortPost;

public class ShortPostComponent extends PostComponent {
    private ShortPost post;

    public ShortPostComponent(ShortPost post) {
        super(post.getCreator(), post.getId());
        this.post = post;

        Label contentLabel = new Label(post.getContent());
        getChildren().add(contentLabel);
    }
}
