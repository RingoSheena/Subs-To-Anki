package com.riley.s2a;

import java.io.File;
import java.io.IOException;

public class FfmpegInterface {
    private String videoPath;
    private String audioFolder;
    private String imageFolder;
    private int countA;
    private int countI;
    private String audioName;
    private String imageName;

    public FfmpegInterface(String videoPath, String audioFolder, String imageFolder) {
        this.videoPath = videoPath;
        this.audioFolder = audioFolder;
        this.imageFolder = imageFolder;
        this.countA = 0;
        this.countI = 0;
        this.audioName = "";
        this.imageName = "";

        new File(audioFolder).mkdirs();
        new File(imageFolder).mkdirs();
    }

    public String extractAudio(String startTime, String endTime, String fileName) throws IOException, InterruptedException {
        String audioPath = audioFolder + File.separator + fileName + countA + ".mp3";
        this.audioName = fileName + countA + ".mp3";
        
        ProcessBuilder pb = new ProcessBuilder(
            "ffmpeg",
            "-y",
            "-ss", formatTime(startTime),
            "-i", videoPath,
            "-t", getDuration(startTime, endTime, .5),
            "-vn",
            audioPath
        );

        pb.inheritIO();

        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("ffmpeg failed audio extraction");
        }
        countA++;
        return audioPath;
    }
    public String extractImage(String startTime, String fileName) throws IOException, InterruptedException {
        String imagePath = imageFolder + File.separator + fileName + countI + ".jpg";
        this.imageName = fileName + countI + ".jpg";
        
        ProcessBuilder pb = new ProcessBuilder(
            "ffmpeg",
            "-y",
            "-ss", formatTime(startTime),
            "-i", videoPath,
            "-frames:v", "1",
            imagePath
        );

        pb.inheritIO();

        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("ffmpeg failed image extraction");
        }
        countI++;
        return imagePath;
    }

    public String formatTime(String time) {
        return time.replace(",", ".");
    }

    public String getAudioName() {
        return audioName;
    }
    public String getImageName() {
        return imageName;
    }

    private double toSeconds(String time) {
        time = formatTime(time);
        String[] parts = time.split(":");

        double hours = Double.parseDouble(parts[0]);
        double minutes = Double.parseDouble(parts[1]);
        double seconds = Double.parseDouble(parts[2]);

        return hours * 3600 + minutes * 60 + seconds;
    }

    private String getDuration(String startTime, String endTime, double padding) {
        return String.valueOf(toSeconds(endTime) - toSeconds(startTime) + padding);
    }
}