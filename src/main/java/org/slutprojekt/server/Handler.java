package org.slutprojekt.server;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.slutprojekt.shared.SocketConnection;
import org.slutprojekt.shared.models.*;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

public class Handler implements Runnable {
    private SocketConnection socketConnection;
    private Connection dbConnection;
    private User user;
    Set<String> currentUsers;

    public Handler(Socket socket, Connection dbConnection, Set<String> currentUsers) throws IOException {
        socketConnection = new SocketConnection(socket);
        this.dbConnection = dbConnection;
        this.currentUsers = currentUsers;
    }

    @Override
    public void run() {
        try {
            // Wait for a message to arrive, handle it, repeat
            while (true) {
                Message in = socketConnection.read();
                Message out = handleMessageIn(in);
                socketConnection.write(out);
            }
        } catch (SocketException e) {
            if (user != null) {
                currentUsers.remove(user.getUsername());
            }
            System.out.println("A client disconnected");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Message handleMessageIn(Message in) {
        // The message must exist
        if (in == null) {
            return new Message<>("error", "The message in is null");
        }

        try {
            // Do the relevant logic dependent on the command
            return switch (in.getMessage()) {
                case "login" -> login((LoginForm) in.getData());
                case "signup" -> signup((LoginForm) in.getData());
                case "get feed" -> getFeed();
                default -> new Message<>("error", "Unknown command");
            };
        } catch (ClassCastException e) {
            // If the data could not be cast to the needed datatype, an error is returned
            return new Message<>("error", "Wrong datatype for the command");
        }
    }

    // Logs into an exising user not currently in use
    // Fails if loginForm is empty, the user doesn't exist, the password is wrong or the user currently is in use
    // If it fails an error message is returned
    // If successful an ok is returned
    private Message<String> login(LoginForm loginForm) {
        // Username and password must exist
        if (loginForm.getUsername() == null || loginForm.getPassword() == null ||
                loginForm.getUsername().isBlank() || loginForm.getPassword().isBlank()) {
            return new Message<>("error", "Username and password are required");
        }

        // User cannot be logged in already
        if (currentUsers.contains(loginForm.getUsername())) {
            return new Message<>("error", "That account is currently being used");
        }

        try {
            Statement statement = dbConnection.createStatement();

            // DB must contain the user that is being logged in to
            ResultSet results = statement.executeQuery("SELECT UserID, PasswordHash FROM Users WHERE Username = '" + loginForm.getUsername() + "';");
            if (!results.next()) {
                return new Message<>("error", "Username not found");
            }

            // Validate password
            String dbPasswordHash = results.getString("PasswordHash");
            BCrypt.Result result = BCrypt.verifyer().verify(loginForm.getPassword().toCharArray(), dbPasswordHash);
            if (!result.verified) {
                return new Message<>("error", "Password does not match");
            }

            // Login to the user
            System.out.println("Logged in a user");
            user = new User(results.getInt("UserID"), loginForm.getUsername());
            currentUsers.add(loginForm.getUsername());
            return new Message<>("ok", "");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message<>("error", "A database error occurred");
        }
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

        // Username cannot be more than 20 chars
        if (signupForm.getUsername().length() > 20) {
            return new Message<>("error", "Username is too long, max 20");
        }

        try {
            Statement statement = dbConnection.createStatement();

            // Don't allow duplicate usernames
            ResultSet results = statement.executeQuery("SELECT * FROM Users WHERE Username = '" + signupForm.getUsername() + "';");
            if (results.next()) {
                return new Message<>("error", "That username is taken");
            }

            // Create the user in the db with a hashed password for security
            String passwordHash = BCrypt.withDefaults().hashToString(12, signupForm.getPassword().toCharArray());
            statement.execute("INSERT INTO Users(Username, PasswordHash) VALUES('" + signupForm.getUsername() + "', '" + passwordHash + "');");

            // Get the id of the newly created user
            results = statement.executeQuery("SELECT UserID FROM Users WHERE Username = '" + signupForm.getUsername() + "';");
            results.next();

            // Login to the user
            System.out.println("Signed up a new user");
            user = new User(results.getInt("UserID"), signupForm.getUsername());
            currentUsers.add(signupForm.getUsername());
            return new Message<>("ok", "");
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message<>("error", "A database error occurred");
        }
    }

    // Gets all posts in the feed
    // Fails if the database fails
    private Message getFeed() {
        try  {
            // Get posts from db
            Statement postStatement = dbConnection.createStatement();
            ResultSet results = postStatement.executeQuery("SELECT * FROM Posts;");

            // Create an array of posts to send back to the user
            ArrayList<Post> posts = new ArrayList<>();
            while (results.next()) {
                // The creator is needed
                int creatorID = results.getInt("UserID");
                Statement userStatement = dbConnection.createStatement();
                ResultSet userResults = userStatement.executeQuery("SELECT * FROM Users WHERE UserID = " + creatorID);
                User user = new User(creatorID, userResults.getString("Username"));

                // Get the data from the post
                int postID = results.getInt("PostID");
                String header = results.getString("Header");
                String body = results.getString("Body");

                // Differentiate between short and long posts, the body part only exists in long posts
                if (body == null) {
                    posts.add(new ShortPost(postID, user, header));
                } else {
                    posts.add(new LongPost(postID, user, header, body));
                }
            }
            return new Message<>("ok", posts);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Message<>("error", "A database error occurred");
        }
    }
}
