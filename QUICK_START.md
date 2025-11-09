# Quick Start Guide - Loading Sample Data

## The Problem
If you get errors like "no such table: rooms" when loading sample data, it means the database schema hasn't been created yet.

## Solution: Create Schema First

### Option 1: Using Java Application (Easiest)

```bash
# Step 1: Build the project
mvn clean package

# Step 2: Run the app once to create the database schema (then exit)
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
# When the menu appears, press 0 to exit
# This creates the database file and all tables

# Step 3: Load sample data
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.SampleDataLoader --clear
```

### Option 2: Using SQLite Command Line

```bash
# Step 1: Create the schema first
sqlite3 hotel.db < src/main/resources/schema.sql

# Step 2: Load sample data
sqlite3 hotel.db < src/main/resources/sample_data.sql
```

### Option 3: Using SQLite Interactively

```bash
# Open SQLite
sqlite3 hotel.db

# Create schema
.read src/main/resources/schema.sql

# Load sample data
.read src/main/resources/sample_data.sql

# Verify
SELECT COUNT(*) FROM rooms;
SELECT COUNT(*) FROM bookings;

# Exit
.quit
```

## Verify Everything Works

```bash
# Check tables exist
sqlite3 hotel.db ".tables"

# Check data was loaded
sqlite3 hotel.db "SELECT COUNT(*) as room_count FROM rooms;"
sqlite3 hotel.db "SELECT COUNT(*) as booking_count FROM bookings;"
```

You should see:
- room_count: 20
- booking_count: 25

## All-in-One Command

```bash
# Build, create schema, and load data in one go
mvn clean package && \
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App <<< "0" && \
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.SampleDataLoader --clear
```

---

**Remember**: Always create the schema (tables) before loading sample data!

