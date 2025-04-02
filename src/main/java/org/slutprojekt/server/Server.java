package org.slutprojekt.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements AutoCloseable {
    private ExecutorService pool = Executors.newFixedThreadPool(20);
    private ServerSocket serverSocket;
    private Connection dbConnection;

    public Server() throws IOException {
        serverSocket = new ServerSocket();
    }

    public void run() throws Exception {
        // Create and start the server
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8080));
        System.out.println("Server started");

        // Connect to database
        String dburl = "jdbc:sqlite:db.db";
        dbConnection = DriverManager.getConnection(dburl);

        // Create the Users table if it doesn't exist
        Statement statement = dbConnection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Users ("
                + "UserID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Username VARCHAR(20) NOT NULL,"
                + "PasswordHash VARCHAR(64) NOT NULL);");

        // Continuously accept clients and dispatch threads to handle them
        while (true) {
            Socket socket = serverSocket.accept();
            pool.execute(new Handler(socket, dbConnection));
            System.out.println("Connected with a client");
        }
    }

    public void close() throws IOException {
        serverSocket.close();
        pool.shutdown();
    }
}
