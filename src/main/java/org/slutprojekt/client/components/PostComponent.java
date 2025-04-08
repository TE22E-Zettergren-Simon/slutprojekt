package org.slutprojekt.client.components;

import org.slutprojekt.client.FXMLUtils;
import org.slutprojekt.client.state.CurrentPostHolder;
import org.slutprojekt.shared.models.User;

public abstract class PostComponent extends ContentComponent {
    public PostComponent(User creator, int postID) {
        super(creator);

        //TODO: Go to a new view where the post and comments are displayed
        setOnMouseClicked(e -> {
            CurrentPostHolder.getInstance().setPostID(postID);
            FXMLUtils.loadNewView("views/post.fxml", getScene());
        });
    }
}
