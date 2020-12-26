package com.row49382.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Readonly dictionary to hold all words regarding Megan
 */
public class MeganReadonlyDictionary implements ReadonlyDictionary<String, DictionaryEntry> {
    private Map<String, DictionaryEntry> dictionary;

    public MeganReadonlyDictionary(ObjectMapper objectMapper, String jsonFileLocation) throws IOException {
        String jsonDictionary = Files.readString(Path.of(jsonFileLocation));
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        MapType mapType = typeFactory.constructMapType(Map.class, String.class, DictionaryEntry.class);

        this.dictionary = objectMapper.readValue(jsonDictionary, mapType);
    }

    @Override
    public DictionaryEntry get(String key) {
        return this.dictionary.get(key);
    }
}
