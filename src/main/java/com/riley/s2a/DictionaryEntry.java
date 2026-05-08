package com.riley.s2a;

public class DictionaryEntry {
    private String term;
    private String reading;
    private String definition;
    private String reference;
    private String sequenceNum;

    public DictionaryEntry(String term, String reading, String definition, String reference, String sequenceNum) {
        this.term = term;
        this.reading = reading;
        this.definition = definition;
        this.reference = reference;
        this.sequenceNum = sequenceNum;
    }
    public DictionaryEntry() {
        
    }

    public String getTerm() {
        return term;
    }
    public String getReading() {
        return reading;
    }
    public String getDefinition() {
        return definition;
    }
    public String getReference() {
        return reference;
    }
    public String getSequenceNum() {
        return sequenceNum;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public void setReading(String reading) {
        this.reading = reading;
    }
    public void setDefinition(String definition) {
        this.definition = definition;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public void setSequenceNum(String sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    public String getAllInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Term: ").append(term).append("\n");
        sb.append("Reading: ").append(reading).append("\n");
        sb.append("Definition: ").append(definition).append("\n");
        sb.append("Reference: ").append(reference).append("\n");
        sb.append("Sequence Number: ").append(sequenceNum);
        return sb.toString();
    }
}