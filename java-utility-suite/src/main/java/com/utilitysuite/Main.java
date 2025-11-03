package com.utilitysuite;

import java.util.Scanner;

import com.utilitysuite.tools.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Java Utility Suite ===");
            System.out.println("1. File Manager");
            System.out.println("2. Text Analyzer");
            System.out.println("3. Data Converter (CSV <-> JSON)");
            System.out.println("4. Encryption Utility (AES)");
            System.out.println("5. System Info Viewer");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = -1;
            try { choice = Integer.parseInt(sc.nextLine().trim()); } catch (Exception ignored) {}

            UtilityTool tool = switch (choice) {
                case 1 -> new FileManager();
                case 2 -> new TextAnalyzer();
                case 3 -> new DataConverter();
                case 4 -> new EncryptionTool();
                case 5 -> new SystemInfoViewer();
                case 6 -> null;
                default -> {
                    System.out.println("Invalid choice! Try again.");
                    yield null;
                }
            };

            if (choice == 6) break;
            if (tool != null) tool.start();
        }
        System.out.println("Goodbye!");
        sc.close();
    }
}
