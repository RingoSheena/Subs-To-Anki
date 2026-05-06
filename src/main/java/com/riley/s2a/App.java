package com.riley.s2a;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class App 
{
    public static void main( String[] args ) throws IOException { 

        SubtitleParser parser = new SubtitleParser(Path.of("C:\\Users\\swift\\OneDrive\\Documents\\Code\\S2A\\s2a\\src\\main\\java\\com\\riley\\Text\\ep01.srt"));
        parser.parse();
        List<SubtitleBlock> blox = parser.getBlocks();

        TokenBuilder builder = new TokenBuilder();
        builder.build(blox);
        List<TokenInfo> tokenBlox = builder.getTokens();
        int i = 0;
        for (TokenInfo TB : tokenBlox) {
            System.out.println(++i);
            System.out.println(TB.getAllInfo());
        }
        System.out.println(tokenBlox.size());
    }
}
 