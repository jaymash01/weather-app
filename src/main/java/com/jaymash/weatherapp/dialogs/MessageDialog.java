package com.jaymash.weatherapp.dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public final class MessageDialog extends Dialog {

    public MessageDialog(Stage parent, String heading, String message) {
        super(parent, heading, message);
    }

    @Override
    protected void createScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/message-dialog.fxml"));
            content = (VBox) fxmlLoader.load();
            Text dialogTitle = (Text) content.lookup("#dialogTitle");
            Text dialogMessage = (Text) content.lookup("#dialogMessage");
            Button closeButton = (Button) content.lookup("#closeDialogButton");

            dialogTitle.setText(title);
            dialogMessage.setText(message);
            closeButton.setOnAction((ActionEvent ev) -> close());
            stage.setScene(new Scene(content));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        stage.close();
    }

}
