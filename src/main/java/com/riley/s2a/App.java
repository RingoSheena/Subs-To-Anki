package com.riley.s2a;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class App 
{
    public static Path subtitlePath = Path.of("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\ep01.srt");
    public static Path dictionaryPath = Path.of("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\JMdict_e.xml");
    public static String dictionaryPathString = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\minidict.xml";
    public static void main( String[] args ) throws Exception { 

        // SubtitleParser parser = new SubtitleParser(subtitlePath);
        // parser.parse();
        // List<SubtitleBlock> blox = parser.getBlocks();
        // TokenBuilder builder = new TokenBuilder();
        // builder.build(blox);
        // List<TokenInfo> tokenBlox = builder.getTokens();
        // int i = 0;
        // for (TokenInfo TB : tokenBlox) {
        //     System.out.println(++i);
        //     System.out.println(TB.getAllInfo());
        // }
        // System.out.println(tokenBlox.size());

        DictionaryParser dictParser = new DictionaryParser("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\JMdict_e.xml");
        dictParser.parse();
        List<DictionaryEntry> entries = dictParser.getEntryList();
        System.out.println("Loaded " + entries.size() + " entries");
        dictParser.exportSimpleDictionary("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\simpledict.tsv");


    }
}