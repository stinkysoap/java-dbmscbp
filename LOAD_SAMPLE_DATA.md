# Loading Sample Data

This guide explains how to load sample data into the hotel management database.

## Overview

The sample data includes:
- **20 rooms** (various types: SINGLE, DOUBLE, SUITE)
- **30 guests** with contact information
- **15 customers** (including corporate clients)
- **12 employees** (various roles)
- **25 bookings** (with different statuses: BOOKED, CHECKED_IN, CHECKED_OUT)
- **15 services** (room service, spa, laundry, etc.)
- **40 booking service line items**
- **20 invoices** (with various payment statuses)
- **30 payments** (CARD, CASH, ONLINE methods)
- **25 room tasks** (housekeeping and maintenance)

**Total: 232 records**

## Method 1: Using Java Utility (Recommended)

### Step 1: Build the project
```bash
mvn clean package
```

### Step 2: Create database schema (if not already created)
```bash
# Run the app once to create the database and tables (then exit)
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
# Press 0 to exit after the menu appears
```

### Step 3: Load sample data

**Option A: Load sample data (keeps existing data):**
```bash
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.SampleDataLoader
```

**Option B: Clear existing data and load fresh sample data:**
```bash
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.SampleDataLoader --clear
```

## Method 2: Using SQLite Command Line

If you have SQLite installed:

**Important**: Create the schema first, then load sample data:

```bash
# Step 1: Create the database schema (tables)
sqlite3 hotel.db < src/main/resources/schema.sql

# Step 2: Load sample data
sqlite3 hotel.db < src/main/resources/sample_data.sql
```

Or interactively:
```bash
sqlite3 hotel.db

# Create schema first
.read src/main/resources/schema.sql

# Then load sample data
.read src/main/resources/sample_data.sql

# Verify
SELECT COUNT(*) FROM rooms;
SELECT COUNT(*) FROM bookings;

.quit
```

## Method 3: Using DataExporter Utility

After loading, you can verify the data:

```bash
# List all tables
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.DataExporter list

# View all data
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.DataExporter all

# View specific table
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.DataExporter table rooms
```

## Sample Data Details

### Rooms
- Mix of SINGLE ($89.99-$99.99), DOUBLE ($129.99-$149.99), and SUITE ($249.99-$399.99)
- Various statuses: AVAILABLE, OCCUPIED, MAINTENANCE

### Bookings
- Dates range from January 2024 to February 2024
- Various statuses: BOOKED, CHECKED_IN, CHECKED_OUT
- Different durations (2-4 nights)

### Services
- Room service (breakfast, lunch, dinner)
- Spa services (massage, facial)
- Transportation (airport shuttle)
- Additional services (laundry, parking, WiFi, etc.)

### Invoices
- Some fully paid (PAID status)
- Some partially paid (ISSUED status with partial payments)
- Tax calculated at 8% (simplified)

### Payments
- Mix of payment methods: CARD, CASH, ONLINE
- Some linked to invoices, some direct to bookings
- Includes deposits and full payments

### Room Tasks
- Housekeeping tasks (cleaning, preparation)
- Maintenance tasks (repairs, inspections)
- Various priorities: LOW, MEDIUM, HIGH
- Statuses: OPEN, IN_PROGRESS, COMPLETED

## Verifying Data

### Check record counts:

```sql
SELECT 'rooms' as table_name, COUNT(*) as count FROM rooms
UNION ALL SELECT 'guests', COUNT(*) FROM guests
UNION ALL SELECT 'bookings', COUNT(*) FROM bookings
UNION ALL SELECT 'services', COUNT(*) FROM services
UNION ALL SELECT 'invoices', COUNT(*) FROM invoices
UNION ALL SELECT 'payments', COUNT(*) FROM payments
UNION ALL SELECT 'employees', COUNT(*) FROM employees
UNION ALL SELECT 'room_tasks', COUNT(*) FROM room_tasks;
```

### View active bookings:

```sql
SELECT b.id, r.number, g.full_name, b.check_in, b.check_out, b.status
FROM bookings b
JOIN rooms r ON b.room_id = r.id
JOIN guests g ON b.guest_id = g.id
WHERE b.status IN ('BOOKED', 'CHECKED_IN')
ORDER BY b.check_in;
```

### View revenue summary:

```sql
SELECT 
    COUNT(*) as total_invoices,
    SUM(CASE WHEN status = 'PAID' THEN total ELSE 0 END) as paid_revenue,
    SUM(CASE WHEN status = 'ISSUED' THEN total ELSE 0 END) as outstanding_revenue,
    SUM(total) as total_revenue
FROM invoices;
```

## Troubleshooting

### "UNIQUE constraint failed"
- Some data may already exist
- Use `--clear` flag to remove existing data first
- Or manually delete specific records

### "Foreign key constraint failed"
- Ensure tables are created in correct order
- Run schema.sql first, then sample_data.sql
- Check that referenced IDs exist

### "Resource not found"
- Ensure you're running from project root directory
- Verify `sample_data.sql` exists in `src/main/resources/`
- Check file permissions

### Data not appearing
- Verify database file location matches `application.properties`
- Check for SQL errors in console output
- Use DataExporter to verify tables exist

## Resetting Database

To completely reset and reload:

```bash
# Delete database file
rm hotel.db

# Run application (creates schema)
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.App
# (Exit immediately after schema creation)

# Load sample data
java -cp 'target/hotel-management-1.0.0.jar:target/lib/*' com.hotel.util.SampleDataLoader --clear
```

## Customizing Sample Data

Edit `src/main/resources/sample_data.sql` to:
- Add more rooms, guests, or bookings
- Modify dates, prices, or statuses
- Add custom services or employees
- Adjust quantities and relationships

After editing, reload using one of the methods above.

---

**Note**: Sample data is designed for testing and demonstration purposes. Dates are set to 2024 - adjust as needed for your use case.

