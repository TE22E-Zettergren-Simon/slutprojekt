package org.slutprojekt.shared.models;

import java.io.Serializable;

public class ShortPost extends Post {
    private String content;

    public ShortPost(int id, User creator, String content) {
        super(id, creator);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
