package com.riley.s2a;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class S2AGui extends JFrame {
    private JTextField videoFileField;
    private JTextField subtitleFileField;
    private JTextField ankiMediaFolderField;
    private JTextField mediaNameField;
    private JTextField tsvOutputField;
    private JTextField dictionaryFileField;

    public S2AGui() {
        setTitle("Subs to Anki");
        setSize(800, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        videoFileField = new JTextField();
        subtitleFileField = new JTextField();
        ankiMediaFolderField = new JTextField();
        mediaNameField = new JTextField();
        tsvOutputField = new JTextField();
        dictionaryFileField = new JTextField();

        addFileRow(mainPanel, gbc, 0, "Source video file:", videoFileField, false);
        addFileRow(mainPanel, gbc, 1, "Source subtitle file:", subtitleFileField, false);
        addFileRow(mainPanel, gbc, 2, "Anki collection.media folder:", ankiMediaFolderField, true);
        addTextRow(mainPanel, gbc, 3, "Media name:", mediaNameField);
        addSaveFileRow(mainPanel, gbc, 4, "Output TSV file:", tsvOutputField);
        addFileRow(mainPanel, gbc, 5, "Dictionary TSV file:", dictionaryFileField, false);

        JButton runButton = new JButton("Generate Anki Cards");
        runButton.addActionListener(e -> runProgram());

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        mainPanel.add(runButton, gbc);

        add(mainPanel);
    }

    private void addTextRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField textField) {
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(textField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(new JLabel(""), gbc);
    }

    private void addFileRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField textField, boolean foldersOnly) {
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(textField, gbc);

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(e -> choosePath(textField, foldersOnly, false));

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(browseButton, gbc);
    }

    private void addSaveFileRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField textField) {
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(textField, gbc);

        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(e -> choosePath(textField, false, true));

        gbc.gridx = 2;
        gbc.weightx = 0;
        panel.add(browseButton, gbc);
    }

    private void choosePath(JTextField textField, boolean foldersOnly, boolean saveFile) {
        JFileChooser chooser = new JFileChooser();

        if (foldersOnly) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }

        int result;

        if (saveFile) {
            result = chooser.showSaveDialog(this);
        } else {
            result = chooser.showOpenDialog(this);
        }

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            textField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void runProgram() {
        String videoPath = videoFileField.getText().trim();
        String subtitlePath = subtitleFileField.getText().trim();
        String ankiMediaFolder = ankiMediaFolderField.getText().trim();
        String mediaName = mediaNameField.getText().trim();
        mediaName = mediaName.replace(".mp3", "");
        mediaName = mediaName.replace(".jpg", "");
        mediaName = mediaName.replace(".jpeg", "");
        mediaName = mediaName.replace(".png", "");
        String tsvOutputPath = tsvOutputField.getText().trim();
        String dictionaryPath = dictionaryFileField.getText().trim();

        if (videoPath.isEmpty() || subtitlePath.isEmpty() || ankiMediaFolder.isEmpty()
                || mediaName.isEmpty() || tsvOutputPath.isEmpty() || dictionaryPath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in every field.");
            return;
        }

        if (!tsvOutputPath.endsWith(".tsv")) {
            tsvOutputPath = tsvOutputPath + ".tsv";
        }

        try {
            System.out.println("====================================");
            System.out.println("Starting Subs to Anki generation...");
            System.out.println("Video: " + videoPath);
            System.out.println("Subtitle: " + subtitlePath);
            System.out.println("Anki media folder: " + ankiMediaFolder);
            System.out.println("Media name: " + mediaName);
            System.out.println("TSV output: " + tsvOutputPath);
            System.out.println("Dictionary: " + dictionaryPath);
            System.out.println("====================================");

            System.out.println("[1/5] Parsing subtitles...");
            SubtitleParser subtitleParser = new SubtitleParser(subtitlePath);
            subtitleParser.parse();
            System.out.println("Subtitle blocks loaded: " + subtitleParser.countBlocks());

            System.out.println("[2/5] Setting up ffmpeg...");
            FfmpegInterface ffmpeg = new FfmpegInterface(
                    videoPath,
                    ankiMediaFolder,
                    ankiMediaFolder
            );
            System.out.println("ffmpeg ready.");

            System.out.println("[3/5] Building tokens and extracting media...");
            TokenBuilder tokenBuilder = new TokenBuilder();

            tokenBuilder.build(
                    subtitleParser.getBlocks(),
                    dictionaryPath,
                    ffmpeg,
                    mediaName
            );

            List<TokenInfo> tokens = tokenBuilder.getTokens();

            System.out.println("Tokens built: " + tokens.size());
            System.out.println("No dictionary entry count: " + tokenBuilder.getNullcount());

            System.out.println("[4/5] Building Anki flashcards...");
            AnkiFlashcardBuilder flashcardBuilder = new AnkiFlashcardBuilder(tokens);
            List<AnkiFlashcard> cards = flashcardBuilder.getCards();

            System.out.println("Flashcards built: " + cards.size());

            System.out.println("[5/5] Exporting TSV...");
            AnkiExporter exporter = new AnkiExporter(
                    ankiMediaFolder,
                    tsvOutputPath
            );

            exporter.export(cards);

            System.out.println("TSV exported to: " + tsvOutputPath);
            System.out.println("Media files saved to: " + ankiMediaFolder);
            System.out.println("====================================");
            System.out.println("Finished successfully.");
            System.out.println("====================================");

            JOptionPane.showMessageDialog(this, "Done! Exported " + cards.size() + " cards.");

        } catch (Exception ex) {
            System.out.println("====================================");
            System.out.println("ERROR WHILE GENERATING CARDS");
            System.out.println("Message: " + ex.getMessage());
            System.out.println("====================================");

            ex.printStackTrace();

            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            S2AGui gui = new S2AGui();
            gui.setVisible(true);
        });
    }
}