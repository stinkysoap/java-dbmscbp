package com.hotel.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private final String url;
    private final boolean initSchema;

    public DatabaseConfig() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
        this.url = properties.getProperty("db.url", "jdbc:sqlite:hotel.db");
        this.initSchema = Boolean.parseBoolean(properties.getProperty("db.initSchema", "true"));
    }

    public String getUrl() {
        return url;
    }

    public boolean shouldInitSchema() {
        return initSchema;
    }
}


