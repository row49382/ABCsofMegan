package com.row49382.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent the properties needed for an entry in
 * the dictionary
 */
public class DictionaryEntry {
    private String word;
    private String pronunciation;
    private String description;

    @JsonCreator
    public DictionaryEntry(
            @JsonProperty("word") String word,
            @JsonProperty("pronunciation") String pronunciation,
            @JsonProperty("description") String description) {
        this.word = word;
        this.pronunciation = pronunciation;
        this.description = description;
    }

    public String getWord() {
        return this.word;
    }

    public String getPronunciation() {
        return this.pronunciation;
    }

    public String getDescription() {
        return this.description;
    }
}
