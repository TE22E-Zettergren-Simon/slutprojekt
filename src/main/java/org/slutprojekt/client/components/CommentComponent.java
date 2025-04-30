package org.slutprojekt.client.components;

import javafx.scene.control.Label;
import org.slutprojekt.shared.models.Comment;

public class CommentComponent extends ContentComponent{
    private final Comment comment;

    public CommentComponent(Comment comment) {
        super(comment.getCreator());
        this.comment = comment;

        Label contentLabel = new Label(comment.getContent());
        getChildren().add(contentLabel);
    }
}
