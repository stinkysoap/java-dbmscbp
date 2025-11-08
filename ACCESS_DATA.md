# Accessing SQLite Database Data

There are several ways to access and view data from the `hotel.db` SQLite database:

## Method 1: SQLite Command-Line Tool (Recommended)

### Install SQLite (if not already installed)

**Linux:**
```bash
sudo apt install sqlite3
```

**macOS:**
```bash
brew install sqlite3
```

**Windows:**
Download from: https://www.sqlite.org/download.html

### Using SQLite CLI

```bash
# Open the database
sqlite3 hotel.db

# Once inside SQLite, you can run commands:
.tables                    # List all tables
.schema                    # Show database schema
.schema rooms             # Show schema for specific table

# Query data
SELECT * FROM rooms;
SELECT * FROM guests;
SELECT * FROM bookings;

# Count records
SELECT COUNT(*) FROM rooms;
SELECT COUNT(*) FROM bookings WHERE status='BOOKED';

# Join queries
SELECT b.id, g.full_name, r.number, b.check_in, b.check_out 
FROM bookings b
JOIN guests g ON b.guest_id = g.id
JOIN rooms r ON b.room_id = r.id;

# Export to CSV
.mode csv
.headers on
.output bookings.csv
SELECT * FROM bookings;

# Exit
.quit
```

### Quick Commands

```bash
# View all rooms
sqlite3 hotel.db "SELECT * FROM rooms;"

# View all bookings
sqlite3 hotel.db "SELECT * FROM bookings;"

# View bookings with guest names
sqlite3 hotel.db "SELECT b.id, g.full_name, r.number, b.check_in, b.check_out FROM bookings b JOIN guests g ON b.guest_id = g.id JOIN rooms r ON b.room_id = r.id;"

# Export all data to CSV
sqlite3 hotel.db <<EOF
.mode csv
.headers on
.output all_data.csv
SELECT * FROM rooms;
SELECT * FROM guests;
SELECT * FROM bookings;
.quit
EOF
```

## Method 2: Java Data Exporter Utility

A Java utility is included to access data programmatically.

### Compile and Run

```bash
# Build the project first
mvn clean package

# List all tables
java -cp 'target/classes:target/lib/*' com.hotel.util.DataExporter list

# View all data
java -cp 'target/classes:target/lib/*' com.hotel.util.DataExporter all

# View specific table
java -cp 'target/classes:target/lib/*' com.hotel.util.DataExporter table rooms
java -cp 'target/classes:target/lib/*' com.hotel.util.DataExporter table bookings

# Execute custom query
java -cp 'target/classes:target/lib/*' com.hotel.util.DataExporter query "SELECT * FROM bookings WHERE status='BOOKED'"
```

## Method 3: SQLite Browser (GUI Tool)

### Install DB Browser for SQLite

**Linux:**
```bash
sudo apt install sqlitebrowser
```

**macOS:**
```bash
brew install --cask db-browser-for-sqlite
```

**Windows/All Platforms:**
Download from: https://sqlitebrowser.org/

Then:
1. Open DB Browser for SQLite
2. File → Open Database → Select `hotel.db`
3. Browse Data tab to view tables
4. Execute SQL tab to run queries

## Method 4: Export to CSV/JSON

### Export to CSV using SQLite CLI

```bash
sqlite3 hotel.db <<EOF
.headers on
.mode csv
.output rooms.csv
SELECT * FROM rooms;
.output bookings.csv
SELECT * FROM bookings;
.quit
EOF
```

### Export to JSON

```bash
sqlite3 hotel.db <<EOF
.mode json
.output data.json
SELECT * FROM rooms;
SELECT * FROM guests;
SELECT * FROM bookings;
.quit
EOF
```

## Useful Queries

### View all bookings with details
```sql
SELECT 
    b.id AS booking_id,
    g.full_name AS guest_name,
    r.number AS room_number,
    r.type AS room_type,
    b.check_in,
    b.check_out,
    b.total_amount,
    b.status
FROM bookings b
JOIN guests g ON b.guest_id = g.id
JOIN rooms r ON b.room_id = r.id
ORDER BY b.check_in DESC;
```

### View invoices with payments
```sql
SELECT 
    i.id AS invoice_id,
    i.booking_id,
    i.total,
    i.status AS invoice_status,
    SUM(p.amount) AS paid_amount
FROM invoices i
LEFT JOIN payments p ON i.id = p.invoice_id
GROUP BY i.id;
```

### View room occupancy
```sql
SELECT 
    r.number,
    r.type,
    r.status,
    COUNT(b.id) AS total_bookings
FROM rooms r
LEFT JOIN bookings b ON r.id = b.room_id
GROUP BY r.id;
```

## Database Location

The database file `hotel.db` is located in the project root directory:
```
/home/m/Hotel ManageMent System/hotel.db
```

## Backup Database

```bash
# Copy the file
cp hotel.db hotel_backup.db

# Or use SQLite backup command
sqlite3 hotel.db ".backup hotel_backup.db"
```

