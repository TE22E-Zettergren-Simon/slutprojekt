package org.slutprojekt.client.state;

import org.slutprojekt.shared.models.Post;

// Holds the current post to be displayed
// So that the program "remembers" what post to display when switching from the home to post screen
public class CurrentPostHolder {
    private static final CurrentPostHolder instance = new CurrentPostHolder();
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
