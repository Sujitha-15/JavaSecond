package com.utilitysuite.tools;

import com.utilitysuite.UtilityTool;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class FileManager implements UtilityTool {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void start() {
        System.out.println("\n--- File Manager ---");
        System.out.println("1. Copy file");
        System.out.println("2. Move file");
        System.out.println("3. Delete file");
        System.out.print("Choice: ");
        String choice = sc.nextLine().trim();
        System.out.print("Source path: ");
        Path src = Paths.get(sc.nextLine().trim());
        try {
            switch (choice) {
                case "1" -> {
                    System.out.print("Destination path: ");
                    Path dest = Paths.get(sc.nextLine().trim());
                    Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Copied to " + dest);
                }
                case "2" -> {
                    System.out.print("Destination path: ");
                    Path dest = Paths.get(sc.nextLine().trim());
                    Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved to " + dest);
                }
                case "3" -> {
                    Files.deleteIfExists(src);
                    System.out.println("Deleted " + src);
                }
                default -> System.out.println("Unknown operation");
            }
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}
