package org.slutprojekt.client.state;

public class CurrentPostHolder {
    private static CurrentPostHolder instance = new CurrentPostHolder();
    private int postID;

    private CurrentPostHolder() {}

    public static CurrentPostHolder getInstance() {
        return instance;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
