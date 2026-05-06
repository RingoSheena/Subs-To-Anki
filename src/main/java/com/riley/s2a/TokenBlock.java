package com.riley.s2a;

public class TokenBlock {
    private String surfaceForm;
    private String baseForm;
    private String partOfSpeech;


    public TokenBlock(String surfaceForm, String baseForm, String partOfSpeech) {
        this.surfaceForm = surfaceForm;
        this.baseForm = baseForm;
        this.partOfSpeech = partOfSpeech;
    }

    public String getSurfaceForm() {
        return surfaceForm;
    }
    public String getBaseForm() {
        return baseForm;
    }
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getAllInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(surfaceForm).append("\n");
        sb.append(baseForm).append("\n");
        sb.append(partOfSpeech).append("\n");
        return sb.toString();
    }
}
