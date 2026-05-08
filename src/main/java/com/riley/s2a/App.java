package com.riley.s2a;

import java.nio.file.Path;
import java.util.List;



public class App 
{
    public static String subtitlePath = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\ep01.srt";
    public static Path dictionaryPath = Path.of("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\JMdict_e.xml");
    public static String tsvPathString = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\simpledict.tsv";
    public static void main( String[] args ) throws Exception { 

        SubtitleParser parser = new SubtitleParser(subtitlePath);
        parser.parse();
        List<SubtitleBlock> blox = parser.getBlocks();
        TokenBuilder builder = new TokenBuilder();
        builder.build(blox);
        List<TokenInfo> tokenBlox = builder.getTokens();
        int i = 0;
        for (TokenInfo TB : tokenBlox) {
            System.out.println(++i);
            System.out.println(TB.getAllInfo());
            System.out.println(TB.getDictionaryEntry().getAllInfo());
        }
        System.out.println(tokenBlox.size());

        // DictionaryParser dictParser = new DictionaryParser("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\JMdict_e.xml");
        // dictParser.parse();
        // List<DictionaryEntry> entries = dictParser.getEntryList();
        // System.out.println("Loaded " + entries.size() + " entries");
        // dictParser.exportSimpleDictionary("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\simpledict.tsv");

        // DictionaryLookup dictionaryLookup = new DictionaryLookup(tsvPathString);
        // DictionaryEntry word = dictionaryLookup.lookup("会う");
        // System.out.println(word.getAllInfo());
        System.out.println("Null count: " + builder.getNullcount());

    }
}