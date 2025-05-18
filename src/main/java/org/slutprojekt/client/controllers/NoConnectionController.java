package org.slutprojekt.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.slutprojekt.client.state.ConnectionHolder;
import org.slutprojekt.client.FXMLUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

// Controller for the no connection screen (no-connection.fxml)
// The user is directed here if the connection to the server ever fails
// Allows the user to try to re-establish a connection to the server
public class NoConnectionController {
    @FXML
    private Label infoLabel;

    @FXML
    private void tryReconnecting() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", 8080));
            ConnectionHolder.getInstance().connect(socket);
        } catch (IOException e) {
            infoLabel.setText("Failed to reconnect");
            return;
        }

        FXMLUtils.loadNewView("views/start.fxml", infoLabel.getScene());
    }
}
