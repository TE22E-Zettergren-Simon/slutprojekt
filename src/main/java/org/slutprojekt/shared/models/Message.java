package org.slutprojekt.shared.models;

import java.io.Serializable;

public class Message<T> implements Serializable {
    private String message;
    private T data;

    public Message(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
