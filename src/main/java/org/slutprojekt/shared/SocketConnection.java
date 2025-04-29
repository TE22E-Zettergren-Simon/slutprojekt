package org.slutprojekt.shared;

import org.slutprojekt.shared.models.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// A connection over a socket, methods to read and write Messages easily
public class SocketConnection {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public SocketConnection(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void write(Message message) throws IOException {
        out.writeObject(message);
    }

    public Message read() throws IOException, ClassNotFoundException {
        Object obj = in.readObject();
        if (!(obj instanceof Message)) {
            throw new IOException("Unusable input type");
        }
        return (Message) obj;
    }
}
