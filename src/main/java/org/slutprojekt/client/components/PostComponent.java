package org.slutprojekt.client.components;

import org.slutprojekt.client.FXMLUtils;
import org.slutprojekt.client.state.CurrentPostHolder;
import org.slutprojekt.shared.models.Post;

public abstract class PostComponent extends ContentComponent {
    public PostComponent(Post post) {
        super(post.getCreator());

        setOnMouseClicked(e -> {
            CurrentPostHolder.getInstance().setPost(post);
            FXMLUtils.loadNewView("views/post.fxml", getScene());
        });
    }

    public abstract void extend();
}
