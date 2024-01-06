module com.jaymash.weatherapp {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;

    opens com.jaymash.weatherapp to javafx.fxml;
    opens com.jaymash.weatherapp.controllers to javafx.fxml;
    exports com.jaymash.weatherapp;
    exports com.jaymash.weatherapp.controllers;
    exports com.jaymash.weatherapp.models;
}