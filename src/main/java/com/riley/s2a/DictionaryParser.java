package com.riley.s2a;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class DictionaryParser {
    private List<DictionaryEntry> entryList;
    private String dictionaryPathString;

    public DictionaryParser(String dictionaryPathString) {
        this.entryList = new ArrayList<DictionaryEntry>();
        this.dictionaryPathString = dictionaryPathString;
    }

    public List<DictionaryEntry> getEntryList() {
        return entryList;
    }

    public void parse() throws XMLStreamException, FileNotFoundException {
        System.setProperty("jdk.xml.entityExpansionLimit", "0");

        boolean gotKeb = false;
        boolean gotReb = false;
        boolean gotRef = false;
        boolean gotGloss = false;

        DictionaryEntry currentEntry = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(dictionaryPathString));

        List<DictionaryEntry> entryList = new ArrayList<>();

        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) { 
                    case "entry":
                        currentEntry = new DictionaryEntry();
                        gotKeb = false;
                        gotReb = false;
                        gotRef = false;
                        gotGloss = false;
                        break;
                    case "ent_seq":
                        if (currentEntry != null) {
                            nextEvent = reader.nextEvent();
                            currentEntry.setSequenceNum(nextEvent.asCharacters().getData());
                        }
                        break;
                    case "keb":
                        if (!gotKeb && currentEntry != null) {
                            nextEvent = reader.nextEvent();
                            currentEntry.setTerm(nextEvent.asCharacters().getData());
                            gotKeb = true;
                        }
                        break;
                    case "reb":
                        if (!gotReb && currentEntry != null) {
                            nextEvent = reader.nextEvent();
                            currentEntry.setReading(nextEvent.asCharacters().getData());
                        }
                        if (currentEntry.getTerm() == null) {
                            currentEntry.setTerm(nextEvent.asCharacters().getData());
                        }
                        gotReb = true;
                        break;
                    case "xref":
                        if (!gotRef && currentEntry != null) {
                            nextEvent = reader.nextEvent();
                            currentEntry.setReference(nextEvent.asCharacters().getData());
                            gotRef = true;
                        }
                        break;
                    case "gloss":
                        if (!gotGloss && currentEntry != null) {
                            nextEvent = reader.nextEvent();
                            currentEntry.setDefinition(nextEvent.asCharacters().getData());
                            gotGloss = true;
                        }
                        break;
                }
            }

            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (endElement.getName().getLocalPart().equals("entry")) {
                    if (currentEntry != null) {
                        entryList.add(currentEntry);
                    }
                    currentEntry = null;
                }
            }
        }
        this.entryList = entryList;
    }

    public void exportSimpleDictionary(String outputPath) throws Exception {
        PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Path.of(outputPath), StandardCharsets.UTF_8));

        for (DictionaryEntry entry : entryList) {
            writer.println(
                clean(entry.getTerm()) + "\t" +
                clean(entry.getReading()) + "\t" +
                clean(entry.getDefinition()) + "\t" +
                clean(entry.getReference()) + "\t" +
                entry.getSequenceNum()
            );
        }

        writer.close();
    }

    private String clean(String text) {
        if (text == null) {
            return "";
        }
        return text
            .replace("\t", " ")
            .replace("\n", " ")
            .replace("\r", " ");
    }
}
