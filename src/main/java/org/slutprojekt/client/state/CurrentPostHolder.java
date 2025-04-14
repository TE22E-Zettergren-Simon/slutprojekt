package org.slutprojekt.client.state;

import org.slutprojekt.shared.models.Post;

public class CurrentPostHolder {
    private static CurrentPostHolder instance = new CurrentPostHolder();
    private Post post;

    private CurrentPostHolder() {}

    public static CurrentPostHolder getInstance() {
        return instance;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
