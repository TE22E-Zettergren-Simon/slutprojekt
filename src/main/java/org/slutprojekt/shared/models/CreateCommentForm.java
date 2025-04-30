package org.slutprojekt.shared.models;

import java.io.Serializable;

public class CreateCommentForm implements Serializable {
    private final String content;
    private final int postID;

    public CreateCommentForm(String content, int postID) {
        this.content = content;
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public int getPostID() {
        return postID;
    }
}
