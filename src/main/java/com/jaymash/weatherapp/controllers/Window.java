package com.jaymash.weatherapp.controllers;

import com.jaymash.weatherapp.dialogs.MessageDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Window {

    public static int WINDOW_WIDTH = 960;
    public static int WINDOW_HEIGHT = 580;
    public static double TITLE_BAR_HEIGHT = 40;

    private Stage stage;
    private AnchorPane root;
    private HBox titleBar;
    private AnchorPane content;

    private double xOffset = 0;
    private double yOffset = 0;

    private void create() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/window.fxml"));
            root = (AnchorPane) fxmlLoader.load();
            titleBar = (HBox) root.lookup("#titleBar");
            titleBar.setPrefSize(WINDOW_WIDTH, TITLE_BAR_HEIGHT);
            titleBar.toFront();

            Text appTitle = (Text) root.lookup("#appTitle");
            Button iconifyButton = (Button) root.lookup("#iconifyButton");
            Button closeButton = (Button) root.lookup("#closeButton");
            content = (AnchorPane) root.lookup("#content");

            content.setPrefWidth(WINDOW_WIDTH);
            AnchorPane.setTopAnchor(content, TITLE_BAR_HEIGHT);
            appTitle.setText("WeatherApp");

            iconifyButton.setOnMouseClicked((MouseEvent ev) -> {
                stage.setIconified(true);
            });

            closeButton.setOnMouseClicked((MouseEvent ev) -> {
                stage.close();
            });

            titleBar.setOnMousePressed((MouseEvent event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            titleBar.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

            stage.setScene(new Scene(root));
        } catch (Exception ex) {
            new MessageDialog(stage, "Error", ex.getMessage()).show();
        }
    }

    public final AnchorPane getRoot() {
        return root;
    }

    public final HBox getTitleBar() {
        return titleBar;
    }

    public final AnchorPane getContent() {
        return content;
    }

    public Window(Stage stage) {
        this.stage = stage;
        create();
    }

}
