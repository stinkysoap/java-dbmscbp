package com.hotel.util;

import com.hotel.config.DatabaseConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class ConnectionManager {
    private static ConnectionManager instance;
    private final DatabaseConfig config;

    private ConnectionManager() {
        this.config = new DatabaseConfig();
        if (config.shouldInitSchema()) {
            initSchema();
        }
    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(config.getUrl());
        // Enable foreign key constraints for SQLite
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    private void initSchema() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            String ddl = loadResource("schema.sql");
            statement.executeUpdate(ddl);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }

    private String loadResource(String name) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name)) {
            if (inputStream == null) {
                throw new IllegalStateException("Resource not found: " + name);
            }
            try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(reader)) {
                return br.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource: " + name, e);
        }
    }
}


