package org.slutprojekt.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (Server server = new Server()) {
            server.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
