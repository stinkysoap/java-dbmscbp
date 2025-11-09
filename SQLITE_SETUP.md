# SQLite Setup Guide

This guide will help you set up and use SQLite for the Hotel Management System.

## Overview

SQLite is an embedded database - **no installation or server setup required!** The database is stored in a single file on your local filesystem.

## Prerequisites

- Java 17 or higher
- Maven installed
- **No database server installation needed!**

## Step 1: Verify Java and Maven

```bash
# Check Java version
java -version

# Check Maven version
mvn -version
```

## Step 2: Configure Application

Edit `src/main/resources/application.properties`:

```properties
db.url=jdbc:sqlite:hotel.db
db.initSchema=true
```

### Database File Location

The database file (`hotel.db`) will be created in the project root directory by default.

You can specify a different location:
```properties
# Absolute path
db.url=jdbc:sqlite:/path/to/your/database/hotel.db

# Relative path (relative to project root)
db.url=jdbc:sqlite:data/hotel.db

# In-memory database (temporary, lost on restart)
db.url=jdbc:sqlite::memory:
```

## Step 3: Build the Project

```bash
# Build the project (downloads SQLite JDBC driver)
mvn clean package
```

## Step 4: Run the Application

```bash
# Run the application
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
```

The application will automatically:
1. Create the database file (`hotel.db`) if it doesn't exist
2. Create all tables if `db.initSchema=true`
3. Initialize the schema from `src/main/resources/schema.sql`

## Step 5: Verify Database

### Using SQLite Command Line Tool

If you have SQLite installed:

```bash
# Open the database
sqlite3 hotel.db

# List all tables
.tables

# View table structure
.schema rooms

# Query data
SELECT * FROM rooms;

# Exit
.quit
```

### Using the DataExporter Utility

The project includes a `DataExporter` utility class:

```bash
# List all tables
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.DataExporter list

# Print all table data
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.DataExporter all

# Print specific table
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.DataExporter table rooms

# Execute custom query
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.DataExporter query "SELECT * FROM bookings WHERE status='BOOKED'"
```

## Database File Management

### Backup Database

Simply copy the database file:

```bash
# Backup
cp hotel.db hotel.db.backup

# Or with timestamp
cp hotel.db hotel.db.$(date +%Y%m%d_%H%M%S).backup
```

### Reset Database

Delete the database file and restart the application:

```bash
# Remove database file
rm hotel.db

# Run application (will create new database)
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
```

### Move Database

You can move the database file to a different location and update `application.properties`:

```bash
# Move database
mkdir -p data
mv hotel.db data/hotel.db

# Update application.properties
# db.url=jdbc:sqlite:data/hotel.db
```

## Troubleshooting

### Database File Not Created

- Check file permissions in the project directory
- Verify `db.initSchema=true` in `application.properties`
- Check for errors in the console output

### Permission Denied

- Ensure you have write permissions in the project directory
- On Linux/Mac: `chmod 755 .` or `chmod 777 .` (less secure)
- On Windows: Run as administrator if needed

### Database Locked

- Close other applications that might be using the database
- Check if another instance of the application is running
- SQLite supports concurrent reads but only one write at a time

### Schema Not Initializing

- Verify `db.initSchema=true` in `application.properties`
- Check that `schema.sql` exists in `src/main/resources/`
- Look for SQL errors in the console output
- Delete `hotel.db` and restart to force schema creation

### Connection Errors

- Verify the database file path is correct
- Check that the path directory exists (for custom paths)
- Ensure no special characters in the path that need escaping

## SQLite Features Used

### Data Types
- `INTEGER` - For IDs and numeric values
- `TEXT` - For strings (names, emails, etc.)
- `REAL` - For floating-point numbers (prices, amounts)
- `DATE` - Stored as TEXT in SQLite (ISO format: YYYY-MM-DD)
- `DATETIME` - Stored as TEXT in SQLite (ISO format: YYYY-MM-DD HH:MM:SS)

### Constraints
- `PRIMARY KEY AUTOINCREMENT` - Auto-incrementing IDs
- `NOT NULL` - Required fields
- `UNIQUE` - Unique constraints (e.g., room numbers)
- `FOREIGN KEY` - Referential integrity (note: SQLite doesn't enforce by default)
- `DEFAULT` - Default values

### Indexes
- Indexes are created for frequently queried columns
- Improves query performance for large datasets

## Performance Considerations

### For Small to Medium Datasets
- SQLite performs excellently for single-user applications
- No configuration needed
- Fast for typical hotel management operations

### For Large Datasets
- Consider adding more indexes for frequently queried columns
- Use `VACUUM` command periodically to optimize database:
  ```bash
  sqlite3 hotel.db "VACUUM;"
  ```
- Consider database file location on fast storage (SSD)

### Concurrent Access
- SQLite supports multiple concurrent reads
- Only one write operation at a time
- For multi-user scenarios, consider a client-server database (PostgreSQL, MySQL) - Note: This project is designed for SQLite only

## Advantages of SQLite

✅ **No Installation** - Embedded, no server setup  
✅ **Zero Configuration** - Works out of the box  
✅ **Portable** - Single file database  
✅ **Fast** - Excellent performance for single-user apps  
✅ **Reliable** - ACID-compliant transactions  
✅ **Cross-Platform** - Works on Windows, Linux, macOS  

## Limitations

⚠️ **Single Writer** - Only one write operation at a time  
⚠️ **No Network Access** - Database file must be accessible locally  
⚠️ **File Size** - Database file grows with data (no inherent limit, but consider file system limits)  

## Production Considerations

For production deployments:

1. **Backup Strategy**: Regular backups of the database file
2. **File Permissions**: Secure the database file (read/write for application only)
3. **Monitoring**: Monitor database file size and performance
4. **Multi-user Support**: This project uses SQLite (single-user). For production multi-user scenarios, consider migrating to PostgreSQL or MySQL

## Additional Resources

- [SQLite Documentation](https://www.sqlite.org/docs.html)
- [SQLite JDBC Driver](https://github.com/xerial/sqlite-jdbc)
- [SQLite Tutorial](https://www.sqlitetutorial.net/)

## Quick Reference

### Common SQLite Commands

```bash
# Open database
sqlite3 hotel.db

# List tables
.tables

# Show schema
.schema

# Show schema for specific table
.schema rooms

# Execute SQL
SELECT * FROM rooms;

# Export data
.mode csv
.output rooms.csv
SELECT * FROM rooms;
.output stdout

# Import data
.mode csv
.import rooms.csv rooms

# Exit
.quit
```

### Useful Queries

```sql
-- Count records in each table
SELECT 'rooms' as table_name, COUNT(*) as count FROM rooms
UNION ALL
SELECT 'guests', COUNT(*) FROM guests
UNION ALL
SELECT 'bookings', COUNT(*) FROM bookings;

-- Find active bookings
SELECT * FROM bookings WHERE status IN ('BOOKED', 'CHECKED_IN');

-- Calculate total revenue
SELECT SUM(total) as total_revenue FROM invoices WHERE status = 'PAID';

-- Find rooms with most bookings
SELECT r.number, r.type, COUNT(b.id) as booking_count
FROM rooms r
LEFT JOIN bookings b ON r.id = b.room_id
GROUP BY r.id
ORDER BY booking_count DESC;
```

---

**Note**: This application is configured for SQLite by default. The database file will be created automatically on first run.

