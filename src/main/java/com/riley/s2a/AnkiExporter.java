package com.riley.s2a;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AnkiExporter {
    private String colmediaPath;
    private String tsvOutputPath;

    public AnkiExporter(String colmediaPath, String tsvOutputPath) {
        this.colmediaPath = colmediaPath;
        this.tsvOutputPath = tsvOutputPath;
    }

    public void export(List<AnkiFlashcard> cards) throws IOException {
        Path outputFile = Path.of(tsvOutputPath);

        if (outputFile.getParent() != null) {
            Files.createDirectories(outputFile.getParent());
        }

        PrintWriter writer = new PrintWriter(Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8));
        for (AnkiFlashcard card : cards) {
            String front = makeFront(card);
            String back = makeBack(card);

            writer.println(clean(front) + "\t" + clean(back));
        }
        writer.close();
    }

    private String makeFront(AnkiFlashcard card) {
        return card.getWord();
    }

    private String makeBack(AnkiFlashcard card) {
        return card.getReading()
                + "<br><br>"
                + card.getSentence()
                + "<br><br>"
                + card.getDefinition()
                + "<br><br>"
                + makeAudioField(card.getAudioName())
                + "<br><br>"
                + makeImageField(card.getImageName());
    }

    private String makeAudioField(String audioName) {
        if (audioName == null || audioName.isEmpty()) {
            return "";
        }

        return "[sound:" + audioName + "]";
    }

    private String makeImageField(String imageName) {
        if (imageName == null || imageName.isEmpty()) {
            return "";
        }

        return "<img src=\"" + imageName + "\" width=\"444\">";
    }

    public String getColmediaPath() {
        return colmediaPath;
    }

    private static String clean(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace("\t", " ")
                .replace("\n", "<br>")
                .replace("\r", "");
    }
}