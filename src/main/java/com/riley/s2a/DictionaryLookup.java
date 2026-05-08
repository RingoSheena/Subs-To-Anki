package com.riley.s2a;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class DictionaryLookup {
    private HashMap<String, DictionaryEntry> map;

    public DictionaryLookup(String tsvPath) throws IOException {
        this.map = new HashMap<String, DictionaryEntry>();
        parse(tsvPath);
    }

    private void parse(String tsvPath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(tsvPath), StandardCharsets.UTF_8);
        for (String line : lines) {
            String[] parts = line.split("\t");

            if (parts.length < 5) continue;
            
            String term = parts[0];
            String reading = parts[1];
            String definition = parts[2];
            String reference = parts[3];
            String sequenceNum = parts[4];

            DictionaryEntry entry = new DictionaryEntry(term, reading, definition, reference, sequenceNum);

            map.put(term, entry);
        }
    }

    public DictionaryEntry lookup(String word) {
        return map.get(word);
    }
    public boolean contains(String word) {
        return map.containsKey(word);
    }
}
