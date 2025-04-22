package org.slutprojekt.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public Server(String host, int port, String dburl) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(20);
        Set<String> currentUsers = Collections.synchronizedSet(new HashSet<>());

        // Connect to database
        Connection dbConnection = DriverManager.getConnection(dburl);

        // Create the Users, Posts and Comments table if it doesn't exist
        Statement statement = dbConnection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS Users ("
                + "UserID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Username VARCHAR(20) NOT NULL,"
                + "PasswordHash VARCHAR(64) NOT NULL);");
        statement.execute("CREATE TABLE IF NOT EXISTS Posts ("
                + "PostID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Header VARCHAR(200) NOT NULL,"
                + "Body VARCHAR(2000),"
                + "UserID INTEGER NOT NULL,"
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID));");
        statement.execute("CREATE TABLE IF NOT EXISTS Comments ("
                + "CommentID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Content VARCHAR(200) NOT NULL,"
                + "UserID INTEGER NOT NULL,"
                + "PostID INTEGER NOT NULL,"
                + "FOREIGN KEY (UserID) REFERENCES Users(UserID)"
                + "FOREIGN KEY (PostID) REFERENCES Posts(PostID));");

        // Create and start the server
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(host, port));
        System.out.println("Server started");

        // Continuously accept clients and dispatch threads to handle them
        while (true) {
            Socket socket = serverSocket.accept();
            pool.execute(new Handler(socket, dbConnection, currentUsers));
            System.out.println("Connected with a client");
        }
    }
}
