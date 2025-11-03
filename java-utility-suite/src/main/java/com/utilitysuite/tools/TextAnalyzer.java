package com.utilitysuite.tools;

import com.utilitysuite.UtilityTool;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class TextAnalyzer implements UtilityTool {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void start() {
        System.out.println("\n--- Text Analyzer ---");
        System.out.print("Enter text file path: ");
        Path p = Path.of(sc.nextLine().trim());
        try {
            String content = Files.readString(p);
            long lines = content.lines().count();
            String[] words = content.split("\\W+");
            long wordCount = Arrays.stream(words).filter(s -> !s.isBlank()).count();
            Map<String, Long> freq = Arrays.stream(words)
                    .filter(s -> !s.isBlank())
                    .map(String::toLowerCase)
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

            System.out.println("Lines: " + lines);
            System.out.println("Words: " + wordCount);
            System.out.println("Top 10 words:");
            freq.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(10)
                    .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}

