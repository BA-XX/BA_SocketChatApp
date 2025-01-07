module ba.socketchatapp.ba_socketchatapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens ba.socketchatapp.ba_socketchatapp to javafx.fxml;
    exports ba.socketchatapp.ba_socketchatapp;
}