package com.riley.s2a;

import java.util.ArrayList;
import java.util.List;

public class AnkiFlashcardBuilder {
    private List<AnkiFlashcard> cards;

    public AnkiFlashcardBuilder(List<TokenInfo> tokens) {
        this.cards = new ArrayList<>();
        build(tokens);
    }

    private void build(List<TokenInfo> tokens) {
        for (TokenInfo token : tokens) {
            DictionaryEntry dictionaryEntry = token.getDictionaryEntry();
            
            String word = token.getWord();
            String reading = dictionaryEntry.getReading();
            String definition = dictionaryEntry.getDefinition();
            String sentence = token.getText();
            String timestampStart = token.getTimestampStart();
            String timestampEnd = token.getTimestampEnd();
            String audioName = token.getAudioName() + token.getCount() + ".mp3";
            String imageName = token.getImageName() + token.getCount() + ".jpg";

            cards.add(new AnkiFlashcard(word, reading, definition, sentence, timestampStart, timestampEnd, audioName, imageName));
        }
    }

    public List<AnkiFlashcard> getCards() {
        return cards;
    }
}