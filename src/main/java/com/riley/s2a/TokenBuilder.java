package com.riley.s2a;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.ja.tokenattributes.BaseFormAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class TokenBuilder {
    private List<TokenInfo> tokens;
    private int nullcount;
    private int count;

    public TokenBuilder() {
        this.tokens = new ArrayList<TokenInfo>();
        this.nullcount = 0;
        this.count = 0;
    }

    public void build(List<SubtitleBlock> subtitleBlocks, String audioName, String imageName) throws IOException {
        String tsvPath = "C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\data\\simpledict.tsv";
        DictionaryLookup dictionaryLookup = new DictionaryLookup(tsvPath);
        JapaneseTokenizer tokenizer = new JapaneseTokenizer(null, true, JapaneseTokenizer.Mode.SEARCH);
        
        CharTermAttribute surfaceForm = tokenizer.addAttribute(CharTermAttribute.class);
        BaseFormAttribute baseForm = tokenizer.addAttribute(BaseFormAttribute.class);

        List<TokenInfo> tokenList = new ArrayList<>();
        Set<String> set = new HashSet<>();

        for (SubtitleBlock sb : subtitleBlocks) {
            try {
                tokenizer.setReader(new StringReader(sb.getFullText()));
                tokenizer.reset();
    
                while (tokenizer.incrementToken()) {
                    String surface = surfaceForm.toString();
                    String base = baseForm.getBaseForm(); 

                    if (base == null) base = surface;
                    if (surface.length() == 1 && (isHiragana(surface.charAt(0)) || isKatakana(surface.charAt(0)))) { continue; }
                    if (!isJapanese(base) || endsWithSmallTsu(base)) { continue; }
                    if (set.contains(surface)) { continue; }

                    DictionaryEntry entry = dictionaryLookup.lookup(base);
                    if (entry == null) {
                        entry = dictionaryLookup.lookup(surface);
                    }    
                    if (entry == null) {
                        nullcount++;
                        continue;
                    }

                    if (set.add(base)) {
                        tokenList.add(new TokenInfo(base, sb.getIndex(), sb.getTimestampEnd(), sb.getTimestampStart(), sb.getFullText(), entry, audioName, imageName, count));
                        count++;
                    }
                }
    
                tokenizer.end();
            } finally {
                tokenizer.close();
            }
        }
        this.tokens = tokenList;
    }

    public List<TokenInfo> getTokens() {
        return this.tokens;
    }

    public static boolean isJapanese(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (isHiragana(c) || isKatakana(c) || isKanji(c)) {
                return true;
            }
        }
        
        return false;
    }

    public int getNullcount() {
        return nullcount;
    }
    public int getCount() {
        return count;
    }

    public static boolean isHiragana(char c) {
        return c >= '\u3040' && c <= '\u309F';
    }
    public static boolean isKatakana(char c) {
        return c >= '\u30A0' && c <= '\u30FF';
    }
    public static boolean isKanji(char c) {
        return c >= '\u4E00' && c <= '\u9FAF';
    }
    public static boolean endsWithSmallTsu(String text) {
        if (text == null || text.length() == 0) {
            return false;
        }

        char lastChar = text.charAt(text.length() - 1);

        return lastChar == 'っ' || lastChar == 'ッ';
    }

}

