package com.hotel.util;

import java.sql.*;

public class DataExporter {
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    public void printAllTables() {
        try (Connection conn = connectionManager.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"})) {
                System.out.println("\n=== Available Tables ===");
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    System.out.println("- " + tableName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error listing tables: " + e.getMessage());
        }
    }

    public void printTableData(String tableName) {
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {
            
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            
            System.out.println("\n=== " + tableName.toUpperCase() + " ===");
            
            // Print headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(meta.getColumnName(i) + "\t");
            }
            System.out.println();
            System.out.println("-".repeat(80));
            
            // Print data
            int rowCount = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    System.out.print((value != null ? value.toString() : "NULL") + "\t");
                }
                System.out.println();
                rowCount++;
            }
            System.out.println("\nTotal rows: " + rowCount);
            
        } catch (SQLException e) {
            System.err.println("Error reading table " + tableName + ": " + e.getMessage());
        }
    }

    public void printAllData() {
        printAllTables();
        try (Connection conn = connectionManager.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"})) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    printTableData(tableName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void executeQuery(String sql) {
        try (Connection conn = connectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            
            // Print headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(meta.getColumnName(i) + "\t");
            }
            System.out.println();
            System.out.println("-".repeat(80));
            
            // Print data
            int rowCount = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    System.out.print((value != null ? value.toString() : "NULL") + "\t");
                }
                System.out.println();
                rowCount++;
            }
            System.out.println("\nTotal rows: " + rowCount);
            
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DataExporter exporter = new DataExporter();
        
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("  java DataExporter list                    - List all tables");
            System.out.println("  java DataExporter all                      - Print all table data");
            System.out.println("  java DataExporter table <table_name>      - Print specific table");
            System.out.println("  java DataExporter query \"SELECT ...\"      - Execute custom query");
            System.out.println("\nExamples:");
            System.out.println("  java DataExporter table rooms");
            System.out.println("  java DataExporter query \"SELECT * FROM bookings WHERE status='BOOKED'\"");
            return;
        }
        
        String command = args[0];
        switch (command.toLowerCase()) {
            case "list":
                exporter.printAllTables();
                break;
            case "all":
                exporter.printAllData();
                break;
            case "table":
                if (args.length < 2) {
                    System.err.println("Error: Table name required");
                    return;
                }
                exporter.printTableData(args[1]);
                break;
            case "query":
                if (args.length < 2) {
                    System.err.println("Error: SQL query required");
                    return;
                }
                exporter.executeQuery(args[1]);
                break;
            default:
                System.err.println("Unknown command: " + command);
        }
    }
}

