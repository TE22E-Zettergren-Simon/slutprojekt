package org.slutprojekt.server;

import org.slutprojekt.shared.SocketConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements AutoCloseable {
    private ExecutorService pool = Executors.newFixedThreadPool(20);
    private ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket();
    }

    public void run() throws IOException {
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8080));
        System.out.println("Server started");

        while (true) {
            System.out.println("Waiting for connection");
            Socket socket = serverSocket.accept();
            pool.execute(new Handler(socket));
            System.out.println("Connected with a client");
        }
    }

    public void close() throws IOException {
        serverSocket.close();
        pool.shutdown();
    }
}
