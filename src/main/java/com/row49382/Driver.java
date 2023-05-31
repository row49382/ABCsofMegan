package com.row49382;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXDecorator;
import com.row49382.controllers.DictionaryController;
import com.row49382.models.DictionaryEntry;
import com.row49382.models.MeganReadonlyDictionary;
import com.row49382.models.ReadonlyDictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Driver extends Application {
    private static final String TITLE = "=== ABCs of Megan Larson ===";
    private static final String PATH_TO_JSON_DICTIONARY_FILE = System.getProperty("user.dir") + "\\src\\main\\resources\\json\\MeganDictionary.json";

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DictionaryView.fxml"));
            Parent mainPane = loader.load();

            ReadonlyDictionary<String, DictionaryEntry> dictionary = new MeganReadonlyDictionary(
                    new ObjectMapper(),
                    PATH_TO_JSON_DICTIONARY_FILE);

            DictionaryController controller = loader.getController();
            controller.setDictionary(dictionary);

            JFXDecorator decorator = new JFXDecorator(primaryStage, mainPane);
            decorator.setCustomMaximize(true);
            Scene scene = new Scene(decorator, 800 ,500);
            scene.getStylesheets().add("/styling/FxmlStyling.css");

            primaryStage.setTitle(TITLE);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.toString());
            error.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            error.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
