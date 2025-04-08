module org.slutprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.instrument;
    requires bcrypt;
    requires java.sql;


    opens org.slutprojekt.client to javafx.fxml;
    exports org.slutprojekt.client;

    opens org.slutprojekt.client.components to javafx.fxml;
    exports org.slutprojekt.client.components;

    opens org.slutprojekt.client.controllers to javafx.fxml;
    exports org.slutprojekt.client.controllers;
    exports org.slutprojekt.client.state;
    opens org.slutprojekt.client.state to javafx.fxml;
}