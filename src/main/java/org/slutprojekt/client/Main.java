package org.slutprojekt.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 8080));
        ConnectionHolder.getInstance().connect(socket);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Skibider");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
