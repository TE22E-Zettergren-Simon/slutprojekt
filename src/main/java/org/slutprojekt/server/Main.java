package org.slutprojekt.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        new Server("localhost", 8080, "jdbc:sqlite:db.db");
    }
}
