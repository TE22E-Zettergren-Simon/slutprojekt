package org.slutprojekt.server;

import org.slutprojekt.shared.SocketConnection;
import org.slutprojekt.shared.models.LoginForm;
import org.slutprojekt.shared.models.Message;
import org.slutprojekt.shared.models.User;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class Handler implements Runnable {
    private SocketConnection socketConnection;
    private HashMap<String, String> mockDB;
    private User user;

    public Handler(Socket socket, HashMap<String, String> mockDB) throws IOException {
        socketConnection = new SocketConnection(socket);
        this.mockDB = mockDB;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = socketConnection.read();
                switch (message.getMessage()) {
                    case "login":
                        if (!(message.getData() instanceof LoginForm)) {
                            //TODO: respond with an error message to the client instead of crashing
                            throw new IllegalStateException("Wrong datatype for the command");
                        }
                        login((LoginForm) message.getData());
                        break;
                    case "signup":
                        if (!(message.getData() instanceof LoginForm)) {
                            //TODO: respond to the client instead of crashing
                            throw new IllegalStateException("Wrong datatype for the command");
                        }
                        signup((LoginForm) message.getData());
                        break;
                    default:
                        //TODO: respond to the client instead of crashing
                        throw new IllegalStateException("Unknown command");
                }

                // old stuff, might be wanted for testing
                //if (message.getMessage().equalsIgnoreCase("exit")) {
                //    socketConnection.close();
                //    System.out.println("A client disconnected");
                //    break;
                //}
                //socketConnection.write(message);
                //System.out.println("Echoed: " + message.getMessage());
            }
        } catch (SocketException e) {
            System.out.println("Lost connection to a client");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: replace all exceptions with messages to the client
    private void login(LoginForm loginForm) {
        // Username and password must exist
        if (loginForm.getUsername() == null || loginForm.getPassword() == null ||
                loginForm.getUsername().isBlank() || loginForm.getPassword().isBlank()) {
            throw new IllegalStateException("Username and password are required");
        }

        // DB must contain the user that is being logged in to
        if (!mockDB.containsKey(loginForm.getUsername())) {
            throw new IllegalStateException("Username not found");
        }

        // Validate password
        if (!mockDB.get(loginForm.getUsername()).equals(loginForm.getPassword())) {
            throw new IllegalStateException("Password does not match");
        }

        // Login to the user
        //TODO: id should not always be zero, correct implementation when real db exists
        user = new User(0, loginForm.getUsername());
    }

    //TODO: replace all exceptions with messages to the client
    private void signup(LoginForm signupForm) {
        // Username and password must exist
        if (signupForm.getUsername() == null || signupForm.getPassword() == null ||
                signupForm.getUsername().isBlank() || signupForm.getPassword().isBlank()) {
            throw new IllegalStateException("Username and password are required");
        }

        // Don't allow duplicate usernames
        if (mockDB.containsKey(signupForm.getUsername())) {
            throw new IllegalStateException("Username already exists");
        }

        // Create the user in the db
        mockDB.put(signupForm.getUsername(), signupForm.getPassword());

        // Login to the user
        user = new User(0, signupForm.getUsername());
    }
}
