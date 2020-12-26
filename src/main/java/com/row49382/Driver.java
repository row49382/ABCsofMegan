package com.row49382;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.row49382.controllers.DictionaryController;
import com.row49382.models.MeganReadonlyDictionary;
import com.row49382.models.ReadonlyDictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Driver extends Application {
    private static final String TITLE = "=== ABCs of Megan Larson ===";
    private static final String PATH_TO_JSON_DICTIONARY_FILE = System.getProperty("user.dir") + "\\src\\main\\resources\\json\\MeganDictionary.json";

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DictionaryView.fxml"));
            Parent mainPane = loader.load();

            ReadonlyDictionary dictionary = new MeganReadonlyDictionary(new ObjectMapper(), PATH_TO_JSON_DICTIONARY_FILE);
            DictionaryController controller = loader.getController();
            controller.setDictionary(dictionary);

            Scene scene = new Scene(mainPane);
            scene.getStylesheets().add("/styling/FxmlStyling.css");

            primaryStage.setTitle(TITLE);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
            error.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
