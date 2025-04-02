package org.slutprojekt.server;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
            // Wait for a message to arrive, handle it, repeat
            while (true) {
                Message in = socketConnection.read();
                Message out;
                // Check the command and verify the provided data
                switch (in.getMessage()) {
                    case "login":
                        if (in.getData() instanceof LoginForm) {
                            out = login((LoginForm) in.getData());
                        } else {
                            out = new Message<>("error", "Wrong datatype for the command");
                        }
                        socketConnection.write(out);
                        break;
                    case "signup":
                        if (in.getData() instanceof LoginForm) {
                            out = signup((LoginForm) in.getData());
                        } else {
                            out = new Message<>("error", "Wrong datatype for the command");
                        }
                        socketConnection.write(out);
                        break;
                    default:
                        out = new Message<>("error", "Unknown command");
                        socketConnection.write(out);
                        break;
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

    // Logs into an exising user not currently in use
    // Fails if loginForm is empty, the user doesn't exist, the password is wrong or the user currently is in use
    // If it fails an error message is returned
    // If successful an ok is returned
    //TODO: make sure the user isn't currently logged in
    private Message<String> login(LoginForm loginForm) {
        // Username and password must exist
        if (loginForm.getUsername() == null || loginForm.getPassword() == null ||
                loginForm.getUsername().isBlank() || loginForm.getPassword().isBlank()) {
            return new Message<>("error", "Username and password are required");
        }

        // DB must contain the user that is being logged in to
        if (!mockDB.containsKey(loginForm.getUsername())) {
            return new Message<>("error", "Username not found");
        }

        // Validate password
        String dbPasswordHash = mockDB.get(loginForm.getUsername());
        BCrypt.Result result = BCrypt.verifyer().verify(loginForm.getPassword().toCharArray(), dbPasswordHash);
        if (!result.verified) {
            return new Message<>("error", "Password does not match");
        }

        // Login to the user
        //TODO: id should not always be zero, correct implementation when real db exists
        System.out.println("Logged in a user");
        user = new User(0, loginForm.getUsername());
        return new Message<>("ok", "");
    }

    // Create a new user in the db and log into it
    // Fails if signupForm is empty or the username is taken
    // If it fails an error message is returned
    // If successful an ok is returned
    private Message<String> signup(LoginForm signupForm) {
        // Username and password must exist
        if (signupForm.getUsername() == null || signupForm.getPassword() == null ||
                signupForm.getUsername().isBlank() || signupForm.getPassword().isBlank()) {
            return new Message<>("error", "Username and password are required");
        }

        // Don't allow duplicate usernames
        if (mockDB.containsKey(signupForm.getUsername())) {
            return new Message<>("error", "That username is taken");
        }

        // Create the user in the db with a hashed password for security
        String passwordHash = BCrypt.withDefaults().hashToString(12, signupForm.getPassword().toCharArray());
        mockDB.put(signupForm.getUsername(), passwordHash);

        // Login to the user
        System.out.println("Signed up a new user");
        user = new User(0, signupForm.getUsername());
        return new Message<>("ok", "");
    }
}
