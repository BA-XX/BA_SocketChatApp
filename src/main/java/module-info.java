module ba.socketchatapp.client {
    requires javafx.controls;
    requires javafx.fxml;


    exports ba.socketchatapp.client.presentation;
    opens ba.socketchatapp.client.presentation to javafx.fxml;
}