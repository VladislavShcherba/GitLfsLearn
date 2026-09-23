package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Path imagesDir = Paths.get("images");

        if (Files.notExists(imagesDir) || !Files.isDirectory(imagesDir)) {
            System.out.println("No images directory found at: " + imagesDir);
            return;
        }

        try (var files = Files.list(imagesDir)) {
            files.filter(Files::isRegularFile)
                    .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                    .forEach(path -> {
                        try {
                            long sizeInBytes = Files.size(path);
                            System.out.println(path.getFileName() + " - " + sizeInBytes + " bytes");
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to read images directory", e);
        }
    }
}
