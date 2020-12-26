package com.row49382.components;

import javafx.scene.control.Slider;

/**
 * Slider Concrete Class to wrap the number values as
 * letters by converting to the char value
 */
public class AlphabetSlider extends Slider {
    public AlphabetSlider() {
        super(65, 90, 65);
    }

    /**
     * Returns the value as the character's ascii letter
     * @return The value as the character's ascii letter
     */
    public String getAlphabetLetterFromValue() {
        return Character.toString((char)this.getValue());
    }

    /**
     * Adjust the slider until it matches the provided letter internal
     * @param letter The letter to adjust the slider to
     */
    public void adjustUntil(char letter) {
        var letterValue = (char) this.getValue();
        if (letterValue < letter) {
            this.incrementUntil(letter);
        }
        else if (letterValue > letter) {
            this.decrementUntil(letter);
        }
    }

    /**
     * Increments the slider until the letter provided matches the number
     * in the alphabet on the slider
     * @param letter The letter to increment to
     */
    private void incrementUntil(char letter) {
        while ((char)this.getValue() < letter) {
            increment();
        }
    }

    /**
     * Decrements until the letter provided matches the number
     * in the alphabet on the slider
     * @param letter the letter to decrement to
     */
    private void decrementUntil(char letter) {
        while ((char)this.getValue() > letter) {
            decrement();
        }
    }
}
