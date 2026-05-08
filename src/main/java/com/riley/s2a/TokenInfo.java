package com.riley.s2a;

public class TokenInfo {
    private String word;
    private int subIndex;
    private String timestampEnd;
    private String timestampStart;
    private String text;

    public TokenInfo(String word, int subIndex, String timestampEnd, String timestampStart, String text) {
        this.word = word;
        this.subIndex = subIndex;
        this.timestampEnd = timestampEnd;
        this.timestampStart = timestampStart;
        this.text = text;
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

    public String getAllInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append("\n");
        sb.append(subIndex).append("\n");
        sb.append(timestampEnd).append(" --> ").append(timestampStart).append("\n");
        sb.append(String.join("\n", text)).append("\n");
        return sb.toString();
    }
}