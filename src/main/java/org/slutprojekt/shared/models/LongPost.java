package org.slutprojekt.shared.models;

public class LongPost extends Post {
    private String heading;
    private String body;

    public LongPost(int id, User creator, String heading, String body) {
        super(id, creator);
        this.heading = heading;
        this.body = body;
    }

    public String getHeading() {
        return heading;
    }

    public String getBody() {
        return body;
    }
}
