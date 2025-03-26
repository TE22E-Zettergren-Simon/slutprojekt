package org.slutprojekt.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (Server server = new Server()) {
            server.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
