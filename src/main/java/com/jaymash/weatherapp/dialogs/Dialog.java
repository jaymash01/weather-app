package com.jaymash.weatherapp.dialogs;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

abstract public class Dialog {

    protected Stage parent;
    protected Stage stage;
    protected String title;
    protected String message;
    protected VBox content;
        
    public Dialog(Stage parent, String title, String message) {
        this.parent = parent;
        this.title = title;
        this.message = message;
    }
        
    public void show() {
        stage = new Stage();
        stage.setTitle(title);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parent);

        Image icon = new Image(getClass().getResource("/images/01d.png").toExternalForm());
        stage.getIcons().add(icon);

        createScene();
        stage.showAndWait();
    }

    abstract protected void createScene();
    
    abstract public void close();
        
}
