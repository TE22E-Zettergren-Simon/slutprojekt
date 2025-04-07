package org.slutprojekt.client.components;

import org.slutprojekt.shared.models.User;

public abstract class PostComponent extends ContentComponent {
    public PostComponent(User creator, int postID) {
        super(creator);

        //TODO: Go to a new view where the post and comments are displayed
        setOnMouseClicked(e -> {
            System.out.println("Clicked post " + postID);
        });
    }
}
