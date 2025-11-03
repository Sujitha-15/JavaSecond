package com.utilitysuite.tools;

import com.utilitysuite.UtilityTool;

public class SystemInfoViewer implements UtilityTool {
    @Override
    public void start() {
        System.out.println("\n--- System Info Viewer ---");
        Runtime rt = Runtime.getRuntime();
        System.out.println("Available processors: " + rt.availableProcessors());
        System.out.println("Free memory (bytes): " + rt.freeMemory());
        System.out.println("Total memory (bytes): " + rt.totalMemory());
        System.out.println("Max memory (bytes): " + rt.maxMemory());
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
    }
}
