package com.riley.s2a;

public class AnkiFlashcard {
    private String word;
    private String reading;
    private String definition;
    private String sentence;
    private String timestampStart;
    private String timestampEnd;
    private String audioPath;
    private String imagePath;

    public AnkiFlashcard(String word, String reading, String definition, String sentence,
                     String timestampStart, String timestampEnd,
                     String audioName, String imageName) {
        this.word = word;
        this.reading = reading;
        this.definition = definition;
        this.sentence = sentence;
        this.timestampStart = timestampStart;
        this.timestampEnd = timestampEnd;
        this.audioPath = audioName;
        this.imagePath = imageName;
    }

    public String getWord() {
        return word;
    }

    public String getReading() {
        return reading;
    }

    public String getDefinition() {
        return definition;
    }

    public String getSentence() {
        return sentence;
    }

    public String getTimestampStart() {
        return timestampStart;
    }

    public String getTimestampEnd() {
        return timestampEnd;
    }

    public String getAudioName() {
        return audioPath;
    }

    public String getImageName() {
        return imagePath;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setTimestampStart(String timestampStart) {
        this.timestampStart = timestampStart;
    }

    public void setTimestampEnd(String timestampEnd) {
        this.timestampEnd = timestampEnd;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTimestamp() {
        return timestampStart + " --> " + timestampEnd;
    }

    public String getAllInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Anki Flashcard ===").append("\n");
        sb.append("Word: ").append(word).append("\n");
        sb.append("Reading: ").append(reading).append("\n");
        sb.append("Definition: ").append(definition).append("\n");
        sb.append("Sentence: ").append(sentence).append("\n");
        sb.append("Timestamp Start: ").append(timestampStart).append("\n");
        sb.append("Timestamp End: ").append(timestampEnd).append("\n");
        sb.append("Timestamp: ").append(getTimestamp()).append("\n");
        sb.append("Audio File: ").append(audioPath).append("\n");
        sb.append("Image File: ").append(imagePath).append("\n");

        sb.append("\n");
        sb.append("Anki Audio Field: ").append(getAnkiAudioField()).append("\n");
        sb.append("Anki Image Field: ").append(getAnkiImageField()).append("\n");

        return sb.toString();
    }

    public String getAnkiAudioField() {
    if (audioPath == null || audioPath.isEmpty()) {
        return "";
    }

    return "[sound:" + audioPath + "]";
    }

    public String getAnkiImageField() {
        if (imagePath == null || imagePath.isEmpty()) {
            return "";
        }

        return "<img src=\"" + imagePath + "\">";
    }
}