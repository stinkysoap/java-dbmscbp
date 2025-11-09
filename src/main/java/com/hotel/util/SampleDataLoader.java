package com.hotel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SampleDataLoader {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    /**
     * Loads sample data from sample_data.sql file
     * @return true if successful, false otherwise
     */
    public boolean loadSampleData() {
        try (Connection conn = connectionManager.getConnection()) {
            String sql = loadResource("sample_data.sql");
            
            // Split by semicolon and execute each statement
            // Need to handle multi-line statements and comments properly
            String[] statements = sql.split(";");
            
            try (Statement stmt = conn.createStatement()) {
                int executed = 0;
                int skipped = 0;
                for (String statement : statements) {
                    // Clean up the statement
                    statement = statement.trim()
                        .replaceAll("--.*", "") // Remove inline comments
                        .replaceAll("(?m)^\\s*--.*$", "") // Remove comment lines
                        .trim();
                    
                    if (statement.isEmpty()) {
                        skipped++;
                        continue; // Skip empty statements
                    }
                    
                    try {
                        stmt.executeUpdate(statement);
                        executed++;
                    } catch (SQLException e) {
                        // Ignore errors for duplicate key constraints (if data already exists)
                        String errorMsg = e.getMessage();
                        if (errorMsg != null && 
                            (errorMsg.contains("UNIQUE constraint") || 
                             errorMsg.contains("already exists") ||
                             errorMsg.contains("duplicate"))) {
                            skipped++;
                            // Silently skip duplicates
                        } else {
                            System.err.println("Error executing statement (first 100 chars): " + 
                                statement.substring(0, Math.min(100, statement.length())));
                            System.err.println("Error: " + errorMsg);
                        }
                    }
                }
                System.out.println("Loaded sample data: " + executed + " statements executed, " + skipped + " skipped");
                return true;
            }
        } catch (SQLException | IOException e) {
            System.err.println("Failed to load sample data: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads sample data with option to clear existing data first
     * @param clearFirst if true, deletes all data from tables before loading
     * @return true if successful, false otherwise
     */
    public boolean loadSampleData(boolean clearFirst) {
        if (clearFirst) {
            if (!clearAllData()) {
                System.err.println("Failed to clear existing data");
                return false;
            }
        }
        return loadSampleData();
    }

    /**
     * Clears all data from all tables (in reverse dependency order)
     */
    public boolean clearAllData() {
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Delete in reverse dependency order to avoid foreign key violations
            stmt.executeUpdate("DELETE FROM payments");
            stmt.executeUpdate("DELETE FROM invoices");
            stmt.executeUpdate("DELETE FROM booking_services");
            stmt.executeUpdate("DELETE FROM room_tasks");
            stmt.executeUpdate("DELETE FROM bookings");
            stmt.executeUpdate("DELETE FROM services");
            stmt.executeUpdate("DELETE FROM employees");
            stmt.executeUpdate("DELETE FROM customers");
            stmt.executeUpdate("DELETE FROM guests");
            stmt.executeUpdate("DELETE FROM rooms");
            
            // Reset auto-increment counters (SQLite specific)
            stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name IN ('rooms', 'guests', 'customers', 'bookings', 'services', 'booking_services', 'invoices', 'payments', 'employees', 'room_tasks')");
            
            System.out.println("Cleared all existing data");
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to clear data: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private String loadResource(String name) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name)) {
            if (inputStream == null) {
                throw new IllegalStateException("Resource not found: " + name);
            }
            try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(reader)) {
                return br.lines().collect(java.util.stream.Collectors.joining("\n"));
            }
        }
    }

    public static void main(String[] args) {
        SampleDataLoader loader = new SampleDataLoader();
        
        if (args.length > 0 && args[0].equals("--clear")) {
            System.out.println("Clearing existing data and loading sample data...");
            loader.loadSampleData(true);
        } else {
            System.out.println("Loading sample data (existing data will remain)...");
            System.out.println("Use --clear flag to clear existing data first");
            loader.loadSampleData(false);
        }
        
        System.out.println("\nSample data loading complete!");
        System.out.println("You can now run the application to see the sample data.");
    }
}

