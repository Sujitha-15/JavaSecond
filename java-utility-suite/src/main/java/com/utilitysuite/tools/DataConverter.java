package com.utilitysuite.tools;

import com.utilitysuite.UtilityTool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Minimal CSV <-> JSON converter. Assumes first CSV line contains headers.
 */
public class DataConverter implements UtilityTool {
    private final Scanner sc = new Scanner(System.in);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void start() {
        System.out.println("\n--- Data Converter ---");
        System.out.println("1. CSV -> JSON");
        System.out.println("2. JSON -> CSV (array of objects)");
        System.out.print("Choice: ");
        String choice = sc.nextLine().trim();
        try {
            if (choice.equals("1")) {
                System.out.print("CSV path: ");
                Path csv = Path.of(sc.nextLine().trim());
                List<String> lines = Files.readAllLines(csv);
                if (lines.isEmpty()) throw new IOException("Empty CSV");
                String[] headers = lines.get(0).split(",");
                List<Map<String, String>> rows = lines.stream().skip(1)
                        .map(l -> l.split(",", -1))
                        .map(values -> {
                            Map<String, String> m = new LinkedHashMap<>();
                            for (int i = 0; i < headers.length; i++) m.put(headers[i].trim(), i < values.length ? values[i].trim() : "");
                            return m;
                        }).collect(Collectors.toList());
                String json = gson.toJson(rows);
                System.out.print("Output JSON path: ");
                Path out = Path.of(sc.nextLine().trim());
                Files.writeString(out, json);
                System.out.println("Wrote JSON to " + out);
            } else if (choice.equals("2")) {
                System.out.print("JSON path: ");
                Path jsonPath = Path.of(sc.nextLine().trim());
                String json = Files.readString(jsonPath);
                List<Map<String, Object>> list = gson.fromJson(json, List.class);
                if (list == null || list.isEmpty()) throw new IOException("Empty JSON array or invalid format");
                // collect headers
                LinkedHashSet<String> headers = new LinkedHashSet<>();
                list.forEach(m -> headers.addAll(m.keySet()));
                List<String> csvLines = new ArrayList<>();
                csvLines.add(String.join(",", headers));
                for (Map<String, Object> row : list) {
                    String line = headers.stream().map(h -> Optional.ofNullable(row.get(h)).map(Object::toString).orElse("")).collect(Collectors.joining(","));
                    csvLines.add(line);
                }
                System.out.print("Output CSV path: ");
                Path out = Path.of(sc.nextLine().trim());
                Files.write(out, csvLines);
                System.out.println("Wrote CSV to " + out);
            } else {
                System.out.println("Unknown choice");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
