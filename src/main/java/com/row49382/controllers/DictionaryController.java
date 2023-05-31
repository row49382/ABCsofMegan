package com.row49382.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.row49382.components.AlphabetSlider;
import com.row49382.models.DictionaryEntry;
import com.row49382.models.ReadonlyDictionary;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Controller to manage interaction between the DictionaryView and the
 * dictionary model
 */
public class DictionaryController {
    /** Keeps track of the current letter being displayed */
    private char currentLetter;

    /** The dictionary that is being displayed */
    private ReadonlyDictionary<String, DictionaryEntry> dictionary;

    @FXML
    private BorderPane mainPane;

    @FXML
    private SplitPane contentPane;

    @FXML
    private AnchorPane sliderPane;

    @FXML
    private VBox sliderPaneVBox;

    @FXML
    private AnchorPane dictionaryPane;

    @FXML
    private VBox dictionaryPaneVBox;

    @FXML
    private JFXTextField dictionaryWord;

    @FXML
    private JFXTextField dictionaryPronunciation;

    @FXML
    private JFXTextArea dictionaryDescription;

    @FXML
    private ButtonBar dictionaryButtonBar;

    @FXML
    private JFXSlider alphabetSlider;

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton previousButton;

    @FXML
    private JFXTextField currentLetterDisplay;

    public void setDictionary(ReadonlyDictionary<String, DictionaryEntry> dictionary) {
        this.dictionary = dictionary;
        this.loadDictionaryValue();
    }

    public BorderPane getMainPane() {
        return this.mainPane;
    }

    @FXML
    public void initialize() {
        this.currentLetter = 'A';

        this.currentLetterDisplay.setText(Character.toString(this.currentLetter));
        this.currentLetterDisplay.textProperty().addListener((observable, oldValue, newValue) -> {
            // if the user deletes the current letter, it shouldn't
            // trigger error until a non-alphabet character is entered
            if (newValue.equals("")) {
                return;
            }

            boolean didErrorOccur = false;

            try {
                char newValueChar = newValue.charAt(0);

                if (Character.isAlphabetic(newValueChar)) {
                    this.currentLetter = newValueChar;
                    ((AlphabetSlider) this.alphabetSlider).adjustUntil(this.currentLetter);
                    this.loadDictionaryValue();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Value provided wasn't alphabetical. Please enter a valid letter");
                    alert.show();

                    didErrorOccur = true;
                }
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();

                didErrorOccur = true;
            }

            if (didErrorOccur) {
                this.currentLetterDisplay.setText(oldValue);
            }
        });

        this.alphabetSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.loadDictionaryValue();
            this.currentLetterDisplay.setText(((AlphabetSlider) this.alphabetSlider).getAlphabetLetterFromValue());
            this.updateButtonDisableProperty();
        });

        this.nextButton.setOnAction((var event) -> {
            this.currentLetter++;
            this.alphabetSlider.increment();
        });

        // since first character is at the top of the alphabet, we can't go to
        // previous value
        this.previousButton.setDisable(true);
        this.previousButton.setOnAction((var event) -> {
            this.currentLetter--;
            this.alphabetSlider.decrement();
        });

        //this.dictionaryDescription.setWrapText(true);

        this.bindForAutoResizing();
        this.setPreferredDimensions();
    }

    /**
     * Loads the current dictionary values based on the current letter
     * into the view
     */
    private void loadDictionaryValue() {
        var dictionaryEntry = (DictionaryEntry)(this.dictionary.get(Character.toString(this.currentLetter).toLowerCase()));

        this.dictionaryWord.setText(dictionaryEntry.getWord());
        this.dictionaryPronunciation.setText(dictionaryEntry.getPronunciation());
        this.dictionaryDescription.setText(dictionaryEntry.getDescription());
    }

    /**
     * Updates the button disable property based on the current letter
     */
    private void updateButtonDisableProperty() {
        this.setButtonDisable(this.nextButton, 'Z');
        this.setButtonDisable(this.previousButton, 'A');
    }

    /**
     * Sets the button to disabled if the slider is on letter provided
     * @param button The button that is being disabled
     * @param disableLetter The letter that the slider is displaying that would cause a disable
     */
    private void setButtonDisable(Button button, char disableLetter) {
        if (this.currentLetter == disableLetter) {
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }
    }

    /**
     * Binds the child containers/nodes height and width to their parents
     * height and width, allowing for auto-resizing
     */
    private void bindForAutoResizing() {
        this.bind(this.contentPane, this.mainPane);
        this.bind(this.alphabetSlider, this.sliderPaneVBox);
        this.bind(this.dictionaryPaneVBox, this.dictionaryPane);
        this.bind(this.dictionaryDescription, this.dictionaryPaneVBox);
        this.bind(this.dictionaryButtonBar, this.dictionaryPaneVBox);
    }

    /**
     * Binds the child pref height and width property to the parent's height
     * and width
     * @param child The child being bound
     * @param parent The parent that is used as a reference for binding
     */
    private void bind(Region child, Region parent) {
        child.prefHeightProperty().bind(parent.heightProperty());
        child.prefWidthProperty().bind(parent.widthProperty());
    }

    /**
     * Sets the preferred dimensions for the Regions
     */
    private void setPreferredDimensions() {
        this.dictionaryWord.setMinHeight(50);
        this.dictionaryWord.setMaxHeight(50);

        this.dictionaryPronunciation.setMinHeight(50);
        this.dictionaryPronunciation.setMaxHeight(50);

        this.dictionaryButtonBar.setMinHeight(50);
        this.dictionaryButtonBar.setMaxHeight(50);

        this.nextButton.setMinWidth(200);
        this.previousButton.setMinWidth(200);

        this.sliderPane.setMinWidth(100);
        this.sliderPane.setMaxWidth(100);
    }
}
