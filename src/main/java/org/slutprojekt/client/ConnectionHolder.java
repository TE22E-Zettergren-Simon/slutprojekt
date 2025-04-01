package org.slutprojekt.client;

import org.slutprojekt.shared.SocketConnection;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHolder {
    private static final ConnectionHolder instance = new ConnectionHolder();
    private SocketConnection socketConnection;

    private ConnectionHolder() {}

    public static ConnectionHolder getInstance() {
        return instance;
    }

    public SocketConnection getSocketConnection() {
        return socketConnection;
    }

    public void connect(Socket socket) throws IOException {
        socketConnection = new SocketConnection(socket);
    }
}
