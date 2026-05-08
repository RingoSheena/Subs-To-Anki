package com.riley.s2a;

import java.nio.file.Path;
import java.util.List;



public class App 
{
    public static String videoPath = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\1.mkv";
    public static String audioFolder = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\output\\audio";
    public static String imageFolder = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\output\\images";
    public static String tsvOutput = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\output\\output.tsv";

    public static String ankiCollectionPath = "C:\\Users\\swift\\AppData\\Roaming\\Anki2\\mafs\\collection.media";

    public static String subtitlePath = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\ep01.srt";
    public static Path dictionaryPath = Path.of("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\JMdict_e.xml");
    public static String tsvPathString = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\simpledict.tsv";
    public static String fileName = "luckytest";
    public static void main(String[] args) throws Exception { 

        FfmpegInterface mediaInterface = new FfmpegInterface(videoPath, ankiCollectionPath, ankiCollectionPath);
        SubtitleParser parser = new SubtitleParser(subtitlePath);
        parser.parse();
        List<SubtitleBlock> blox = parser.getBlocks();
        TokenBuilder builder = new TokenBuilder();
        builder.build(blox, fileName, fileName);
        List<TokenInfo> tokenBlox = builder.getTokens();
        AnkiFlashcardBuilder flashCardBuilder = new AnkiFlashcardBuilder(tokenBlox);
        List<AnkiFlashcard> flashcards = flashCardBuilder.getCards();
        for (AnkiFlashcard cards : flashcards) {
            System.out.println(cards.getAllInfo());
            mediaInterface.extractAudio(cards.getTimestampStart(), cards.getTimestampEnd(), fileName);
            mediaInterface.extractImage(cards.getTimestampStart(), fileName);
        }
        AnkiExporter exporter = new AnkiExporter(ankiCollectionPath, tsvOutput);
        exporter.export(flashcards);

        // DictionaryParser dictParser = new DictionaryParser("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\JMdict_e.xml");
        // dictParser.parse();
        // List<DictionaryEntry> entries = dictParser.getEntryList();
        // System.out.println("Loaded " + entries.size() + " entries");
        // dictParser.exportSimpleDictionary("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\simpledict.tsv");

        // DictionaryLookup dictionaryLookup = new DictionaryLookup(tsvPathString);
        // DictionaryEntry word = dictionaryLookup.lookup("会う");
        // System.out.println(word.getAllInfo());

    }
}