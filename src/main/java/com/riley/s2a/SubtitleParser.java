package com.riley.s2a;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SubtitleParser {
    private Path path;
    private List<SubtitleBlock> blocks;

    public SubtitleParser(Path path) {
        this.path = path;
        this.blocks = new ArrayList<SubtitleBlock>();
    }

    public String getPath() {
        return path.toString();
    }

    public void parse() throws IOException {
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
            String time = lines.get(i).trim();
            i++;

            List<String> text = new ArrayList<>();
            while (i < lines.size() && !lines.get(i).trim().isEmpty()) {
                text.add(lines.get(i));
                i++;
            }

            blockList.add(new SubtitleBlock(index, time, text));
        }
        this.blocks = blockList;
    }

    public List<SubtitleBlock> getBlocks() {
        return this.blocks;
    }

    public int countBlocks() {
        return blocks.size();
    }
}
