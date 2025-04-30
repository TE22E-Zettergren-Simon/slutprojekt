package org.slutprojekt.shared.models;

import java.io.Serializable;

public class Post implements Serializable {
    private final int id;
    private final User creator;

    public Post(int id, User creator) {
        this.id = id;
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }
}
