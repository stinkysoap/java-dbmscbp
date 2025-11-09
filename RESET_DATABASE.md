# Reset SQLite Database

If you need to reset the SQLite database (clear all data and start fresh), follow these steps:

## Option 1: Delete Database File (Simplest)

Simply delete the database file and restart the application:

```bash
# Delete the database file
rm hotel.db

# Run the application (will create fresh database)
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
```

The application will automatically create all tables on first run.

## Option 2: Clear All Data (Keep Database File)

If you want to keep the database file but clear all data:

### Using SampleDataLoader utility:

```bash
# Clear all data
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.SampleDataLoader --clear
```

### Using SQLite command line:

```bash
sqlite3 hotel.db
```

Then run:
```sql
-- Delete all data (in reverse dependency order)
DELETE FROM payments;
DELETE FROM invoices;
DELETE FROM booking_services;
DELETE FROM services;
DELETE FROM room_tasks;
DELETE FROM employees;
DELETE FROM bookings;
DELETE FROM customers;
DELETE FROM guests;
DELETE FROM rooms;

-- Reset auto-increment counters
DELETE FROM sqlite_sequence WHERE name IN (
    'rooms', 'guests', 'customers', 'bookings', 
    'services', 'booking_services', 'invoices', 
    'payments', 'employees', 'room_tasks'
);

-- Verify tables are empty
SELECT 'rooms' as table_name, COUNT(*) as count FROM rooms
UNION ALL SELECT 'guests', COUNT(*) FROM guests
UNION ALL SELECT 'bookings', COUNT(*) FROM bookings;

.quit
```

## Option 3: Drop and Recreate Tables

If you want to completely recreate the schema:

```bash
sqlite3 hotel.db
```

Then:
```sql
-- Drop all tables (in reverse dependency order)
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS booking_services;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS room_tasks;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS guests;
DROP TABLE IF EXISTS rooms;

-- Drop indexes
DROP INDEX IF EXISTS idx_bookings_room;
DROP INDEX IF EXISTS idx_bookings_guest;
DROP INDEX IF EXISTS idx_room_tasks_room;
DROP INDEX IF EXISTS idx_booking_services_booking;
DROP INDEX IF EXISTS idx_invoices_booking;
DROP INDEX IF EXISTS idx_payments_invoice;

.quit
```

Then restart the application - it will recreate all tables automatically.

## Option 4: Using DataExporter Utility

You can also use the DataExporter to verify the reset:

```bash
# After resetting, verify tables are empty
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.DataExporter all
```

## Backup Before Resetting

Before resetting, you may want to backup your database:

```bash
# Create backup
cp hotel.db hotel.db.backup

# Or with timestamp
cp hotel.db hotel.db.$(date +%Y%m%d_%H%M%S).backup
```

## Restore from Backup

If you need to restore from a backup:

```bash
# Restore from backup
cp hotel.db.backup hotel.db
```

## Troubleshooting

### "Database is locked"
- Close all applications using the database
- Check if another instance of the app is running
- Wait a few seconds and try again

### "Cannot delete database file"
- Ensure no application is using the database
- Check file permissions
- On Windows, close any SQLite tools that might have the file open

### Tables not recreating
- Verify `db.initSchema=true` in `application.properties`
- Check that `schema.sql` exists in `src/main/resources/`
- Look for errors in console output

---

**Note**: Resetting the database will permanently delete all data. Make sure to backup important data first!

