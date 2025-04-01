package org.slutprojekt.server;

import org.slutprojekt.shared.SocketConnection;
import org.slutprojekt.shared.models.Message;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

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
                    socketConnection.close();
                    System.out.println("A client disconnected");
                    break;
                }
                socketConnection.write(message);
                System.out.println("Echoed: " + message.getMessage());
            }
        } catch (SocketException e) {
            System.out.println("Lost connection to a client");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
