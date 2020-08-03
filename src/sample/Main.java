package sample;

import dataModel.ContactData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Contacts");
        primaryStage.setScene(new Scene(root, 900, 680));
        primaryStage.show();
    }


    @Override
    public void stop() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit application");
        alert.setHeaderText("Are you sure you want to exit so soon?");
        alert.setContentText("Press OK to exit, or press cancel");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == (ButtonType.OK)){
            ContactData.getInstance().saveContacts();
            Platform.exit();
        }

    }

    @Override
    public void init() {
        ContactData.getInstance().loadContacts();
    }
}
