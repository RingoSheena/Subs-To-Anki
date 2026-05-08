package com.riley.s2a;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SubtitleParser {
    private String subtitlePathString;
    private List<SubtitleBlock> blocks;

    public SubtitleParser(String subtitlePathString) {
        this.subtitlePathString = subtitlePathString;
        this.blocks = new ArrayList<SubtitleBlock>();
    }

    public String getPath() {
        return subtitlePathString;
    }

    public void parse() throws IOException {
        Path path = Path.of(subtitlePathString);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<SubtitleBlock> blockList = new ArrayList<>();
        int i = 0;
        while (i < lines.size()) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                i++;
                continue;
            }
            int index = Integer.parseInt(line);
            i++;

            if (i >= lines.size()) break;
            String[] timestamp = lines.get(i).trim().split(" --> ");
            i++;

            List<String> text = new ArrayList<>();
            while (i < lines.size() && !lines.get(i).trim().isEmpty()) {
                text.add(lines.get(i));
                i++;
            }

            blockList.add(new SubtitleBlock(index, timestamp[0], timestamp[1], text));
        }
        this.blocks = blockList;
    }

    public List<SubtitleBlock> getBlocks() {
        return this.blocks;
    }

    public String printBlocks() {
        StringBuilder sb = new StringBuilder();
        for (SubtitleBlock block : blocks) {
            sb.append(block.getIndex()).append("\n")
            .append(block.getTimestampStart()).append(block.getTimestampEnd()).append("\n")
            .append(block.getFullText()).append("\n");
        }
        return sb.toString();
    }

    public int countBlocks() {
        return blocks.size();
    }
}
