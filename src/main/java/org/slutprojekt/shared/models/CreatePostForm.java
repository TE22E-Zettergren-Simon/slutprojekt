package org.slutprojekt.shared.models;

import java.io.Serializable;

public class CreatePostForm implements Serializable {
    private final String header;
    private final String body;

    public CreatePostForm(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
