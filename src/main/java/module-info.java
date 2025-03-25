module org.slutprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.slutprojekt.client.components to javafx.fxml;
    exports org.slutprojekt.client.components;

    opens org.slutprojekt.client.controllers to javafx.fxml;
    exports org.slutprojekt.client.controllers;
}