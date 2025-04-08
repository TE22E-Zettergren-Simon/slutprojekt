package org.slutprojekt.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slutprojekt.client.state.ConnectionHolder;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene;
        try {
            // Attempts to connect to the server
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", 8080));
            ConnectionHolder.getInstance().connect(socket);

            // If connected, loads the start view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/start.fxml"));
            scene = new Scene(fxmlLoader.load());
        } catch (SocketException e) {
            // If not connected, loads an info view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/no-connection.fxml"));
            scene = new Scene(fxmlLoader.load());
        }

        // Shows the loaded view to the user
        stage.setScene(scene);
        stage.setTitle("Skibider");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
