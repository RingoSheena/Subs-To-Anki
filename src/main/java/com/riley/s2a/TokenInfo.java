package com.riley.s2a;

public class TokenInfo {
    private String word;
    private int subIndex;
    private String timestamp;
    private String text;

    public TokenInfo(String word, int subIndex, String timestamp, String text) {
        this.word = word;
        this.subIndex = subIndex;
        this.timestamp = timestamp;
        this.text = text;
    }

    public String getWord() {
        return word;
    }

    public int getSubIndex() {
        return subIndex;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public String getAllInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append("\n");
        sb.append(subIndex).append("\n");
        sb.append(timestamp).append("\n");
        sb.append(String.join("\n", text)).append("\n");
        return sb.toString();
    }
}