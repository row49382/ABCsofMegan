package com.row49382.components;

import javafx.scene.control.TextField;

/**
 * Text field that ensures only one character is provided
 */
public class SingleCharacterTextField extends TextField {

    public SingleCharacterTextField() {
        super("");
    }

    public SingleCharacterTextField(String text) {
        super(text);
    }

    @Override
    public void replaceText(int start, int end, String text) {
        super.replaceText(start, end, text);
        verifySingleCharacter();
    }

    @Override
    public void replaceSelection(String text) {
        super.replaceSelection(text);
        verifySingleCharacter();
    }

    private void verifySingleCharacter() {
        if (this.getText().length() > 1) {
            this.setText(getText().substring(0, 1));
        }
    }
}
