package com.jaymash.weatherapp;

import com.google.gson.Gson;
import com.jaymash.weatherapp.controllers.IndexController;
import com.jaymash.weatherapp.controllers.SettingsController;
import com.jaymash.weatherapp.controllers.Window;
import com.jaymash.weatherapp.dialogs.Dialog;
import com.jaymash.weatherapp.dialogs.MessageDialog;
import com.jaymash.weatherapp.models.Preferences;
import com.jaymash.weatherapp.utils.FileUtils;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;

public class App extends Application {

    private Stage stage;
    private Window window;
    private Preferences preferences;

    @Override
    public void start(Stage stage) {
        stage.setTitle("WeatherApp");
        stage.setWidth(Window.WINDOW_WIDTH);
        stage.setHeight(Window.WINDOW_HEIGHT);
        stage.initStyle(StageStyle.UNDECORATED);
        centerStage(stage, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);

        Image icon = new Image(getClass().getResource("/images/sun-64.png").toExternalForm());
        stage.getIcons().add(icon);
        loadPreferences();

        this.stage = stage;
        window = new Window(this.stage);
        this.stage.show();
        gotoIndex();
    }

    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }

    private void loadPreferences() {
        try (InputStream inputStream = getClass().getResourceAsStream("/preferences.json")) {
            preferences = new Gson().fromJson(FileUtils.readFromInputStream(inputStream), Preferences.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Initializable createScene(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane root = (AnchorPane) fxmlLoader.load();

            // clear window content pane and add new children
            ObservableList<Node> windowContent = window.getContent().getChildren();
            windowContent.clear();
            windowContent.addAll(root.getChildren());
            window.getTitleBar().toFront();
            return (Initializable) fxmlLoader.getController();
        } catch (Exception ex) {
            Dialog dialog = new MessageDialog(stage, "Error", ex.getMessage());
            dialog.show();
        }

        return null;
    }

    public void gotoIndex() {
        IndexController controller = (IndexController) createScene("/fxml/index.fxml");

        if (controller != null) {
            controller.setApp(this);
            controller.setStage(stage);
            controller.initialize();
        }
    }

    public void gotoSettings() {
        SettingsController controller = (SettingsController) createScene("/fxml/settings.fxml");

        if (controller != null) {
            controller.setApp(this);
            controller.setStage(stage);
            controller.initialize();
        }
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public static void main(String[] args) {
        launch(args);
    }

}