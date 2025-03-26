package org.slutprojekt.server;

import org.slutprojekt.shared.SocketConnection;
import org.slutprojekt.shared.models.Message;

import java.io.IOException;
import java.net.Socket;

public class Handler implements Runnable {
    private SocketConnection socketConnection;

    public Handler(Socket socket) throws IOException {
        socketConnection = new SocketConnection(socket);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = socketConnection.read();
                if (message.getMessage().equalsIgnoreCase("exit")) {
                    System.out.println("Stopping thread");
                    break;
                }
                socketConnection.write(message);
                System.out.println("Echoed: " + message.getMessage());
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
