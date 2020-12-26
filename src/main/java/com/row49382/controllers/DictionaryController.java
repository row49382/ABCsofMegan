package com.row49382.controllers;

import com.row49382.components.AlphabetSlider;
import com.row49382.models.DictionaryEntry;
import com.row49382.models.ReadonlyDictionary;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Controller manage interaction between the DictionaryView and the
 * dictionary model
 */
public class DictionaryController {
    private char currentLetter;
    private ReadonlyDictionary dictionary;

    @FXML
    private TextField dictionaryWord;

    @FXML
    private TextField dictionaryPronunciation;

    @FXML
    private TextArea dictionaryDescription;

    @FXML
    private Slider alphabetSlider;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @FXML
    private TextField currentLetterDisplay;

    public void setDictionary(ReadonlyDictionary dictionary) {
        this.dictionary = dictionary;
        this.loadDictionaryValue();
    }

    @FXML
    public void initialize() {
        this.currentLetter = 'A';

        // TODO: Make the slider increment/decrement
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
                    this.alphabetSlider.adjustValue(this.currentLetter);
                    this.loadDictionaryValue();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Value provided wasn't alphabetical. Please enter a valid letter");
                    alert.show();

                    didErrorOccur = true;
                }
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
                alert.show();

                didErrorOccur = true;
            }

            if (didErrorOccur) {
                this.currentLetterDisplay.setText(oldValue);
            }
        });

        this.alphabetSlider.setBlockIncrement(1);
        this.alphabetSlider.showTickMarksProperty();
        this.alphabetSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.loadDictionaryValue();
            this.currentLetterDisplay.setText(((AlphabetSlider) this.alphabetSlider).getAlphabetLetterFromValue());

            this.setButtonDisable(this.nextButton, 'Z');
            this.setButtonDisable(this.previousButton, 'A');
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
            this.setButtonDisable(this.previousButton, 'A');
            this.setButtonDisable(this.nextButton, 'Z');

            this.alphabetSlider.decrement();
        });

        this.dictionaryDescription.setWrapText(true);
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
     * @return if the button needed to be disabled or not
     */
    private void setButtonDisable(Button button, char disableLetter) {
        if (this.currentLetter == disableLetter) {
            button.setDisable(true);
        } else {
            button.setDisable(false);
        }
    }
}
