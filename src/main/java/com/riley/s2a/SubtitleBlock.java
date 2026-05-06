package com.riley.s2a;

import java.util.List;

public class SubtitleBlock {
    private int index;
    private String timestamp;
    private List<String> text;

    public SubtitleBlock(int index, String timestamp, List<String> text) {
        this.index = index;
        this.timestamp = timestamp;
        this.text = text;
    }

    public String getFullText() {
        return String.join(" ", text);
    }

    public int getIndex() {
        return this.index;
    }
    public String getTimestamp() {
        return this.timestamp;
    }
    public List<String> getText() {
        return this.text;
    }

    public String getAllInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(index).append("\n");
        sb.append(timestamp).append("\n");
        sb.append(String.join("\n", text)).append("\n");
        return sb.toString();
    }
}