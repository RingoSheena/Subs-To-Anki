package com.riley.s2a;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.ja.tokenattributes.BaseFormAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class App 
{
    public static void main( String[] args ) throws IOException {
        // String text = "私は日本語を勉強しています。";
        // JapaneseTokenizer tokenizer = new JapaneseTokenizer(null, true, JapaneseTokenizer.Mode.SEARCH);
        // tokenizer.setReader(new StringReader(text));
        // CharTermAttribute term = tokenizer.addAttribute(CharTermAttribute.class);
        // BaseFormAttribute baseForm = tokenizer.addAttribute(BaseFormAttribute.class);
        // PartOfSpeechAttribute partOfSpeech = tokenizer.addAttribute(PartOfSpeechAttribute.class);
        // tokenizer.reset();
        // while (tokenizer.incrementToken()) {
        //     String surface = term.toString();
        //     String base = baseForm.getBaseForm();
        //     String pos = partOfSpeech.getPartOfSpeech();
        //     System.out.println("Surface: " + surface);
        //     System.out.println("Base: " + base);
        //     System.out.println("POS: " + pos);
        //     System.out.println();
        // }
        // tokenizer.end();
        // tokenizer.close();

        int one = 1;
        String timestamp = "00:00:00,939 --> 00:00:03,316";
        List<String> text = Arrays.asList("Hi","Bye");
        SubtitleBlock newBlock = new SubtitleBlock(one, timestamp, text);

        // System.out.println(newBlock.getIndex() + "\n" + newBlock.getText() + "\n" + newBlock.getTimestamp());

        SubtitleParser parser = new SubtitleParser(Path.of("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\src\\main\\java\\com\\riley\\Text\\ep01.srt"));
        System.out.println(parser.getPath());
        for (SubtitleBlock b : parser.getBlocks()) {
            System.out.println(b.getIndex());
        }
        parser.parse();
        for (SubtitleBlock b : parser.getBlocks()) {
            System.out.println(b.getAllInfo());
        }

    }
}
 