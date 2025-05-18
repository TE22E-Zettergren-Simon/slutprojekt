package org.slutprojekt.client.state;

import org.slutprojekt.shared.SocketConnection;
import org.slutprojekt.shared.models.Message;

import java.io.IOException;
import java.net.Socket;

// Holds the connection to the server
// Used to share the connection between controllers
public class ConnectionHolder {
    private static final ConnectionHolder instance = new ConnectionHolder();
    private SocketConnection socketConnection;

    private ConnectionHolder() {}

    public static ConnectionHolder getInstance() {
        return instance;
    }

    public Message read() throws IOException, ClassNotFoundException {
        return socketConnection.read();
    }

    public void write(Message message) throws IOException {
        socketConnection.write(message);
    }

    public void connect(Socket socket) throws IOException {
        socketConnection = new SocketConnection(socket);
    }
}
