package com.riley.s2a;

public class TokenInfo {
    private String word;
    private int subIndex;
    private String timestampEnd;
    private String timestampStart;
    private String text;
    private DictionaryEntry dictionaryEntry;
    private String audioName;
    private String imageName;
    private int count;

    public TokenInfo(String word, int subIndex, String timestampEnd, String timestampStart, String text, DictionaryEntry dictionaryEntry, String audioName, String imageName, int count) {
        this.word = word;
        this.subIndex = subIndex;
        this.timestampEnd = timestampEnd;
        this.timestampStart = timestampStart;
        this.text = text;
        this.dictionaryEntry = dictionaryEntry;
        this.audioName = audioName;
        this.imageName = imageName;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public int getSubIndex() {
        return subIndex;
    }

    public String getTimestampEnd() {
        return timestampEnd;
    }

    public String getTimestampStart() {
        return timestampStart;
    }

    public String getText() {
        return text;
    }
    
    public DictionaryEntry getDictionaryEntry() {
        return dictionaryEntry;
    }

    public String getImageName() {
        return imageName;
    }
    public String getAudioName() {
        return audioName;
    }
    public int getCount() {
        return count;
    }

    public String getAllInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append("\n");
        sb.append(subIndex).append("\n");
        sb.append(timestampStart).append(" --> ").append(timestampEnd).append("\n");
        sb.append(String.join("\n", text)).append("\n");
        return sb.toString();
    }
}