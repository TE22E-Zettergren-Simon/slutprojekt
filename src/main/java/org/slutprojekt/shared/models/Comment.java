package org.slutprojekt.shared.models;

import java.io.Serializable;

public class Comment implements Serializable {
    private final int id;
    private final User creator;
    private final Post post;
    private final String content;

    public Comment(int id, User creator, Post post, String content) {
        this.id = id;
        this.creator = creator;
        this.post = post;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public Post getPost() {
        return post;
    }

    public String getContent() {
        return content;
    }
}
