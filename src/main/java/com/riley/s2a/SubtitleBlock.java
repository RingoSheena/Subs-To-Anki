package com.riley.s2a;

import java.util.List;

public class SubtitleBlock {
    private int index;
    private String timestampStart;
    private String timestampEnd;
    private List<String> text;

    public SubtitleBlock(int index, String timestampStart, String timestampEnd, List<String> text) {
        this.index = index;
        this.timestampStart = timestampStart;
        this.timestampEnd = timestampEnd;
        this.text = text;
    }

    public String getFullText() {
        return String.join(" ", text);
    }
    public int getIndex() {
        return this.index;
    }
    public String getTimestampStart() {
        return this.timestampStart;
    }
    public String getTimestampEnd() {
        return this.timestampEnd;
    }
    public List<String> getText() {
        return this.text;
    }

    public String getAllInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(index).append("\n");
        sb.append(timestampEnd).append(" --> ");
        sb.append(timestampStart).append("\n");
        sb.append(String.join("\n", text)).append("\n");
        return sb.toString();
    }
}