package org.slutprojekt.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements AutoCloseable {
    private ExecutorService pool = Executors.newFixedThreadPool(20);
    private ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket();
    }

    public void run() throws IOException {
        // Create and start the server
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8080));
        System.out.println("Server started");

        //TODO: Replace with an sqlite database
        HashMap<String, String> mockDB = new HashMap<>();

        // Continuously accept clients and dispatch threads to handle them
        while (true) {
            Socket socket = serverSocket.accept();
            pool.execute(new Handler(socket, mockDB));
            System.out.println("Connected with a client");
        }
    }

    public void close() throws IOException {
        serverSocket.close();
        pool.shutdown();
    }
}
