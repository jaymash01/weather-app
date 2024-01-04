module com.jaymash.weatherapp {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires retrofit2;
    requires okhttp3;
    requires retrofit2.converter.gson;
    requires okhttp3.logging;

    opens com.jaymash.weatherapp to javafx.fxml;
    opens com.jaymash.weatherapp.controllers to javafx.fxml;
    exports com.jaymash.weatherapp;
    exports com.jaymash.weatherapp.controllers;
    exports com.jaymash.weatherapp.models;
}